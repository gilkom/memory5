import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BazaSlowekComponent } from './components/lista-slow/baza-slowek/baza-slowek.component';
import { StronaGlownaComponent } from './components/strona-glowna/strona-glowna.component';
import { SlowoSzczegolyComponent } from './components/lista-slow/slowo-szczegoly/slowo-szczegoly.component';
import { DodajSlowoComponent } from './components/lista-slow/dodaj-slowo/dodaj-slowo.component';
import { ListaSlowComponent } from './components/lista-slow/lista-slow.component';

@NgModule({
  declarations: [
    AppComponent,
    BazaSlowekComponent,
    StronaGlownaComponent,
    SlowoSzczegolyComponent,
    DodajSlowoComponent,
    ListaSlowComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
