import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoal } from 'app/shared/model/goal.model';
import { GoalService } from '../../../shared/services/goal.service';

@Component({
  templateUrl: './goal-delete-dialog.component.html',
})
export class GoalDeleteDialogComponent {
  goal?: IGoal;

  constructor(protected goalService: GoalService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goalService.delete(id).subscribe(() => {
      this.eventManager.broadcast('goalListModification');
      this.activeModal.close();
    });
  }
}
