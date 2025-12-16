import { TestBed } from '@angular/core/testing';

import { CodesnippetService } from './codesnippet-service';

describe('CodesnippetService', () => {
  let service: CodesnippetService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CodesnippetService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
