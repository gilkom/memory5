import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DodajSlowoComponent } from './dodaj-slowo.component';

describe('DodajSlowoComponent', () => {
  let component: DodajSlowoComponent;
  let fixture: ComponentFixture<DodajSlowoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DodajSlowoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DodajSlowoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
