import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { GoalUpdateComponent } from 'app/entities/goal/goal-update.component';
import { GoalService } from 'app/entities/goal/goal.service';
import { Goal } from 'app/shared/model/goal.model';

describe('Component Tests', () => {
  describe('Goal Management Update Component', () => {
    let comp: GoalUpdateComponent;
    let fixture: ComponentFixture<GoalUpdateComponent>;
    let service: GoalService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [GoalUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GoalUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoalUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoalService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Goal(123);
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
        const entity = new Goal();
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
