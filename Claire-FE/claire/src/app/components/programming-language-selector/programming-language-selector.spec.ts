import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgrammingLanguageSelector } from './programming-language-selector';

describe('ProgrammingLanguageSelector', () => {
  let component: ProgrammingLanguageSelector;
  let fixture: ComponentFixture<ProgrammingLanguageSelector>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgrammingLanguageSelector]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProgrammingLanguageSelector);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
