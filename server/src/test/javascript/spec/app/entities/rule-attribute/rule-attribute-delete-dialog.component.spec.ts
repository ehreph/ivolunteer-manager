import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { RuleAttributeDeleteDialogComponent } from 'app/entities/rule-attribute/rule-attribute-delete-dialog.component';
import { RuleAttributeService } from 'app/entities/rule-attribute/rule-attribute.service';

describe('Component Tests', () => {
  describe('RuleAttribute Management Delete Component', () => {
    let comp: RuleAttributeDeleteDialogComponent;
    let fixture: ComponentFixture<RuleAttributeDeleteDialogComponent>;
    let service: RuleAttributeService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [RuleAttributeDeleteDialogComponent],
      })
        .overrideTemplate(RuleAttributeDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RuleAttributeDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RuleAttributeService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
