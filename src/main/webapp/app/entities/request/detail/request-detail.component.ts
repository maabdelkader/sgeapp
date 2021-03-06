import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { IRequest } from '../request.model';

@Component({
  selector: 'jhi-request-detail',
  templateUrl: './request-detail.component.html',
})
export class RequestDetailComponent implements OnInit {
  request: IRequest | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ request }) => {
      this.request = request;
    });
  }

  navigateToTimehseetForm() {
    this.router.navigate(['/time-sheet/new/', this.request?.id]);
  }

  navigateToRequestTimesheets() {
    this.router.navigate([this.router.url + '/timesheets']);
  }

  previousState(): void {
    window.history.back();
  }
}
