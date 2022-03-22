import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRuleAttribute } from 'app/shared/model/rule-attribute.model';
import { RuleAttributeService } from '../../../shared/services/rule-attribute.service';

@Component({
  templateUrl: './rule-attribute-delete-dialog.component.html',
})
export class RuleAttributeDeleteDialogComponent {
  ruleAttribute?: IRuleAttribute;

  constructor(
    protected ruleAttributeService: RuleAttributeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.ruleAttributeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('ruleAttributeListModification');
      this.activeModal.close();
    });
  }
}
