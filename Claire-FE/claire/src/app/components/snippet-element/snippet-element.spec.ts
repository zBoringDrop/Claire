import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnippetElement } from './snippet-element';

describe('SnippetElement', () => {
  let component: SnippetElement;
  let fixture: ComponentFixture<SnippetElement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SnippetElement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SnippetElement);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
