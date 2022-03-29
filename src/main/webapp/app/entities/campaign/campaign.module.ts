import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CampaignComponent } from './list/campaign.component';
import { CampaignDetailComponent } from './detail/campaign-detail.component';
import { CampaignUpdateComponent } from './update/campaign-update.component';
import { CampaignDeleteDialogComponent } from './delete/campaign-delete-dialog.component';
import { CampaignRoutingModule } from './route/campaign-routing.module';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

const primeNgModules = [ButtonModule, TableModule];

@NgModule({
  imports: [...primeNgModules, SharedModule, CampaignRoutingModule],
  declarations: [CampaignComponent, CampaignDetailComponent, CampaignUpdateComponent, CampaignDeleteDialogComponent],
  entryComponents: [CampaignDeleteDialogComponent],
})
export class CampaignModule {}
