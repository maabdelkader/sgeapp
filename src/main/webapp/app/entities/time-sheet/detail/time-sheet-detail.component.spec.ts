import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TimeSheetDetailComponent } from './time-sheet-detail.component';

describe('TimeSheet Management Detail Component', () => {
  let comp: TimeSheetDetailComponent;
  let fixture: ComponentFixture<TimeSheetDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TimeSheetDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ timeSheet: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TimeSheetDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TimeSheetDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load timeSheet on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.timeSheet).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
