import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBadge } from 'app/shared/model/badge.model';
import { BadgeService } from '../../../shared/services/badge.service';

@Component({
  templateUrl: './badge-delete-dialog.component.html',
})
export class BadgeDeleteDialogComponent {
  badge?: IBadge;

  constructor(protected badgeService: BadgeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.badgeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('badgeListModification');
      this.activeModal.close();
    });
  }
}
