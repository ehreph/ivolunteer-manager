import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserCompetence } from 'app/shared/model/user-competence.model';
import { UserCompetenceService } from '../../../services/user-competence.service';

@Component({
  templateUrl: './user-competence-delete-dialog.component.html',
})
export class UserCompetenceDeleteDialogComponent {
  userCompetence?: IUserCompetence;

  constructor(
    protected userCompetenceService: UserCompetenceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userCompetenceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userCompetenceListModification');
      this.activeModal.close();
    });
  }
}
