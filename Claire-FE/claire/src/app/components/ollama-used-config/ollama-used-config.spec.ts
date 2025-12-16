import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OllamaUsedConfig } from './ollama-used-config';

describe('OllamaUsedConfig', () => {
  let component: OllamaUsedConfig;
  let fixture: ComponentFixture<OllamaUsedConfig>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OllamaUsedConfig]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OllamaUsedConfig);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
