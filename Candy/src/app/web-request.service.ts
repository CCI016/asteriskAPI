import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class WebRequestService {
  readonly ROOT_URL =  'http://localhost:8080';

  constructor(private http: HttpClient) {
  }

  getInfo(uri : string) {
    return this.http.get(`${this.ROOT_URL}/${uri}`).toPromise();
  }

//   "/": {
//     "target": "http://localhost:8080",
//     "secure": false,
//     "ws": true
// }
}
