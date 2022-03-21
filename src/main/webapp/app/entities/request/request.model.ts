import { ITimeSheet } from 'app/entities/time-sheet/time-sheet.model';
import { IApplicationUser } from 'app/entities/application-user/application-user.model';
import { ICampaign } from 'app/entities/campaign/campaign.model';
import { RequestStatus } from 'app/entities/enumerations/request-status.model';

export interface IRequest {
  id?: number;
  status?: RequestStatus | null;
  timeSheets?: ITimeSheet[] | null;
  owner?: IApplicationUser | null;
  compaign?: ICampaign | null;
}

export class Request implements IRequest {
  constructor(
    public id?: number,
    public status?: RequestStatus | null,
    public timeSheets?: ITimeSheet[] | null,
    public owner?: IApplicationUser | null,
    public compaign?: ICampaign | null
  ) {}
}

export function getRequestIdentifier(request: IRequest): number | undefined {
  return request.id;
}
