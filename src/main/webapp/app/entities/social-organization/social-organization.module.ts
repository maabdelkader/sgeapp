import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SocialOrganizationComponent } from './list/social-organization.component';
import { SocialOrganizationDetailComponent } from './detail/social-organization-detail.component';
import { SocialOrganizationUpdateComponent } from './update/social-organization-update.component';
import { SocialOrganizationDeleteDialogComponent } from './delete/social-organization-delete-dialog.component';
import { SocialOrganizationRoutingModule } from './route/social-organization-routing.module';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
@NgModule({
  imports: [ButtonModule, TableModule, SharedModule, SocialOrganizationRoutingModule],
  declarations: [
    SocialOrganizationComponent,
    SocialOrganizationDetailComponent,
    SocialOrganizationUpdateComponent,
    SocialOrganizationDeleteDialogComponent,
  ],
  entryComponents: [SocialOrganizationDeleteDialogComponent],
})
export class SocialOrganizationModule {}
