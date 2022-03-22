import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IInstitution, Institution } from 'app/shared/model/institution.model';
import { InstitutionService } from '../../../shared/services/institution.service';

@Component({
  selector: 'jhi-institution-update',
  templateUrl: './institution-update.component.html',
})
export class InstitutionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
  });

  constructor(protected institutionService: InstitutionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ institution }) => {
      this.updateForm(institution);
    });
  }

  updateForm(institution: IInstitution): void {
    this.editForm.patchValue({
      id: institution.id,
      name: institution.name,
      description: institution.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const institution = this.createFromForm();
    if (institution.id !== undefined) {
      this.subscribeToSaveResponse(this.institutionService.update(institution));
    } else {
      this.subscribeToSaveResponse(this.institutionService.create(institution));
    }
  }

  private createFromForm(): IInstitution {
    return {
      ...new Institution(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstitution>>): void {
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
