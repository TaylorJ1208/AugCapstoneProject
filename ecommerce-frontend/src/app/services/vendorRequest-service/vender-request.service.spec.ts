import { TestBed } from '@angular/core/testing';

import { VenderRequestService } from './vender-request.service';

describe('VenderRequestService', () => {
  let service: VenderRequestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VenderRequestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
