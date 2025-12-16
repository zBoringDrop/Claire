import { TestBed } from '@angular/core/testing';

import { Userollamaconfigservice } from './userollamaconfigservice';

describe('Userollamaconfigservice', () => {
  let service: Userollamaconfigservice;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Userollamaconfigservice);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
