import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TimeSheetComponent } from './list/time-sheet.component';
import { TimeSheetDetailComponent } from './detail/time-sheet-detail.component';
import { TimeSheetUpdateComponent } from './update/time-sheet-update.component';
import { TimeSheetDeleteDialogComponent } from './delete/time-sheet-delete-dialog.component';
import { TimeSheetRoutingModule } from './route/time-sheet-routing.module';

@NgModule({
  imports: [SharedModule, TimeSheetRoutingModule],
  declarations: [TimeSheetComponent, TimeSheetDetailComponent, TimeSheetUpdateComponent, TimeSheetDeleteDialogComponent],
  entryComponents: [TimeSheetDeleteDialogComponent],
})
export class TimeSheetModule {}
