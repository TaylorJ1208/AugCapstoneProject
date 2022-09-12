import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCatalogComponent } from './update-catalog.component';

describe('UpdateCatalogComponent', () => {
  let component: UpdateCatalogComponent;
  let fixture: ComponentFixture<UpdateCatalogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCatalogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateCatalogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
