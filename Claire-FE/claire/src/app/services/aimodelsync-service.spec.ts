import { TestBed } from '@angular/core/testing';

import { AimodelsyncService } from './aimodelsync-service';

describe('AimodelsyncService', () => {
  let service: AimodelsyncService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AimodelsyncService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
