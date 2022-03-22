import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { InstitutionUpdateComponent } from 'app/entities/institution/institution-update.component';
import { InstitutionService } from 'app/entities/institution/institution.service';
import { Institution } from 'app/shared/model/institution.model';

describe('Component Tests', () => {
  describe('Institution Management Update Component', () => {
    let comp: InstitutionUpdateComponent;
    let fixture: ComponentFixture<InstitutionUpdateComponent>;
    let service: InstitutionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [InstitutionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(InstitutionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(InstitutionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InstitutionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Institution(123);
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
        const entity = new Institution();
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
