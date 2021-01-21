import { Component, OnInit } from '@angular/core';
import { WebRequestService } from 'src/app/web-request.service';
import { Candy } from '../models/candy';
import { Provider } from '../models/provider';

@Component({
  selector: 'app-candy',
  templateUrl: './candy.component.html',
  styleUrls: ['./candy.component.css']
})

export class CandyComponent implements OnInit {
  request : string;
  candy : Candy[];
  providers : Provider[];
  editedCandy : Candy;
  visible : boolean;
  previousProvider : number;
  previuosName : string;


  constructor(private webService : WebRequestService) {
    this.editedCandy = {} as Candy;
    this.visible = false;
    this.candy = [];
    this.providers = [];
    this.request = "getCandy/list";
  }

  ngOnInit(): void {
    this.getAllCandy();
    this.getAllProviders();
  }


  getAllCandy() {
    this.candy = [];
    this.request = "getCandy/list";
    this.webService.getData(this.request).subscribe(data => {
      this.candy = <Candy[]> data;
    });
  }

  getAllProviders() {
    this.providers = [];
    this.request = "getCandy/providers";
    this.webService.getData(this.request).subscribe(data => this.providers = <Provider[]> data);
    console.log(this.request);
  }

  getNewData(providerID : number, candyName : string) {
    this.candy = [];
    this.request = "getCandy/filterByNameAndProvider";

    if (providerID != null || candyName != "") {
      this.request += "?";

      this.request += `${(candyName != "") ? ("name=" + candyName) : ""}`;
      this.request = this.andOperator(this.request, providerID);
      this.request += `${(providerID != null) ? ("provider=" + providerID) : ""}`;
    } else {
      this.request = "getCandy/list";
    }

    this.webService.getData(this.request).subscribe(data => this.candy = <Candy[]> data);
  }

  andOperator(request : string, param : any) {
    if((param != "" && param != 0 && param != null) && (request.substr(request.length - 1) != "?"))
      request += "&";
    return request;
  }

  close() {
    this.visible = false;
  }

  edit(candyElem : Candy) {
    this.editedCandy = candyElem;
    this.previousProvider = candyElem.provider.id;
    this.previuosName = candyElem.name;
    console.log(this.previuosName);
    this.visible = true;
  }

  submitEdit(newName : string, newProviderID : number, candyID : number) {
    this.request = "getCandy/updateCandy"
    var status = {} as Candy;

    this.webService.postData(this.request, {id : candyID, name : newName, provider : newProviderID}).subscribe((data) => {
       status = <Candy> data;
       this.getAllCandy();});
    this.close();
  }

  sortName = (a: Candy, b: Candy) => a.name.localeCompare(b.name);
  sortListened = (a : Candy, b : Candy) => b.prompt.numberOfPlays - a.prompt.numberOfPlays; 
}