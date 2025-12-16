import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisRunning } from './analysis-running';

describe('AnalysisRunning', () => {
  let component: AnalysisRunning;
  let fixture: ComponentFixture<AnalysisRunning>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalysisRunning]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalysisRunning);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
