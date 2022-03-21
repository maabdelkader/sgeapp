import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SocialOrganizationService } from '../service/social-organization.service';
import { ISocialOrganization, SocialOrganization } from '../social-organization.model';

import { SocialOrganizationUpdateComponent } from './social-organization-update.component';

describe('SocialOrganization Management Update Component', () => {
  let comp: SocialOrganizationUpdateComponent;
  let fixture: ComponentFixture<SocialOrganizationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let socialOrganizationService: SocialOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SocialOrganizationUpdateComponent],
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
      .overrideTemplate(SocialOrganizationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocialOrganizationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    socialOrganizationService = TestBed.inject(SocialOrganizationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const socialOrganization: ISocialOrganization = { id: 456 };

      activatedRoute.data = of({ socialOrganization });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(socialOrganization));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SocialOrganization>>();
      const socialOrganization = { id: 123 };
      jest.spyOn(socialOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ socialOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: socialOrganization }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(socialOrganizationService.update).toHaveBeenCalledWith(socialOrganization);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SocialOrganization>>();
      const socialOrganization = new SocialOrganization();
      jest.spyOn(socialOrganizationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ socialOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: socialOrganization }));
      saveSubject.complete();

      // THEN
      expect(socialOrganizationService.create).toHaveBeenCalledWith(socialOrganization);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SocialOrganization>>();
      const socialOrganization = { id: 123 };
      jest.spyOn(socialOrganizationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ socialOrganization });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(socialOrganizationService.update).toHaveBeenCalledWith(socialOrganization);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
