import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaSlowComponent } from './lista-slow.component';

describe('ListaSlowComponent', () => {
  let component: ListaSlowComponent;
  let fixture: ComponentFixture<ListaSlowComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListaSlowComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaSlowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
