import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITimeSheet, TimeSheet } from '../time-sheet.model';
import { TimeSheetService } from '../service/time-sheet.service';

@Injectable({ providedIn: 'root' })
export class TimeSheetRoutingResolveService implements Resolve<ITimeSheet> {
  constructor(protected service: TimeSheetService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITimeSheet> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((timeSheet: HttpResponse<TimeSheet>) => {
          if (timeSheet.body) {
            return of(timeSheet.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TimeSheet());
  }
}
