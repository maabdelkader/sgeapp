import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISocialOrganization } from '../social-organization.model';

@Component({
  selector: 'jhi-social-organization-detail',
  templateUrl: './social-organization-detail.component.html',
})
export class SocialOrganizationDetailComponent implements OnInit {
  socialOrganization: ISocialOrganization | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ socialOrganization }) => {
      this.socialOrganization = socialOrganization;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
