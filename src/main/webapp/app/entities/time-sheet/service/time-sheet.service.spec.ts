import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import dayjs from 'dayjs/esm';

import { DATE_FORMAT } from 'app/config/input.constants';
import { TimeSheetStatus } from 'app/entities/enumerations/time-sheet-status.model';
import { ITimeSheet, TimeSheet } from '../time-sheet.model';

import { TimeSheetService } from './time-sheet.service';

describe('TimeSheet Service', () => {
  let service: TimeSheetService;
  let httpMock: HttpTestingController;
  let elemDefault: ITimeSheet;
  let expectedResult: ITimeSheet | ITimeSheet[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TimeSheetService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      employeeCivility: 'AAAAAAA',
      employeeFirstName: 'AAAAAAA',
      employeeLastName: 'AAAAAAA',
      registryNumber: 'AAAAAAA',
      dateFrom: currentDate,
      dateTo: currentDate,
      direction: 'AAAAAAA',
      division: 'AAAAAAA',
      um: 'AAAAAAA',
      status: TimeSheetStatus.CREATED,
      ccas: 'AAAAAAA',
      nbHoursCcas: 0,
      coordinatingCommittee: 'AAAAAAA',
      nbHoursCdc: 0,
      nbHoursAdmin: 0,
      nbHoursPotFdCfdt: 0,
      nbHoursPotFdCgt: 0,
      nbHoursPotFdFo: 0,
      nbHoursPotFdCfeCgc: 0,
      commissionType: 'AAAAAAA',
      nbHoursCommision: 0,
      proximityType: 'AAAAAAA',
      nbHoursProximity: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          dateFrom: currentDate.format(DATE_FORMAT),
          dateTo: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TimeSheet', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          dateFrom: currentDate.format(DATE_FORMAT),
          dateTo: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateFrom: currentDate,
          dateTo: currentDate,
        },
        returnedFromService
      );

      service.create(new TimeSheet()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TimeSheet', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          employeeCivility: 'BBBBBB',
          employeeFirstName: 'BBBBBB',
          employeeLastName: 'BBBBBB',
          registryNumber: 'BBBBBB',
          dateFrom: currentDate.format(DATE_FORMAT),
          dateTo: currentDate.format(DATE_FORMAT),
          direction: 'BBBBBB',
          division: 'BBBBBB',
          um: 'BBBBBB',
          status: 'BBBBBB',
          ccas: 'BBBBBB',
          nbHoursCcas: 1,
          coordinatingCommittee: 'BBBBBB',
          nbHoursCdc: 1,
          nbHoursAdmin: 1,
          nbHoursPotFdCfdt: 1,
          nbHoursPotFdCgt: 1,
          nbHoursPotFdFo: 1,
          nbHoursPotFdCfeCgc: 1,
          commissionType: 'BBBBBB',
          nbHoursCommision: 1,
          proximityType: 'BBBBBB',
          nbHoursProximity: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateFrom: currentDate,
          dateTo: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TimeSheet', () => {
      const patchObject = Object.assign(
        {
          employeeCivility: 'BBBBBB',
          dateFrom: currentDate.format(DATE_FORMAT),
          dateTo: currentDate.format(DATE_FORMAT),
          direction: 'BBBBBB',
          um: 'BBBBBB',
          ccas: 'BBBBBB',
          nbHoursCcas: 1,
          nbHoursAdmin: 1,
          nbHoursPotFdCfdt: 1,
          nbHoursPotFdCgt: 1,
          nbHoursPotFdFo: 1,
          nbHoursPotFdCfeCgc: 1,
          commissionType: 'BBBBBB',
        },
        new TimeSheet()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          dateFrom: currentDate,
          dateTo: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TimeSheet', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          employeeCivility: 'BBBBBB',
          employeeFirstName: 'BBBBBB',
          employeeLastName: 'BBBBBB',
          registryNumber: 'BBBBBB',
          dateFrom: currentDate.format(DATE_FORMAT),
          dateTo: currentDate.format(DATE_FORMAT),
          direction: 'BBBBBB',
          division: 'BBBBBB',
          um: 'BBBBBB',
          status: 'BBBBBB',
          ccas: 'BBBBBB',
          nbHoursCcas: 1,
          coordinatingCommittee: 'BBBBBB',
          nbHoursCdc: 1,
          nbHoursAdmin: 1,
          nbHoursPotFdCfdt: 1,
          nbHoursPotFdCgt: 1,
          nbHoursPotFdFo: 1,
          nbHoursPotFdCfeCgc: 1,
          commissionType: 'BBBBBB',
          nbHoursCommision: 1,
          proximityType: 'BBBBBB',
          nbHoursProximity: 1,
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          dateFrom: currentDate,
          dateTo: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TimeSheet', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTimeSheetToCollectionIfMissing', () => {
      it('should add a TimeSheet to an empty array', () => {
        const timeSheet: ITimeSheet = { id: 123 };
        expectedResult = service.addTimeSheetToCollectionIfMissing([], timeSheet);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(timeSheet);
      });

      it('should not add a TimeSheet to an array that contains it', () => {
        const timeSheet: ITimeSheet = { id: 123 };
        const timeSheetCollection: ITimeSheet[] = [
          {
            ...timeSheet,
          },
          { id: 456 },
        ];
        expectedResult = service.addTimeSheetToCollectionIfMissing(timeSheetCollection, timeSheet);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TimeSheet to an array that doesn't contain it", () => {
        const timeSheet: ITimeSheet = { id: 123 };
        const timeSheetCollection: ITimeSheet[] = [{ id: 456 }];
        expectedResult = service.addTimeSheetToCollectionIfMissing(timeSheetCollection, timeSheet);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(timeSheet);
      });

      it('should add only unique TimeSheet to an array', () => {
        const timeSheetArray: ITimeSheet[] = [{ id: 123 }, { id: 456 }, { id: 37331 }];
        const timeSheetCollection: ITimeSheet[] = [{ id: 123 }];
        expectedResult = service.addTimeSheetToCollectionIfMissing(timeSheetCollection, ...timeSheetArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const timeSheet: ITimeSheet = { id: 123 };
        const timeSheet2: ITimeSheet = { id: 456 };
        expectedResult = service.addTimeSheetToCollectionIfMissing([], timeSheet, timeSheet2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(timeSheet);
        expect(expectedResult).toContain(timeSheet2);
      });

      it('should accept null and undefined values', () => {
        const timeSheet: ITimeSheet = { id: 123 };
        expectedResult = service.addTimeSheetToCollectionIfMissing([], null, timeSheet, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(timeSheet);
      });

      it('should return initial array if no TimeSheet is added', () => {
        const timeSheetCollection: ITimeSheet[] = [{ id: 123 }];
        expectedResult = service.addTimeSheetToCollectionIfMissing(timeSheetCollection, undefined, null);
        expect(expectedResult).toEqual(timeSheetCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
