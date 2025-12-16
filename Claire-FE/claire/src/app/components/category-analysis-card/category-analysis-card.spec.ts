import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryAnalysisCard } from './category-analysis-card';

describe('CategoryAnalysisCard', () => {
  let component: CategoryAnalysisCard;
  let fixture: ComponentFixture<CategoryAnalysisCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryAnalysisCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryAnalysisCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
