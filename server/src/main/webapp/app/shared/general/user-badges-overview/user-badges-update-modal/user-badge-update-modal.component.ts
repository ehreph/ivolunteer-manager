import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { BadgeService } from '@services/badge.service';
import { UserBadgeService } from '@services/user-badge.service';
import { IBadge } from '@model/badge.model';
import { IUserBadge, UserBadge } from '@model/user-badge.model';

@Component({
  selector: 'jhi-user-badge-update-modal',
  templateUrl: './user-badge-update-modal.component.html',
  styleUrls: ['./user-badge-update-modal.component.scss'],
})
export class UserBadgeUpdateModalComponent implements OnInit {
  isSaving = false;
  userBadge: IUserBadge;

  badges: IBadge[] = [];
  userId: any;

  editForm = this.fb.group({
    id: [],
    userId: [],
    badgeId: [null, Validators.required],
  });

  constructor(
    protected userBadgeService: UserBadgeService,
    protected badgeService: BadgeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.userBadge) {
      this.editForm.get('badgeId').disable();
      this.badgeService.find(this.userBadge.badgeId).subscribe(res => {
        if (res) {
          this.badges.push(res.body);
        }
      });
      this.updateForm(this.userBadge);
    } else {
      this.badgeService.findNewBadgesForUser(this.userId).subscribe((res: HttpResponse<IBadge[]>) => (this.badges = res.body || []));

      this.editForm.patchValue({
        userId: this.userId,
      });
    }
  }

  updateForm(iUserBadge: IUserBadge): void {
    this.editForm.patchValue({
      id: iUserBadge.id,
      userId: iUserBadge.userId,
      badgeId: iUserBadge.badgeId,
    });
  }

  save(): void {
    this.isSaving = true;
    const iUserBadge = this.createFromForm();
    if (iUserBadge.id !== undefined && iUserBadge.id !== null) {
      this.subscribeToSaveResponse(this.userBadgeService.update(iUserBadge));
    } else {
      this.subscribeToSaveResponse(this.userBadgeService.create(iUserBadge));
    }
  }

  private createFromForm(): IUserBadge {
    return {
      ...new UserBadge(),
      id: this.editForm.get(['id'])!.value,
      userId: this.userId,
      badgeId: this.editForm.get(['badgeId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserBadge>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.close();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBadge): any {
    return item.id;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  close(): void {
    this.activeModal.close(true);
  }
}
