import { TestBed } from '@angular/core/testing';

import { ProgramminglanguageService } from './programminglanguage-service';

describe('ProgramminglanguageService', () => {
  let service: ProgramminglanguageService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProgramminglanguageService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
