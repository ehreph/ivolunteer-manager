import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserGoals } from 'app/shared/model/user-goals.model';
import { UserGoalsService } from '../../../services/user-goals.service';

@Component({
  templateUrl: './user-goals-delete-dialog.component.html',
})
export class UserGoalsDeleteDialogComponent {
  userGoals?: IUserGoals;

  constructor(protected userGoalsService: UserGoalsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userGoalsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userGoalsListModification');
      this.activeModal.close();
    });
  }
}
