import dayjs from 'dayjs/esm';
import { IRequest } from 'app/entities/request/request.model';
import { ICompany } from 'app/entities/company/company.model';
import { TimeSheetStatus } from 'app/entities/enumerations/time-sheet-status.model';

export interface ITimeSheet {
  id?: number;
  employeeCivility?: string | null;
  employeeFirstName?: string | null;
  employeeLastName?: string | null;
  registryNumber?: string | null;
  dateFrom?: dayjs.Dayjs | null;
  dateTo?: dayjs.Dayjs | null;
  direction?: string | null;
  division?: string | null;
  um?: string | null;
  status?: TimeSheetStatus | null;
  ccas?: string | null;
  nbHoursCcas?: number | null;
  coordinatingCommittee?: string | null;
  nbHoursCdc?: number | null;
  nbHoursAdmin?: number | null;
  nbHoursPotFdCfdt?: number | null;
  nbHoursPotFdCgt?: number | null;
  nbHoursPotFdFo?: number | null;
  nbHoursPotFdCfeCgc?: number | null;
  commissionType?: string | null;
  nbHoursCommision?: number | null;
  proximityType?: string | null;
  nbHoursProximity?: number | null;
  request?: IRequest | null;
  company?: ICompany | null;
}

export class TimeSheet implements ITimeSheet {
  constructor(
    public id?: number,
    public employeeCivility?: string | null,
    public employeeFirstName?: string | null,
    public employeeLastName?: string | null,
    public registryNumber?: string | null,
    public dateFrom?: dayjs.Dayjs | null,
    public dateTo?: dayjs.Dayjs | null,
    public direction?: string | null,
    public division?: string | null,
    public um?: string | null,
    public status?: TimeSheetStatus | null,
    public ccas?: string | null,
    public nbHoursCcas?: number | null,
    public coordinatingCommittee?: string | null,
    public nbHoursCdc?: number | null,
    public nbHoursAdmin?: number | null,
    public nbHoursPotFdCfdt?: number | null,
    public nbHoursPotFdCgt?: number | null,
    public nbHoursPotFdFo?: number | null,
    public nbHoursPotFdCfeCgc?: number | null,
    public commissionType?: string | null,
    public nbHoursCommision?: number | null,
    public proximityType?: string | null,
    public nbHoursProximity?: number | null,
    public request?: IRequest | null,
    public company?: ICompany | null
  ) {}
}

export function getTimeSheetIdentifier(timeSheet: ITimeSheet): number | undefined {
  return timeSheet.id;
}
