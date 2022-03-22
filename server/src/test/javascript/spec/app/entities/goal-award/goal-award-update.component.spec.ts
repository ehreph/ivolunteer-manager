import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { GoalAwardUpdateComponent } from 'app/entities/goal-award/goal-award-update.component';
import { GoalAwardService } from 'app/entities/goal-award/goal-award.service';
import { GoalAward } from 'app/shared/model/goal-award.model';

describe('Component Tests', () => {
  describe('GoalAward Management Update Component', () => {
    let comp: GoalAwardUpdateComponent;
    let fixture: ComponentFixture<GoalAwardUpdateComponent>;
    let service: GoalAwardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [GoalAwardUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GoalAwardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoalAwardUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoalAwardService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoalAward(123);
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
        const entity = new GoalAward();
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
