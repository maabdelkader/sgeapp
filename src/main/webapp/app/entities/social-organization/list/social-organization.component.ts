import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocialOrganization } from '../social-organization.model';
import { SocialOrganizationService } from '../service/social-organization.service';
import { SocialOrganizationDeleteDialogComponent } from '../delete/social-organization-delete-dialog.component';

@Component({
  selector: 'jhi-social-organization',
  templateUrl: './social-organization.component.html',
})
export class SocialOrganizationComponent implements OnInit {
  socialOrganizations?: ISocialOrganization[];
  isLoading = false;

  constructor(protected socialOrganizationService: SocialOrganizationService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.socialOrganizationService.query().subscribe({
      next: (res: HttpResponse<ISocialOrganization[]>) => {
        this.isLoading = false;
        this.socialOrganizations = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ISocialOrganization): number {
    return item.id!;
  }

  delete(socialOrganization: ISocialOrganization): void {
    const modalRef = this.modalService.open(SocialOrganizationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.socialOrganization = socialOrganization;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
