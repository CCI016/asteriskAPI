import { Component, OnInit } from '@angular/core';
import { WebRequestService } from 'src/app/web-request.service';
import { Candy } from '../models/candy';
import { Prompt } from '../models/prompt';
import { Provider } from '../models/provider';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-candy',
  templateUrl: './add-candy.component.html',
  styleUrls: ['./add-candy.component.css']
})
export class AddCandyComponent implements OnInit {
  candyName : string;
  providerName : string;
  providers : Provider[];
  propmpts : Prompt[];
  request : string;
  selectedPrompt : number;
  selectedProvider : number;

  constructor(
    private webService : WebRequestService,
    private router: Router
  ) {
    this.propmpts = [];
    this. providers = [];
    this.candyName = "";
    this.providerName ="";
    this.selectedPrompt = 0;
    this.selectedProvider = 0;
  }

  ngOnInit(): void {
    this.getAllPrompts();
    this.getAllProviders();
  }

  getAllProviders() {
    // this.providers = [];
    this.request = "getCandy/providers";
    this.webService.getData(this.request).subscribe(data => this.providers = <Provider[]> data);
    console.log(this.request);
  }
  
  getAllPrompts() {
    // this.propmpts = [];
    this.request = "getCandy/prompts";
    this.webService.getData(this.request).subscribe(data => this.propmpts = <Prompt[]> data);
    console.log(this.request);
  }

  addCandy() {

    var candy : Candy;

    this.request = "getCandy/addCandy";

    if (this.candyName != "" && (this.providerName != "" || this.selectedProvider != 0) && this.selectedPrompt != 0 && this.selectedPrompt != null) {
      if (this.selectedProvider != 0 && this.selectedProvider != null) {
        console.log(this.selectedProvider);
        this.webService.postData(this.request, {name : this.candyName, providerID : this.selectedProvider, promptID : this.selectedPrompt}).subscribe((data) => {
          this.router.navigate(['']);
      })
      } else {

        this.webService.postData(this.request, {name : this.candyName, providerName : this.providerName, promptID : this.selectedPrompt}).subscribe((data) => {
          this.router.navigate(['']);
        })
      }
    } 
  }
}
