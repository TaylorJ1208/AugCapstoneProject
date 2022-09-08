import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StorePurchasesComponent } from './store-purchases.component';

describe('StorePurchasesComponent', () => {
  let component: StorePurchasesComponent;
  let fixture: ComponentFixture<StorePurchasesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StorePurchasesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StorePurchasesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
