import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITimeSheet, TimeSheet } from '../time-sheet.model';
import { TimeSheetService } from '../service/time-sheet.service';
import { IRequest } from 'app/entities/request/request.model';
import { RequestService } from 'app/entities/request/service/request.service';
import { ICompany } from 'app/entities/company/company.model';
import { CompanyService } from 'app/entities/company/service/company.service';
import { TimeSheetStatus } from 'app/entities/enumerations/time-sheet-status.model';

@Component({
  selector: 'jhi-time-sheet-update',
  templateUrl: './time-sheet-update.component.html',
})
export class TimeSheetUpdateComponent implements OnInit {
  isSaving = false;
  timeSheetStatusValues = Object.keys(TimeSheetStatus);

  requestsSharedCollection: IRequest[] = [];
  companiesSharedCollection: ICompany[] = [];

  editForm = this.fb.group({
    id: [],
    employeeCivility: [],
    employeeFirstName: [],
    employeeLastName: [],
    registryNumber: [],
    dateFrom: [],
    dateTo: [],
    direction: [],
    division: [],
    um: [],
    status: [],
    ccas: [],
    nbHoursCcas: [],
    coordinatingCommittee: [],
    nbHoursCdc: [],
    nbHoursAdmin: [],
    nbHoursPotFdCfdt: [],
    nbHoursPotFdCgt: [],
    nbHoursPotFdFo: [],
    nbHoursPotFdCfeCgc: [],
    commissionType: [],
    nbHoursCommision: [],
    proximityType: [],
    nbHoursProximity: [],
    request: [],
    company: [],
  });

  constructor(
    protected timeSheetService: TimeSheetService,
    protected requestService: RequestService,
    protected companyService: CompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ timeSheet }) => {
      this.updateForm(timeSheet);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const timeSheet = this.createFromForm();
    if (timeSheet.id !== undefined) {
      this.subscribeToSaveResponse(this.timeSheetService.update(timeSheet));
    } else {
      this.subscribeToSaveResponse(this.timeSheetService.create(timeSheet));
    }
  }

  trackRequestById(index: number, item: IRequest): number {
    return item.id!;
  }

  trackCompanyById(index: number, item: ICompany): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITimeSheet>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(timeSheet: ITimeSheet): void {
    this.editForm.patchValue({
      id: timeSheet.id,
      employeeCivility: timeSheet.employeeCivility,
      employeeFirstName: timeSheet.employeeFirstName,
      employeeLastName: timeSheet.employeeLastName,
      registryNumber: timeSheet.registryNumber,
      dateFrom: timeSheet.dateFrom,
      dateTo: timeSheet.dateTo,
      direction: timeSheet.direction,
      division: timeSheet.division,
      um: timeSheet.um,
      status: timeSheet.status,
      ccas: timeSheet.ccas,
      nbHoursCcas: timeSheet.nbHoursCcas,
      coordinatingCommittee: timeSheet.coordinatingCommittee,
      nbHoursCdc: timeSheet.nbHoursCdc,
      nbHoursAdmin: timeSheet.nbHoursAdmin,
      nbHoursPotFdCfdt: timeSheet.nbHoursPotFdCfdt,
      nbHoursPotFdCgt: timeSheet.nbHoursPotFdCgt,
      nbHoursPotFdFo: timeSheet.nbHoursPotFdFo,
      nbHoursPotFdCfeCgc: timeSheet.nbHoursPotFdCfeCgc,
      commissionType: timeSheet.commissionType,
      nbHoursCommision: timeSheet.nbHoursCommision,
      proximityType: timeSheet.proximityType,
      nbHoursProximity: timeSheet.nbHoursProximity,
      request: timeSheet.request,
      company: timeSheet.company,
    });

    this.requestsSharedCollection = this.requestService.addRequestToCollectionIfMissing(this.requestsSharedCollection, timeSheet.request);
    this.companiesSharedCollection = this.companyService.addCompanyToCollectionIfMissing(this.companiesSharedCollection, timeSheet.company);
  }

  protected loadRelationshipsOptions(): void {
    this.requestService
      .query()
      .pipe(map((res: HttpResponse<IRequest[]>) => res.body ?? []))
      .pipe(
        map((requests: IRequest[]) => this.requestService.addRequestToCollectionIfMissing(requests, this.editForm.get('request')!.value))
      )
      .subscribe((requests: IRequest[]) => (this.requestsSharedCollection = requests));

    this.companyService
      .query()
      .pipe(map((res: HttpResponse<ICompany[]>) => res.body ?? []))
      .pipe(
        map((companies: ICompany[]) => this.companyService.addCompanyToCollectionIfMissing(companies, this.editForm.get('company')!.value))
      )
      .subscribe((companies: ICompany[]) => (this.companiesSharedCollection = companies));
  }

  protected createFromForm(): ITimeSheet {
    return {
      ...new TimeSheet(),
      id: this.editForm.get(['id'])!.value,
      employeeCivility: this.editForm.get(['employeeCivility'])!.value,
      employeeFirstName: this.editForm.get(['employeeFirstName'])!.value,
      employeeLastName: this.editForm.get(['employeeLastName'])!.value,
      registryNumber: this.editForm.get(['registryNumber'])!.value,
      dateFrom: this.editForm.get(['dateFrom'])!.value,
      dateTo: this.editForm.get(['dateTo'])!.value,
      direction: this.editForm.get(['direction'])!.value,
      division: this.editForm.get(['division'])!.value,
      um: this.editForm.get(['um'])!.value,
      status: this.editForm.get(['status'])!.value,
      ccas: this.editForm.get(['ccas'])!.value,
      nbHoursCcas: this.editForm.get(['nbHoursCcas'])!.value,
      coordinatingCommittee: this.editForm.get(['coordinatingCommittee'])!.value,
      nbHoursCdc: this.editForm.get(['nbHoursCdc'])!.value,
      nbHoursAdmin: this.editForm.get(['nbHoursAdmin'])!.value,
      nbHoursPotFdCfdt: this.editForm.get(['nbHoursPotFdCfdt'])!.value,
      nbHoursPotFdCgt: this.editForm.get(['nbHoursPotFdCgt'])!.value,
      nbHoursPotFdFo: this.editForm.get(['nbHoursPotFdFo'])!.value,
      nbHoursPotFdCfeCgc: this.editForm.get(['nbHoursPotFdCfeCgc'])!.value,
      commissionType: this.editForm.get(['commissionType'])!.value,
      nbHoursCommision: this.editForm.get(['nbHoursCommision'])!.value,
      proximityType: this.editForm.get(['proximityType'])!.value,
      nbHoursProximity: this.editForm.get(['nbHoursProximity'])!.value,
      request: this.editForm.get(['request'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }
}
