import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BazaSlowekComponent } from './components/lista-slow/baza-slowek/baza-slowek.component';
import { SlowoSzczegolyComponent } from './components/lista-slow/slowo-szczegoly/slowo-szczegoly.component';
import { DodajSlowoComponent } from './components/lista-slow/dodaj-slowo/dodaj-slowo.component';
import { ListaSlowComponent } from './components/lista-slow/lista-slow.component';
import { StronaGlownaComponent } from './components/strona-glowna/strona-glowna.component';

const routes: Routes = [
  {path: '', redirectTo: '', pathMatch: 'full'},
  {path: 'lista_slow', component: ListaSlowComponent, children: [
    {path:'baza_slowek', component: BazaSlowekComponent},
    {path: 'dodaj_slowo', component: DodajSlowoComponent}
  ]},
  {path: 'baza_slowek/:id', component: SlowoSzczegolyComponent},
  {path:'glowna', component: StronaGlownaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
