import { TestBed } from '@angular/core/testing';

import { UserkeyproviderService } from './userkeyprovider-service';

describe('UserkeyproviderService', () => {
  let service: UserkeyproviderService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserkeyproviderService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
