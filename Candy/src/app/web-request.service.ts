import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Candy } from './pages/models/candy';

@Injectable({
  providedIn: 'root'
})
export class WebRequestService {
  readonly ROOT_URL =  'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  getData(uri : string) {
    return this.http.get(`${this.ROOT_URL}/${uri}`);
  }

  postData(uri : string, t : any) : Observable<any> {
    return this.http.post(`${this.ROOT_URL}/${uri}`, t);
  }

}
