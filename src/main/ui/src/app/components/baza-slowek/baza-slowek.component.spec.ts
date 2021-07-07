import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BazaSlowekComponent } from './baza-slowek.component';

describe('BazaSlowekComponent', () => {
  let component: BazaSlowekComponent;
  let fixture: ComponentFixture<BazaSlowekComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BazaSlowekComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BazaSlowekComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
