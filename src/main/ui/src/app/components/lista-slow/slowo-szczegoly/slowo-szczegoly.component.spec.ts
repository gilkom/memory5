import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SlowoSzczegolyComponent } from './slowo-szczegoly.component';

describe('SlowoSzczegolyComponent', () => {
  let component: SlowoSzczegolyComponent;
  let fixture: ComponentFixture<SlowoSzczegolyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SlowoSzczegolyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SlowoSzczegolyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
