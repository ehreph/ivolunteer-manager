import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBadge, Badge } from 'app/shared/model/badge.model';
import { BadgeService } from '../../../shared/services/badge.service';

@Component({
  selector: 'jhi-badge-update',
  templateUrl: './badge-update.component.html',
})
export class BadgeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected badgeService: BadgeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ badge }) => {
      this.updateForm(badge);
    });
  }

  updateForm(badge: IBadge): void {
    this.editForm.patchValue({
      id: badge.id,
      name: badge.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const badge = this.createFromForm();
    if (badge.id !== undefined) {
      this.subscribeToSaveResponse(this.badgeService.update(badge));
    } else {
      this.subscribeToSaveResponse(this.badgeService.create(badge));
    }
  }

  private createFromForm(): IBadge {
    return {
      ...new Badge(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBadge>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
