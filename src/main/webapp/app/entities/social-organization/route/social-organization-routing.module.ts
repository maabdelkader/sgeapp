import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SocialOrganizationComponent } from '../list/social-organization.component';
import { SocialOrganizationDetailComponent } from '../detail/social-organization-detail.component';
import { SocialOrganizationUpdateComponent } from '../update/social-organization-update.component';
import { SocialOrganizationRoutingResolveService } from './social-organization-routing-resolve.service';

const socialOrganizationRoute: Routes = [
  {
    path: '',
    component: SocialOrganizationComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SocialOrganizationDetailComponent,
    resolve: {
      socialOrganization: SocialOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SocialOrganizationUpdateComponent,
    resolve: {
      socialOrganization: SocialOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SocialOrganizationUpdateComponent,
    resolve: {
      socialOrganization: SocialOrganizationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(socialOrganizationRoute)],
  exports: [RouterModule],
})
export class SocialOrganizationRoutingModule {}
