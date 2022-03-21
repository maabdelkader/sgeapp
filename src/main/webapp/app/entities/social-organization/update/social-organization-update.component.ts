import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISocialOrganization, SocialOrganization } from '../social-organization.model';
import { SocialOrganizationService } from '../service/social-organization.service';

@Component({
  selector: 'jhi-social-organization-update',
  templateUrl: './social-organization-update.component.html',
})
export class SocialOrganizationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    adminQuota: [],
    commissionQuota: [],
    proximityQuota: [],
    numberOfAdmins: [],
  });

  constructor(
    protected socialOrganizationService: SocialOrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ socialOrganization }) => {
      this.updateForm(socialOrganization);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const socialOrganization = this.createFromForm();
    if (socialOrganization.id !== undefined) {
      this.subscribeToSaveResponse(this.socialOrganizationService.update(socialOrganization));
    } else {
      this.subscribeToSaveResponse(this.socialOrganizationService.create(socialOrganization));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISocialOrganization>>): void {
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

  protected updateForm(socialOrganization: ISocialOrganization): void {
    this.editForm.patchValue({
      id: socialOrganization.id,
      name: socialOrganization.name,
      adminQuota: socialOrganization.adminQuota,
      commissionQuota: socialOrganization.commissionQuota,
      proximityQuota: socialOrganization.proximityQuota,
      numberOfAdmins: socialOrganization.numberOfAdmins,
    });
  }

  protected createFromForm(): ISocialOrganization {
    return {
      ...new SocialOrganization(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      adminQuota: this.editForm.get(['adminQuota'])!.value,
      commissionQuota: this.editForm.get(['commissionQuota'])!.value,
      proximityQuota: this.editForm.get(['proximityQuota'])!.value,
      numberOfAdmins: this.editForm.get(['numberOfAdmins'])!.value,
    };
  }
}
