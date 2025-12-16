import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AiModelCard } from './ai-model-card';

describe('AiModelCard', () => {
  let component: AiModelCard;
  let fixture: ComponentFixture<AiModelCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AiModelCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AiModelCard);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
