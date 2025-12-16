import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SnippetUploader } from './snippet-uploader';

describe('SnippetUploader', () => {
  let component: SnippetUploader;
  let fixture: ComponentFixture<SnippetUploader>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SnippetUploader]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SnippetUploader);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
