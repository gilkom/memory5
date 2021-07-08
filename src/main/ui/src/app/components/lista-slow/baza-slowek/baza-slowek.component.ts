import { Component, OnInit } from '@angular/core';
import { Slowo } from 'src/app/models/memory5.model';
import { Memory5Service } from 'src/app/services/memory5.service';

@Component({
  selector: 'app-baza-slowek',
  templateUrl: './baza-slowek.component.html',
  styleUrls: ['./baza-slowek.component.css']
})
export class BazaSlowekComponent implements OnInit {

  slowa?: Slowo[];
  aktualneSlowo: Slowo = {};
  aktualnyIndex = -1;
  slowo = '';

  constructor(private memory5Service: Memory5Service) { }

  ngOnInit(): void {
    this.retrieveSlowa();
  }

  retrieveSlowa(): void{
    this.memory5Service.getAll()
      .subscribe(
        data => {
          this.slowa = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }
  refreshList(): void{
    this.retrieveSlowa();
    this.aktualneSlowo = {};
    this.aktualnyIndex = -1;
  }

  setActiveSlowo(slowo: Slowo, index: number): void{
    this.aktualneSlowo = slowo;
    this.aktualnyIndex = index;
  }

  removeAllSlowa(): void {
    this.memory5Service.deleteAll()
      .subscribe(
        response => {
          console.log(response);
          this.refreshList();
        },
        error => {
          console.log(error);
        });
  }

  searchSlowo(): void{
    this.aktualneSlowo = {};
    this.aktualnyIndex = -1;

    this.memory5Service.findBySlowo(this.slowo)
      .subscribe(
        data  => {
          this.slowa = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

}
