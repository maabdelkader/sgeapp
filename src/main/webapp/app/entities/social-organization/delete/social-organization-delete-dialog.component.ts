import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISocialOrganization } from '../social-organization.model';
import { SocialOrganizationService } from '../service/social-organization.service';

@Component({
  templateUrl: './social-organization-delete-dialog.component.html',
})
export class SocialOrganizationDeleteDialogComponent {
  socialOrganization?: ISocialOrganization;

  constructor(protected socialOrganizationService: SocialOrganizationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.socialOrganizationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
