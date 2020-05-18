import { TestBed } from '@angular/core/testing';

import { CityServiceService } from './city-service.service';

describe('CityServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CityServiceService = TestBed.get(CityServiceService);
    expect(service).toBeTruthy();
  });
});
