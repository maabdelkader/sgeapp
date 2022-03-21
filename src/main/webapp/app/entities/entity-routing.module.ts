import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company',
        data: { pageTitle: 'Companies' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'application-user',
        data: { pageTitle: 'ApplicationUsers' },
        loadChildren: () => import('./application-user/application-user.module').then(m => m.ApplicationUserModule),
      },
      {
        path: 'social-organization',
        data: { pageTitle: 'SocialOrganizations' },
        loadChildren: () => import('./social-organization/social-organization.module').then(m => m.SocialOrganizationModule),
      },
      {
        path: 'campaign',
        data: { pageTitle: 'Campaigns' },
        loadChildren: () => import('./campaign/campaign.module').then(m => m.CampaignModule),
      },
      {
        path: 'request',
        data: { pageTitle: 'Requests' },
        loadChildren: () => import('./request/request.module').then(m => m.RequestModule),
      },
      {
        path: 'time-sheet',
        data: { pageTitle: 'TimeSheets' },
        loadChildren: () => import('./time-sheet/time-sheet.module').then(m => m.TimeSheetModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
