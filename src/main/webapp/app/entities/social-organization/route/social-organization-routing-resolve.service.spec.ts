import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ISocialOrganization, SocialOrganization } from '../social-organization.model';
import { SocialOrganizationService } from '../service/social-organization.service';

import { SocialOrganizationRoutingResolveService } from './social-organization-routing-resolve.service';

describe('SocialOrganization routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: SocialOrganizationRoutingResolveService;
  let service: SocialOrganizationService;
  let resultSocialOrganization: ISocialOrganization | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(SocialOrganizationRoutingResolveService);
    service = TestBed.inject(SocialOrganizationService);
    resultSocialOrganization = undefined;
  });

  describe('resolve', () => {
    it('should return ISocialOrganization returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocialOrganization = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocialOrganization).toEqual({ id: 123 });
    });

    it('should return new ISocialOrganization if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocialOrganization = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSocialOrganization).toEqual(new SocialOrganization());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as SocialOrganization })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultSocialOrganization = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultSocialOrganization).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
