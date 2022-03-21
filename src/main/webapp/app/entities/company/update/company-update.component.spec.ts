import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompanyService } from '../service/company.service';
import { ICompany, Company } from '../company.model';
import { ISocialOrganization } from 'app/entities/social-organization/social-organization.model';
import { SocialOrganizationService } from 'app/entities/social-organization/service/social-organization.service';

import { CompanyUpdateComponent } from './company-update.component';

describe('Company Management Update Component', () => {
  let comp: CompanyUpdateComponent;
  let fixture: ComponentFixture<CompanyUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let companyService: CompanyService;
  let socialOrganizationService: SocialOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompanyUpdateComponent],
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
      .overrideTemplate(CompanyUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompanyUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    companyService = TestBed.inject(CompanyService);
    socialOrganizationService = TestBed.inject(SocialOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call socialOrganization query and add missing value', () => {
      const company: ICompany = { id: 456 };
      const socialOrganization: ISocialOrganization = { id: 61668 };
      company.socialOrganization = socialOrganization;

      const socialOrganizationCollection: ISocialOrganization[] = [{ id: 53942 }];
      jest.spyOn(socialOrganizationService, 'query').mockReturnValue(of(new HttpResponse({ body: socialOrganizationCollection })));
      const expectedCollection: ISocialOrganization[] = [socialOrganization, ...socialOrganizationCollection];
      jest.spyOn(socialOrganizationService, 'addSocialOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(socialOrganizationService.query).toHaveBeenCalled();
      expect(socialOrganizationService.addSocialOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        socialOrganizationCollection,
        socialOrganization
      );
      expect(comp.socialOrganizationsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const company: ICompany = { id: 456 };
      const socialOrganization: ISocialOrganization = { id: 69996 };
      company.socialOrganization = socialOrganization;

      activatedRoute.data = of({ company });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(company));
      expect(comp.socialOrganizationsCollection).toContain(socialOrganization);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Company>>();
      const company = { id: 123 };
      jest.spyOn(companyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: company }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(companyService.update).toHaveBeenCalledWith(company);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Company>>();
      const company = new Company();
      jest.spyOn(companyService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: company }));
      saveSubject.complete();

      // THEN
      expect(companyService.create).toHaveBeenCalledWith(company);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Company>>();
      const company = { id: 123 };
      jest.spyOn(companyService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ company });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(companyService.update).toHaveBeenCalledWith(company);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSocialOrganizationById', () => {
      it('Should return tracked SocialOrganization primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSocialOrganizationById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
