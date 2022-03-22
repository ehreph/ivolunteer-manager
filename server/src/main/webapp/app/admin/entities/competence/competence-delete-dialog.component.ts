import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompetence } from 'app/shared/model/competence.model';
import { CompetenceService } from '../../../shared/services/competence.service';

@Component({
  templateUrl: './competence-delete-dialog.component.html',
})
export class CompetenceDeleteDialogComponent {
  competence?: ICompetence;

  constructor(
    protected competenceService: CompetenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.competenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('competenceListModification');
      this.activeModal.close();
    });
  }
}
