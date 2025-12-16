import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnalysisFailed } from './analysis-failed';

describe('AnalysisFailed', () => {
  let component: AnalysisFailed;
  let fixture: ComponentFixture<AnalysisFailed>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnalysisFailed]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnalysisFailed);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
