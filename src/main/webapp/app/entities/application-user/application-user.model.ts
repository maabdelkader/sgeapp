import { IUser } from 'app/entities/user/user.model';
import { IRequest } from 'app/entities/request/request.model';
import { ICampaign } from 'app/entities/campaign/campaign.model';
import { ICompany } from 'app/entities/company/company.model';

export interface IApplicationUser {
  id?: number;
  internalUser?: IUser | null;
  requests?: IRequest[] | null;
  campaigns?: ICampaign[] | null;
  company?: ICompany | null;
}

export class ApplicationUser implements IApplicationUser {
  constructor(
    public id?: number,
    public internalUser?: IUser | null,
    public requests?: IRequest[] | null,
    public campaigns?: ICampaign[] | null,
    public company?: ICompany | null
  ) {}
}

export function getApplicationUserIdentifier(applicationUser: IApplicationUser): number | undefined {
  return applicationUser.id;
}
