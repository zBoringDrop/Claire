import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnippetDetails } from './snippet-details';

describe('SnippetDetails', () => {
  let component: SnippetDetails;
  let fixture: ComponentFixture<SnippetDetails>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SnippetDetails]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SnippetDetails);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
