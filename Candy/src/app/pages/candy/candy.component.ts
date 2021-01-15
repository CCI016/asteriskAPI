import { Component, OnInit } from '@angular/core';
// import { WebRequestService } from 'src/app/web-request.service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-candy',
  templateUrl: './candy.component.html',
  styleUrls: ['./candy.component.css']
})

export class CandyComponent implements OnInit {
  lol;
  constructor(private http: HttpClient) {
  }
  // private webRequestService : WebRequestService
  ngOnInit(): void {
    this.lol = this.getCandy();
    // console.log(this.lol);
  }

  getCandy() {
    console.log(this.http.get('http://localhost:8080/getCandy/list'));
    return this.http.get('http://localhost:8080/getCandy/list'); 
  }
}
