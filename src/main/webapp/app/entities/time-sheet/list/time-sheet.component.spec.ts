import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TimeSheetService } from '../service/time-sheet.service';

import { TimeSheetComponent } from './time-sheet.component';

describe('TimeSheet Management Component', () => {
  let comp: TimeSheetComponent;
  let fixture: ComponentFixture<TimeSheetComponent>;
  let service: TimeSheetService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TimeSheetComponent],
    })
      .overrideTemplate(TimeSheetComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TimeSheetComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TimeSheetService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.timeSheets?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
