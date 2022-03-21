import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { SocialOrganizationService } from '../service/social-organization.service';

import { SocialOrganizationComponent } from './social-organization.component';

describe('SocialOrganization Management Component', () => {
  let comp: SocialOrganizationComponent;
  let fixture: ComponentFixture<SocialOrganizationComponent>;
  let service: SocialOrganizationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SocialOrganizationComponent],
    })
      .overrideTemplate(SocialOrganizationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SocialOrganizationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SocialOrganizationService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.socialOrganizations?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
