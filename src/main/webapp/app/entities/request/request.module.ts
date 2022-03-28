import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RequestComponent } from './list/request.component';
import { RequestDetailComponent } from './detail/request-detail.component';
import { RequestUpdateComponent } from './update/request-update.component';
import { RequestDeleteDialogComponent } from './delete/request-delete-dialog.component';
import { RequestRoutingModule } from './route/request-routing.module';
import { MultiSelectModule } from 'primeng/multiselect';
import { CardModule } from 'primeng/card';

@NgModule({
  imports: [MultiSelectModule, CardModule, SharedModule, RequestRoutingModule],
  declarations: [RequestComponent, RequestDetailComponent, RequestUpdateComponent, RequestDeleteDialogComponent],
  entryComponents: [RequestDeleteDialogComponent],
})
export class RequestModule {}
