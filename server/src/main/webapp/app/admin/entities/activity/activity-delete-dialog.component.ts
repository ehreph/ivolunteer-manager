import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { ActivityService } from '../../../shared/services/activity.service';
import { IActivity } from '../../../shared/model/activity.model';

@Component({
  templateUrl: './activity-delete-dialog.component.html',
})
export class ActivityDeleteDialogComponent {
  activity?: IActivity;

  constructor(protected activityService: ActivityService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('activityListModification');
      this.activeModal.close();
    });
  }
}
