import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OllamaConfigurator } from './ollama-configurator';

describe('OllamaConfigurator', () => {
  let component: OllamaConfigurator;
  let fixture: ComponentFixture<OllamaConfigurator>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OllamaConfigurator]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OllamaConfigurator);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
