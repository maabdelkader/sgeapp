import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TimeSheetComponent } from '../list/time-sheet.component';
import { TimeSheetDetailComponent } from '../detail/time-sheet-detail.component';
import { TimeSheetUpdateComponent } from '../update/time-sheet-update.component';
import { TimeSheetRoutingResolveService } from './time-sheet-routing-resolve.service';

const timeSheetRoute: Routes = [
  {
    path: '',
    component: TimeSheetComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TimeSheetDetailComponent,
    resolve: {
      timeSheet: TimeSheetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TimeSheetUpdateComponent,
    resolve: {
      timeSheet: TimeSheetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TimeSheetUpdateComponent,
    resolve: {
      timeSheet: TimeSheetRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(timeSheetRoute)],
  exports: [RouterModule],
})
export class TimeSheetRoutingModule {}
