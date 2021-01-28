import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCandyComponent } from './add-candy.component';

describe('AddCandyComponent', () => {
  let component: AddCandyComponent;
  let fixture: ComponentFixture<AddCandyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddCandyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCandyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
