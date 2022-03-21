import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompany, Company } from '../company.model';
import { CompanyService } from '../service/company.service';
import { ISocialOrganization } from 'app/entities/social-organization/social-organization.model';
import { SocialOrganizationService } from 'app/entities/social-organization/service/social-organization.service';
import { CompanyType } from 'app/entities/enumerations/company-type.model';

@Component({
  selector: 'jhi-company-update',
  templateUrl: './company-update.component.html',
})
export class CompanyUpdateComponent implements OnInit {
  isSaving = false;
  companyTypeValues = Object.keys(CompanyType);

  socialOrganizationsCollection: ISocialOrganization[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    raisonSocial: [],
    companyType: [],
    socialOrganization: [],
  });

  constructor(
    protected companyService: CompanyService,
    protected socialOrganizationService: SocialOrganizationService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ company }) => {
      this.updateForm(company);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const company = this.createFromForm();
    if (company.id !== undefined) {
      this.subscribeToSaveResponse(this.companyService.update(company));
    } else {
      this.subscribeToSaveResponse(this.companyService.create(company));
    }
  }

  trackSocialOrganizationById(index: number, item: ISocialOrganization): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompany>>): void {
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

  protected updateForm(company: ICompany): void {
    this.editForm.patchValue({
      id: company.id,
      name: company.name,
      raisonSocial: company.raisonSocial,
      companyType: company.companyType,
      socialOrganization: company.socialOrganization,
    });

    this.socialOrganizationsCollection = this.socialOrganizationService.addSocialOrganizationToCollectionIfMissing(
      this.socialOrganizationsCollection,
      company.socialOrganization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.socialOrganizationService
      .query({ filter: 'company-is-null' })
      .pipe(map((res: HttpResponse<ISocialOrganization[]>) => res.body ?? []))
      .pipe(
        map((socialOrganizations: ISocialOrganization[]) =>
          this.socialOrganizationService.addSocialOrganizationToCollectionIfMissing(
            socialOrganizations,
            this.editForm.get('socialOrganization')!.value
          )
        )
      )
      .subscribe((socialOrganizations: ISocialOrganization[]) => (this.socialOrganizationsCollection = socialOrganizations));
  }

  protected createFromForm(): ICompany {
    return {
      ...new Company(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      raisonSocial: this.editForm.get(['raisonSocial'])!.value,
      companyType: this.editForm.get(['companyType'])!.value,
      socialOrganization: this.editForm.get(['socialOrganization'])!.value,
    };
  }
}
