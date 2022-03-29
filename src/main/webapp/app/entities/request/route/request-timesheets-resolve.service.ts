import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRequest, Request } from '../request.model';
import { RequestService } from '../service/request.service';
import { ITimeSheet, TimeSheet } from 'app/entities/time-sheet/time-sheet.model';
import { TimeSheetService } from 'app/entities/time-sheet/service/time-sheet.service';

@Injectable({ providedIn: 'root' })
export class RequestTimesheetsResolveService implements Resolve<any> {
  constructor(protected service: TimeSheetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): any {
    const id = route.params['id'];
    if (id) {
      return this.service.query({ requestId: id });
    }
  }
}
