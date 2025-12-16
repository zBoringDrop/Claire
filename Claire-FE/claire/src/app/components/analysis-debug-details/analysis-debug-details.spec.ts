import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisDebugDetails } from './analysis-debug-details';

describe('AnalysisDebugDetails', () => {
  let component: AnalysisDebugDetails;
  let fixture: ComponentFixture<AnalysisDebugDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalysisDebugDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalysisDebugDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
