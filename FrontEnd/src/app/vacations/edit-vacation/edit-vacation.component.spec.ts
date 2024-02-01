import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditVacationComponent } from './edit-vacation.component';

describe('EditVacationComponent', () => {
  let component: EditVacationComponent;
  let fixture: ComponentFixture<EditVacationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditVacationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditVacationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
