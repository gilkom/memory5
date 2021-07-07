import { TestBed } from '@angular/core/testing';

import { Memory5Service } from './memory5.service';

describe('Memory5Service', () => {
  let service: Memory5Service;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Memory5Service);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
