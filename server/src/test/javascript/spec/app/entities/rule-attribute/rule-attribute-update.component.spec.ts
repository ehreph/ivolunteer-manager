import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { RuleAttributeUpdateComponent } from 'app/entities/rule-attribute/rule-attribute-update.component';
import { RuleAttributeService } from 'app/entities/rule-attribute/rule-attribute.service';
import { RuleAttribute } from 'app/shared/model/rule-attribute.model';

describe('Component Tests', () => {
  describe('RuleAttribute Management Update Component', () => {
    let comp: RuleAttributeUpdateComponent;
    let fixture: ComponentFixture<RuleAttributeUpdateComponent>;
    let service: RuleAttributeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [RuleAttributeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(RuleAttributeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RuleAttributeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RuleAttributeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RuleAttribute(123);
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
        const entity = new RuleAttribute();
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
