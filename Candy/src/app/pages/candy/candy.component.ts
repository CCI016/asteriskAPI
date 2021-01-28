import { Component, OnInit, AfterViewInit } from '@angular/core';
import { WebRequestService } from 'src/app/web-request.service';
import { Candy } from '../models/candy';
import { Provider } from '../models/provider';
import { NzModalService } from 'ng-zorro-antd/modal';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { NzTableQueryParams } from 'ng-zorro-antd/table';
import { Prompt } from '../models/prompt';

@Component({
  selector: 'app-candy',
  templateUrl: './candy.component.html',
  styleUrls: ['./candy.component.css']
})

export class CandyComponent implements OnInit, AfterViewInit {
  request : string;
  candy : Candy[];
  providers : Provider[];
  editedCandy : Candy;
  visible : boolean;
  previousProvider : Provider;
  previousName : string;
  selectedService : number;
  showDelete : boolean;
  selectedSearchName : string;
  pageSize : number;
  total : number;
  previuosPrompt : Prompt;
  prompts : Prompt[];

  constructor(
    public webService : WebRequestService,
    private modal: NzModalService,
    private notification: NzNotificationService,) {
    this.editedCandy = {} as Candy;
    this.visible = false;
    this.showDelete = false;
    this.candy = [];
    this.providers = [];
    this.previousProvider  = {} as Provider;
    this.request = "getCandy/list";
    this.selectedSearchName = "";
    this.pageSize = 1;
    this.total = 10;
    this.previuosPrompt = {} as Prompt;
    this.prompts = [];

  }

  ngOnInit(): void {
    this.getTotal();
    this.getAllCandy("", "", 1);
    this.getAllProviders();
    this.getAllPrompts();
    this.webService.test();
  }


  ngAfterViewInit() {
    this.getAllCandy("", "", 1);
    this.getAllProviders();
  }

  getTotal() {
    this.request ="getCandy/getTotal"
    this.webService.getData(this.request).subscribe((data) => {this.total = <number> data});
  }

  getAllCandy(sortField : string, sortOrder : string, pageIndex : number) {
    // this.candy = [];
    this.request = "getCandy/list?pageIndex=" + pageIndex + "&sortField=" + sortField + "&pageSize=" + this.pageSize + "&sortingOrder=" + sortOrder;
    this.webService.getData(this.request).subscribe(data => {
     this.candy = <Candy[]> data;
     console.log(this.candy);
    });
  }

  getAllProviders() {
    // this.providers = [];
    this.request = "getCandy/providers";
    this.webService.getData(this.request).subscribe(data => this.providers = <Provider[]> data);
    console.log(this.request);
  }

  getAllPrompts() {
    this.request = "getCandy/prompts"
    this.webService.getData(this.request).subscribe(data => {
      // this.prompts = [];
      this.prompts = <Prompt[]> data;
    })
  }

  getNewData(providerID : number, candyName : string) {
    // this.candy = [];
    this.request = "getCandy/filterByNameAndProvider";

    if (providerID != null || candyName != "") {
      this.request += "?";
      this.request += `${(candyName != "") ? ("name=" + candyName) : ""}`;
      this.request = this.andOperator(this.request, providerID);
      this.request += `${(providerID != null) ? ("provider=" + providerID) : ""}`;
      this.webService.getData(this.request).subscribe(data => this.candy = <Candy[]> data);
    } else {
      this.getAllCandy("", "", 1);
    }

   
  }

  andOperator(request : string, param : any) {
    if((param != "" && param != 0 && param != null) && (request.substr(request.length - 1) != "?"))
      request += "&";
    return request;
  }

  close() {
    this.showDelete = false;
    this.visible = false;
  }

  edit(candyElem : Candy) {
    this.editedCandy = candyElem;
    this.previousProvider = candyElem.provider;
    this.previousName = candyElem.name;
    this.previuosPrompt = candyElem.prompt;
    this.visible = true;
  }

  submitEdit(newName : string, newProviderID : number, candyID : number) {
    this.request = "getCandy/updateCandy"
    var send : {id : number; name : string; provider : number; prompt : number}
    var status = {} as Candy;
    send = {id : candyID, name : newName,provider : newProviderID, prompt : this.previuosPrompt.id};
    this.webService.postData(this.request, send).subscribe((data) => {
       status = <Candy> data;
       this.getNewData(this.selectedService, this.selectedSearchName);;});
    this.close();
  }

  //Displays the delete module, if the answer is yes he will send the post request and after will display a notification
  showDeleteModule(candy : Candy) {
    var status : {isDeleted : boolean};
    this.request = "getCandy/deleteCandy"
    this.modal.confirm({
      nzTitle : "Are you sure?",
      nzContent : "Candy " + candy.name + " will be deleted!", 
      nzOkText : "Yes",
      nzOnOk : () => {
        this.webService.postData(this.request, candy).subscribe((data) => {
         status = data;
         if (data == true) {
           this.notification.success(
            'Success',
            'You succesfully deleted ' + candy.name
           )
         } else if (data == false) {
           this.notification.error(
             'Error',
             'An error occured when trying to delete ' + candy.name + ' . Try again later'
           )
         }
         this.getNewData(this.selectedService, this.selectedSearchName);
         this.getTotal();
        })
      }
    });
  }

  onQueryParamsChange(params: NzTableQueryParams): void {
    const { pageSize, pageIndex, sort } = params;
    const currentSort = sort.find((item) => item.value !== null);
    const sortField = (currentSort && currentSort.key) || null;
    const sortOrder = (currentSort && currentSort.value) || null;
    this.pageSize = pageSize;
    console.log("page size: " + this.pageSize);
    this.getAllCandy(sortField, sortOrder, pageIndex);
  }


}