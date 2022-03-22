import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { UserGoalsUpdateComponent } from 'app/entities/user-goals/user-goals-update.component';
import { UserGoalsService } from 'app/entities/user-goals/user-goals.service';
import { UserGoals } from 'app/shared/model/user-goals.model';

describe('Component Tests', () => {
  describe('UserGoals Management Update Component', () => {
    let comp: UserGoalsUpdateComponent;
    let fixture: ComponentFixture<UserGoalsUpdateComponent>;
    let service: UserGoalsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [UserGoalsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserGoalsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserGoalsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserGoalsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserGoals(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserGoals();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
