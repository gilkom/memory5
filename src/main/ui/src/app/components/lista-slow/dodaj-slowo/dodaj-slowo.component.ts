import { Component, OnInit } from '@angular/core';
import { Slowo } from 'src/app/models/memory5.model';
import { Memory5Service } from 'src/app/services/memory5.service';

@Component({
  selector: 'app-dodaj-slowo',
  templateUrl: './dodaj-slowo.component.html',
  styleUrls: ['./dodaj-slowo.component.css']
})
export class DodajSlowoComponent implements OnInit {

  slowo: Slowo = {
    slowo: '',
    tlumaczenie: '',
    rodzaj: '',
    dzwiek: '',
    obraz: ''
  };
  submitted = false;

  constructor(private memory5Service: Memory5Service) { }

  ngOnInit(): void {
  }

  saveSlowo(): void{
    const data = {
      slowo: this.slowo.slowo,
      tlumaczenie: this.slowo.tlumaczenie,
      rodzaj: this.slowo.rodzaj,
      dzwiek: this.slowo.dzwiek,
      obraz: this.slowo.obraz
    };

    this.memory5Service.create(data)
      .subscribe(
        response => {
          console.log(response);
          this.submitted =  true;
        },
        error => {
          console.log(error);
        });
  }

  newSlowo(): void{
    this.submitted = false;
    this.slowo = {
      slowo: '',
      tlumaczenie: '',
      rodzaj: '',
      dzwiek: '',
      obraz: ''
    };
  }

}
