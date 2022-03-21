import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISocialOrganization, SocialOrganization } from '../social-organization.model';

import { SocialOrganizationService } from './social-organization.service';

describe('SocialOrganization Service', () => {
  let service: SocialOrganizationService;
  let httpMock: HttpTestingController;
  let elemDefault: ISocialOrganization;
  let expectedResult: ISocialOrganization | ISocialOrganization[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SocialOrganizationService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAAAAA',
      adminQuota: 0,
      commissionQuota: 0,
      proximityQuota: 0,
      numberOfAdmins: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a SocialOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new SocialOrganization()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SocialOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          adminQuota: 1,
          commissionQuota: 1,
          proximityQuota: 1,
          numberOfAdmins: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SocialOrganization', () => {
      const patchObject = Object.assign(
        {
          name: 'BBBBBB',
          adminQuota: 1,
          numberOfAdmins: 1,
        },
        new SocialOrganization()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SocialOrganization', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          name: 'BBBBBB',
          adminQuota: 1,
          commissionQuota: 1,
          proximityQuota: 1,
          numberOfAdmins: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a SocialOrganization', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addSocialOrganizationToCollectionIfMissing', () => {
      it('should add a SocialOrganization to an empty array', () => {
        const socialOrganization: ISocialOrganization = { id: 123 };
        expectedResult = service.addSocialOrganizationToCollectionIfMissing([], socialOrganization);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(socialOrganization);
      });

      it('should not add a SocialOrganization to an array that contains it', () => {
        const socialOrganization: ISocialOrganization = { id: 123 };
        const socialOrganizationCollection: ISocialOrganization[] = [
          {
            ...socialOrganization,
          },
          { id: 456 },
        ];
        expectedResult = service.addSocialOrganizationToCollectionIfMissing(socialOrganizationCollection, socialOrganization);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SocialOrganization to an array that doesn't contain it", () => {
        const socialOrganization: ISocialOrganization = { id: 123 };
        const socialOrganizationCollection: ISocialOrganization[] = [{ id: 456 }];
        expectedResult = service.addSocialOrganizationToCollectionIfMissing(socialOrganizationCollection, socialOrganization);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(socialOrganization);
      });

      it('should add only unique SocialOrganization to an array', () => {
        const socialOrganizationArray: ISocialOrganization[] = [{ id: 123 }, { id: 456 }, { id: 31384 }];
        const socialOrganizationCollection: ISocialOrganization[] = [{ id: 123 }];
        expectedResult = service.addSocialOrganizationToCollectionIfMissing(socialOrganizationCollection, ...socialOrganizationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const socialOrganization: ISocialOrganization = { id: 123 };
        const socialOrganization2: ISocialOrganization = { id: 456 };
        expectedResult = service.addSocialOrganizationToCollectionIfMissing([], socialOrganization, socialOrganization2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(socialOrganization);
        expect(expectedResult).toContain(socialOrganization2);
      });

      it('should accept null and undefined values', () => {
        const socialOrganization: ISocialOrganization = { id: 123 };
        expectedResult = service.addSocialOrganizationToCollectionIfMissing([], null, socialOrganization, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(socialOrganization);
      });

      it('should return initial array if no SocialOrganization is added', () => {
        const socialOrganizationCollection: ISocialOrganization[] = [{ id: 123 }];
        expectedResult = service.addSocialOrganizationToCollectionIfMissing(socialOrganizationCollection, undefined, null);
        expect(expectedResult).toEqual(socialOrganizationCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
