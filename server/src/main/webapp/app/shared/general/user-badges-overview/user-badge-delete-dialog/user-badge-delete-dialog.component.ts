import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserBadge } from 'app/shared/model/user-badge.model';
import { UserBadgeService } from '../../../services/user-badge.service';

@Component({
  templateUrl: './user-badge-delete-dialog.component.html',
})
export class UserBadgeDeleteDialogComponent {
  userBadge?: IUserBadge;

  constructor(protected userBadgeService: UserBadgeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userBadgeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userBadgeListModification');
      this.activeModal.close();
    });
  }
}
