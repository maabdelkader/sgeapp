import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimeSheet } from '../time-sheet.model';
import { TimeSheetService } from '../service/time-sheet.service';
import { TimeSheetDeleteDialogComponent } from '../delete/time-sheet-delete-dialog.component';

@Component({
  selector: 'jhi-time-sheet',
  templateUrl: './time-sheet.component.html',
})
export class TimeSheetComponent implements OnInit {
  timeSheets?: ITimeSheet[];
  isLoading = false;

  constructor(protected timeSheetService: TimeSheetService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.timeSheetService.query().subscribe({
      next: (res: HttpResponse<ITimeSheet[]>) => {
        this.isLoading = false;
        this.timeSheets = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITimeSheet): number {
    return item.id!;
  }

  delete(timeSheet: ITimeSheet): void {
    const modalRef = this.modalService.open(TimeSheetDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.timeSheet = timeSheet;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
