import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CandyComponent } from './pages/candy/candy.component';
import { AddCandyComponent } from './pages/add-candy/add-candy.component';

const routes: Routes = [
  { path: '', redirectTo: 'candy', pathMatch: 'full' },
  {
    path: 'candy',
    children: [
      {
        path: '',
        component: CandyComponent
      },
      {
        path: 'add',
        component: AddCandyComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
