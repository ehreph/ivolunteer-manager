import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { UserCompetenceUpdateComponent } from 'app/entities/user-competence/user-competence-update.component';
import { UserCompetenceService } from 'app/entities/user-competence/user-competence.service';
import { UserCompetence } from 'app/shared/model/user-competence.model';

describe('Component Tests', () => {
  describe('UserCompetence Management Update Component', () => {
    let comp: UserCompetenceUpdateComponent;
    let fixture: ComponentFixture<UserCompetenceUpdateComponent>;
    let service: UserCompetenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [UserCompetenceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserCompetenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserCompetenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserCompetenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserCompetence(123);
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
        const entity = new UserCompetence();
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
