import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISocialOrganization, SocialOrganization } from '../social-organization.model';
import { SocialOrganizationService } from '../service/social-organization.service';

@Injectable({ providedIn: 'root' })
export class SocialOrganizationRoutingResolveService implements Resolve<ISocialOrganization> {
  constructor(protected service: SocialOrganizationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISocialOrganization> | Observable<never> {
    const id = route.params['socialOrgId'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((socialOrganization: HttpResponse<SocialOrganization>) => {
          if (socialOrganization.body) {
            return of(socialOrganization.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SocialOrganization());
  }
}
