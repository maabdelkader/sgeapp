import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITimeSheet } from '../time-sheet.model';
import { TimeSheetService } from '../service/time-sheet.service';
import { TimeSheetDeleteDialogComponent } from '../delete/time-sheet-delete-dialog.component';
import { RequestService } from 'app/entities/request/service/request.service';
import { IRequest } from 'app/entities/request/request.model';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-time-sheet',
  templateUrl: './time-sheet.component.html',
})
export class TimeSheetComponent implements OnInit {
  timeSheets?: ITimeSheet[];
  requests?: any;
  selectedRequest?: any;
  isLoading = false;
  display = false;

  constructor(
    protected timeSheetService: TimeSheetService,
    protected modalService: NgbModal,
    private requestService: RequestService,
    private router: Router
  ) {}

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

    this.requestService.query().subscribe(data => {
      this.requests = data.body;
      console.log(this.requests);
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITimeSheet): number {
    return item.id!;
  }

  showDialog() {
    this.display = true;
  }

  newTimesheet() {
    this.router.navigate(['/time-sheet/new/', this.selectedRequest[0]]);
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
