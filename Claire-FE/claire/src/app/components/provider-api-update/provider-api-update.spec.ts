import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProviderApiUpdate } from './provider-api-update';

describe('ProviderApiUpdate', () => {
  let component: ProviderApiUpdate;
  let fixture: ComponentFixture<ProviderApiUpdate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProviderApiUpdate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProviderApiUpdate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
