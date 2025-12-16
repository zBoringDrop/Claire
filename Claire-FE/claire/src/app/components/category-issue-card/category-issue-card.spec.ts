import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryIssueCard } from './category-issue-card';

describe('CategoryAnalysisCard', () => {
  let component: CategoryIssueCard;
  let fixture: ComponentFixture<CategoryIssueCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryIssueCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryIssueCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
