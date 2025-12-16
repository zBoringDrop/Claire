import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileElement } from './file-element';

describe('FileElement', () => {
  let component: FileElement;
  let fixture: ComponentFixture<FileElement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FileElement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FileElement);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
