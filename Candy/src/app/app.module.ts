import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { CandyComponent } from './pages/candy/candy.component';
import { AddCandyComponent } from './pages/add-candy/add-candy.component';
import { UploadComponentComponent } from './componenets/upload-component/upload-component.component';
import { SearchAreaComponent } from './componenets/search-area/search-area.component';
import { SupplierComponent } from './componenets/supplier/supplier.component';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzGridModule } from 'ng-zorro-antd/grid';


import { NzCascaderModule } from 'ng-zorro-antd/cascader';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzUploadModule } from 'ng-zorro-antd/upload';
import { NzSpaceModule } from 'ng-zorro-antd/space';
import { HttpClientModule } from '@angular/common/http';


registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    SearchAreaComponent,
    SupplierComponent,
    CandyComponent,
    AddCandyComponent,
    UploadComponentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    IconsProviderModule,
    NzLayoutModule,
    NzMenuModule,
    NzInputModule,
    NzButtonModule,
    NzCascaderModule,
    NzTableModule,
    NzSpaceModule,
    NzUploadModule,
    NzSpinModule,
    NzGridModule,
    HttpClientModule,
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US }],
  bootstrap: [AppComponent]
})
export class AppModule { }
