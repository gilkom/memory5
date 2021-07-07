import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BazaSlowekComponent } from './components/baza-slowek/baza-slowek.component';
import { StronaGlownaComponent } from './components/strona-glowna/strona-glowna.component';

const routes: Routes = [
  {path: '', redirectTo: '', pathMatch: 'full'},
  {path:'slowka', component: BazaSlowekComponent},
  {path:'glowna', component: StronaGlownaComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
