import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IInstitution } from 'app/shared/model/institution.model';
import { InstitutionService } from '../../../shared/services/institution.service';

@Component({
  templateUrl: './institution-delete-dialog.component.html',
})
export class InstitutionDeleteDialogComponent {
  institution?: IInstitution;

  constructor(
    protected institutionService: InstitutionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.institutionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('institutionListModification');
      this.activeModal.close();
    });
  }
}
