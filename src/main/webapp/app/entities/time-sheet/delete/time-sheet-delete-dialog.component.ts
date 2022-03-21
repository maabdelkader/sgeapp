import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimeSheet } from '../time-sheet.model';
import { TimeSheetService } from '../service/time-sheet.service';

@Component({
  templateUrl: './time-sheet-delete-dialog.component.html',
})
export class TimeSheetDeleteDialogComponent {
  timeSheet?: ITimeSheet;

  constructor(protected timeSheetService: TimeSheetService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.timeSheetService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
