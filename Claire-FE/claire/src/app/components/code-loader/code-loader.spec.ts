import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeLoader } from './code-loader';

describe('CodeLoader', () => {
  let component: CodeLoader;
  let fixture: ComponentFixture<CodeLoader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CodeLoader]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CodeLoader);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
