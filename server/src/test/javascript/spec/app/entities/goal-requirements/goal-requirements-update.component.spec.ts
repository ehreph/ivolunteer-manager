import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { GoalRequirementsUpdateComponent } from 'app/entities/goal-requirements/goal-requirements-update.component';
import { GoalRequirementsService } from 'app/entities/goal-requirements/goal-requirements.service';
import { GoalRequirements } from 'app/shared/model/goal-requirements.model';

describe('Component Tests', () => {
  describe('GoalRequirements Management Update Component', () => {
    let comp: GoalRequirementsUpdateComponent;
    let fixture: ComponentFixture<GoalRequirementsUpdateComponent>;
    let service: GoalRequirementsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [GoalRequirementsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GoalRequirementsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoalRequirementsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoalRequirementsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoalRequirements(123);
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
        const entity = new GoalRequirements();
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
