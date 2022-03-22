import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { CompetenceUpdateComponent } from 'app/entities/competence/competence-update.component';
import { CompetenceService } from 'app/entities/competence/competence.service';
import { Competence } from 'app/shared/model/competence.model';

describe('Component Tests', () => {
  describe('Competence Management Update Component', () => {
    let comp: CompetenceUpdateComponent;
    let fixture: ComponentFixture<CompetenceUpdateComponent>;
    let service: CompetenceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [CompetenceUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CompetenceUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompetenceUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompetenceService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Competence(123);
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
        const entity = new Competence();
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
