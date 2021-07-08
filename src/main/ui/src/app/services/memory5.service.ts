import { Injectable } from '@angular/core';
import {HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Slowo } from '../models/memory5.model';

const baseUrl = 'http://localhost:8080/baza_slowek';

@Injectable({
  providedIn: 'root'
})

export class Memory5Service {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Slowo[]> {
    return this.http.get<Slowo[]>(baseUrl);
  }

  get(id: any): Observable<Slowo>{
    return this.http.get('${baseUrl}/${id}');
  }

  create(data: any): Observable<any> {
    return this.http.post(baseUrl, data);
  }

  deleteAll(): Observable<any>{
    return this.http.delete(baseUrl);
  }

  findBySlowo(slowo: any): Observable<Slowo[]>{
    return this.http.get<Slowo[]>('${baseUrl}?title=${title}');
  }
}
