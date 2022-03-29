import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RequestComponent } from './list/request.component';
import { RequestDetailComponent } from './detail/request-detail.component';
import { RequestUpdateComponent } from './update/request-update.component';
import { RequestDeleteDialogComponent } from './delete/request-delete-dialog.component';
import { RequestRoutingModule } from './route/request-routing.module';
import { MultiSelectModule } from 'primeng/multiselect';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { RequestTimesheetListComponent } from './request-timesheet-list/request-timesheet-list';

const primeNgModules = [MultiSelectModule, CardModule, ButtonModule, TableModule];
@NgModule({
  imports: [...primeNgModules, SharedModule, RequestRoutingModule],
  declarations: [
    RequestTimesheetListComponent,
    RequestComponent,
    RequestDetailComponent,
    RequestUpdateComponent,
    RequestDeleteDialogComponent,
  ],
  entryComponents: [RequestDeleteDialogComponent],
})
export class RequestModule {}
