import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { UserBadgeUpdateComponent } from 'app/entities/user-badge/user-badge-update.component';
import { UserBadgeService } from 'app/entities/user-badge/user-badge.service';
import { UserBadge } from 'app/shared/model/user-badge.model';

describe('Component Tests', () => {
  describe('UserBadge Management Update Component', () => {
    let comp: UserBadgeUpdateComponent;
    let fixture: ComponentFixture<UserBadgeUpdateComponent>;
    let service: UserBadgeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [UserBadgeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserBadgeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserBadgeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserBadgeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserBadge(123);
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
        const entity = new UserBadge();
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
