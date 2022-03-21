import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SocialOrganizationDetailComponent } from './social-organization-detail.component';

describe('SocialOrganization Management Detail Component', () => {
  let comp: SocialOrganizationDetailComponent;
  let fixture: ComponentFixture<SocialOrganizationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SocialOrganizationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ socialOrganization: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SocialOrganizationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SocialOrganizationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load socialOrganization on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.socialOrganization).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
