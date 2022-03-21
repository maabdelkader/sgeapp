import dayjs from 'dayjs/esm';
import { IRequest } from 'app/entities/request/request.model';
import { IApplicationUser } from 'app/entities/application-user/application-user.model';

export interface ICampaign {
  id?: number;
  name?: string | null;
  dateFrom?: dayjs.Dayjs | null;
  dateTo?: dayjs.Dayjs | null;
  description?: string | null;
  requests?: IRequest[] | null;
  admin?: IApplicationUser | null;
}

export class Campaign implements ICampaign {
  constructor(
    public id?: number,
    public name?: string | null,
    public dateFrom?: dayjs.Dayjs | null,
    public dateTo?: dayjs.Dayjs | null,
    public description?: string | null,
    public requests?: IRequest[] | null,
    public admin?: IApplicationUser | null
  ) {}
}

export function getCampaignIdentifier(campaign: ICampaign): number | undefined {
  return campaign.id;
}
