import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TimeSheetService } from '../service/time-sheet.service';
import { ITimeSheet, TimeSheet } from '../time-sheet.model';
import { IRequest } from 'app/entities/request/request.model';
import { RequestService } from 'app/entities/request/service/request.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';

import { TimeSheetUpdateComponent } from './time-sheet-update.component';

describe('TimeSheet Management Update Component', () => {
  let comp: TimeSheetUpdateComponent;
  let fixture: ComponentFixture<TimeSheetUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let timeSheetService: TimeSheetService;
  let requestService: RequestService;
  let companyService: CompanyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TimeSheetUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TimeSheetUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TimeSheetUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    timeSheetService = TestBed.inject(TimeSheetService);
    requestService = TestBed.inject(RequestService);
    companyService = TestBed.inject(CompanyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Request query and add missing value', () => {
      const timeSheet: ITimeSheet = { id: 456 };
      const request: IRequest = { id: 67312 };
      timeSheet.request = request;

      const requestCollection: IRequest[] = [{ id: 6424 }];
      jest.spyOn(requestService, 'query').mockReturnValue(of(new HttpResponse({ body: requestCollection })));
      const additionalRequests = [request];
      const expectedCollection: IRequest[] = [...additionalRequests, ...requestCollection];
      jest.spyOn(requestService, 'addRequestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      expect(requestService.query).toHaveBeenCalled();
      expect(requestService.addRequestToCollectionIfMissing).toHaveBeenCalledWith(requestCollection, ...additionalRequests);
      expect(comp.requestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Company query and add missing value', () => {
      const timeSheet: ITimeSheet = { id: 456 };
      const company: ICompany = { id: 36764 };
      timeSheet.company = company;

      const companyCollection: ICompany[] = [{ id: 3676 }];
      jest.spyOn(companyService, 'query').mockReturnValue(of(new HttpResponse({ body: companyCollection })));
      const additionalCompanies = [company];
      const expectedCollection: ICompany[] = [...additionalCompanies, ...companyCollection];
      jest.spyOn(companyService, 'addCompanyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      expect(companyService.query).toHaveBeenCalled();
      expect(companyService.addCompanyToCollectionIfMissing).toHaveBeenCalledWith(companyCollection, ...additionalCompanies);
      expect(comp.companiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const timeSheet: ITimeSheet = { id: 456 };
      const request: IRequest = { id: 54631 };
      timeSheet.request = request;
      const company: ICompany = { id: 53731 };
      timeSheet.company = company;

      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(timeSheet));
      expect(comp.requestsSharedCollection).toContain(request);
      expect(comp.companiesSharedCollection).toContain(company);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TimeSheet>>();
      const timeSheet = { id: 123 };
      jest.spyOn(timeSheetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timeSheet }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(timeSheetService.update).toHaveBeenCalledWith(timeSheet);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TimeSheet>>();
      const timeSheet = new TimeSheet();
      jest.spyOn(timeSheetService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: timeSheet }));
      saveSubject.complete();

      // THEN
      expect(timeSheetService.create).toHaveBeenCalledWith(timeSheet);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TimeSheet>>();
      const timeSheet = { id: 123 };
      jest.spyOn(timeSheetService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ timeSheet });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(timeSheetService.update).toHaveBeenCalledWith(timeSheet);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackRequestById', () => {
      it('Should return tracked Request primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackRequestById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCompanyById', () => {
      it('Should return tracked Company primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCompanyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
