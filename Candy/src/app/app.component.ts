import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { WebSocketService } from './web-socket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent {
  title = 'Candy';
  isCollapsed = false;
  time : string;
  protected webSocketSubscription: Subscription;
  constructor(
    protected webSocket : WebSocketService
  ) {}

  ngOnInit(): void {
    this.webSocketConnection();
  }


  webSocketConnection() {
    this.webSocketSubscription = this.webSocket.initSocket('ws://' + 'localhost:8080' + '/websocket').subscribe(
      message => {
        const parsedMessage = JSON.parse(message.data);
        if (parsedMessage.time) {
          this.time = parsedMessage.time;
          console.log(this.time);
        }
      });
  }
}
