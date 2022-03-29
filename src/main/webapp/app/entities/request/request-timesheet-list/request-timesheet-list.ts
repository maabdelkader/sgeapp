import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TimeSheetService } from 'app/entities/time-sheet/service/time-sheet.service';
import { ITimeSheet } from 'app/entities/time-sheet/time-sheet.model';

@Component({
  selector: 'app-request-timesheet-list-component',
  templateUrl: './request-timesheet-list.html',
})
export class RequestTimesheetListComponent {
  timeSheets?: ITimeSheet[] | null;
  requestId?: number;
  isLoading = false;

  constructor(private timesheetService: TimeSheetService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(data => {
      this.timeSheets = data.timesheets.body;
    });

    this.activatedRoute.params.subscribe(data => {
      this.requestId = data.id;
    });
  }
}
