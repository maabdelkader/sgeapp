import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITimeSheet, getTimeSheetIdentifier } from '../time-sheet.model';

export type EntityResponseType = HttpResponse<ITimeSheet>;
export type EntityArrayResponseType = HttpResponse<ITimeSheet[]>;

@Injectable({ providedIn: 'root' })
export class TimeSheetService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/time-sheets');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(timeSheet: ITimeSheet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeSheet);
    return this.http
      .post<ITimeSheet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(timeSheet: ITimeSheet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeSheet);
    return this.http
      .put<ITimeSheet>(`${this.resourceUrl}/${getTimeSheetIdentifier(timeSheet) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(timeSheet: ITimeSheet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(timeSheet);
    return this.http
      .patch<ITimeSheet>(`${this.resourceUrl}/${getTimeSheetIdentifier(timeSheet) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITimeSheet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITimeSheet[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTimeSheetToCollectionIfMissing(
    timeSheetCollection: ITimeSheet[],
    ...timeSheetsToCheck: (ITimeSheet | null | undefined)[]
  ): ITimeSheet[] {
    const timeSheets: ITimeSheet[] = timeSheetsToCheck.filter(isPresent);
    if (timeSheets.length > 0) {
      const timeSheetCollectionIdentifiers = timeSheetCollection.map(timeSheetItem => getTimeSheetIdentifier(timeSheetItem)!);
      const timeSheetsToAdd = timeSheets.filter(timeSheetItem => {
        const timeSheetIdentifier = getTimeSheetIdentifier(timeSheetItem);
        if (timeSheetIdentifier == null || timeSheetCollectionIdentifiers.includes(timeSheetIdentifier)) {
          return false;
        }
        timeSheetCollectionIdentifiers.push(timeSheetIdentifier);
        return true;
      });
      return [...timeSheetsToAdd, ...timeSheetCollection];
    }
    return timeSheetCollection;
  }

  protected convertDateFromClient(timeSheet: ITimeSheet): ITimeSheet {
    return Object.assign({}, timeSheet, {
      dateFrom: timeSheet.dateFrom?.isValid() ? timeSheet.dateFrom.format(DATE_FORMAT) : undefined,
      dateTo: timeSheet.dateTo?.isValid() ? timeSheet.dateTo.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateFrom = res.body.dateFrom ? dayjs(res.body.dateFrom) : undefined;
      res.body.dateTo = res.body.dateTo ? dayjs(res.body.dateTo) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((timeSheet: ITimeSheet) => {
        timeSheet.dateFrom = timeSheet.dateFrom ? dayjs(timeSheet.dateFrom) : undefined;
        timeSheet.dateTo = timeSheet.dateTo ? dayjs(timeSheet.dateTo) : undefined;
      });
    }
    return res;
  }
}
