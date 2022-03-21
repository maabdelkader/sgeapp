import { ISocialOrganization } from 'app/entities/social-organization/social-organization.model';
import { IApplicationUser } from 'app/entities/application-user/application-user.model';
import { ITimeSheet } from 'app/entities/time-sheet/time-sheet.model';
import { CompanyType } from 'app/entities/enumerations/company-type.model';

export interface ICompany {
  id?: number;
  name?: string | null;
  raisonSocial?: string | null;
  companyType?: CompanyType | null;
  socialOrganization?: ISocialOrganization | null;
  applicationUsers?: IApplicationUser[] | null;
  timeSheets?: ITimeSheet[] | null;
}

export class Company implements ICompany {
  constructor(
    public id?: number,
    public name?: string | null,
    public raisonSocial?: string | null,
    public companyType?: CompanyType | null,
    public socialOrganization?: ISocialOrganization | null,
    public applicationUsers?: IApplicationUser[] | null,
    public timeSheets?: ITimeSheet[] | null
  ) {}
}

export function getCompanyIdentifier(company: ICompany): number | undefined {
  return company.id;
}
