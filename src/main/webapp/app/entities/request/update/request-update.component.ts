import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IRequest, Request } from '../request.model';
import { RequestService } from '../service/request.service';
import { IApplicationUser } from 'app/entities/application-user/application-user.model';
import { ApplicationUserService } from 'app/entities/application-user/service/application-user.service';
import { ICampaign } from 'app/entities/campaign/campaign.model';
import { CampaignService } from 'app/entities/campaign/service/campaign.service';
import { RequestStatus } from 'app/entities/enumerations/request-status.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-request-update',
  templateUrl: './request-update.component.html',
})
export class RequestUpdateComponent implements OnInit {
  isSaving = false;
  requestStatusValues = Object.keys(RequestStatus);
  currentUser: any;

  applicationUsersSharedCollection: IApplicationUser[] = [];
  campaignsSharedCollection: ICampaign[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    owner: [],
    compaign: [],
  });

  constructor(
    protected requestService: RequestService,
    protected applicationUserService: ApplicationUserService,
    protected campaignService: CampaignService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private accountService: AccountService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ request }) => {
      this.updateForm(request);

      this.loadRelationshipsOptions();
    });

    this.accountService.getAuthenticationState().subscribe(data => {
      this.currentUser = data;
      console.log(this.currentUser);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const request = this.createFromForm();
    console.log('result', request);
    if (request.id !== undefined) {
      this.subscribeToSaveResponse(this.requestService.update(request));
    } else {
      this.subscribeToSaveResponse(this.requestService.create(request));
    }
  }

  trackApplicationUserById(index: number, item: IApplicationUser): number {
    return item.id!;
  }

  trackCampaignById(index: number, item: ICampaign): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRequest>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(data => {
      if (data.body) {
        console.log('request saved', data);
        this.router.navigate(['/time-sheet/new/', data.body?.id]);
      }
    });
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(request: IRequest): void {
    this.editForm.patchValue({
      id: request.id,
      status: request.status,
      owner: request.owner,
      compaign: request.compaign,
    });

    this.applicationUsersSharedCollection = this.applicationUserService.addApplicationUserToCollectionIfMissing(
      this.applicationUsersSharedCollection,
      request.owner
    );
    this.campaignsSharedCollection = this.campaignService.addCampaignToCollectionIfMissing(
      this.campaignsSharedCollection,
      request.compaign
    );
  }

  protected loadRelationshipsOptions(): void {
    this.applicationUserService
      .query()
      .pipe(map((res: HttpResponse<IApplicationUser[]>) => res.body ?? []))
      .pipe(
        map((applicationUsers: IApplicationUser[]) =>
          this.applicationUserService.addApplicationUserToCollectionIfMissing(applicationUsers, this.editForm.get('owner')!.value)
        )
      )
      .subscribe((applicationUsers: IApplicationUser[]) => (this.applicationUsersSharedCollection = applicationUsers));

    this.campaignService
      .query()
      .pipe(map((res: HttpResponse<ICampaign[]>) => res.body ?? []))
      .pipe(
        map((campaigns: ICampaign[]) =>
          this.campaignService.addCampaignToCollectionIfMissing(campaigns, this.editForm.get('compaign')!.value)
        )
      )
      .subscribe((campaigns: ICampaign[]) => (this.campaignsSharedCollection = campaigns));
  }

  protected createFromForm(): IRequest {
    return {
      ...new Request(),
      id: this.editForm.get(['id'])!.value,
      status: RequestStatus.CREATED,
      owner: this.currentUser,
      compaign: this.editForm.get(['compaign'])!.value,
    };
  }
}
