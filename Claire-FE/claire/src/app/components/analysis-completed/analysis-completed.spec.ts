import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisCompleted } from './analysis-completed';

describe('AnalysisCompleted', () => {
  let component: AnalysisCompleted;
  let fixture: ComponentFixture<AnalysisCompleted>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalysisCompleted]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalysisCompleted);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
