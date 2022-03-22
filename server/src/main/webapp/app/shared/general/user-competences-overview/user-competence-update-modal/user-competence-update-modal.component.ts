import { Component, OnInit } from '@angular/core';
import { IUserCompetence, UserCompetence } from '../../../../shared/model/user-competence.model';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { ICompetence } from '../../../../shared/model/competence.model';
import { CompetenceService } from '@services/competence.service';
import { UserCompetenceService } from '@services/user-competence.service';

@Component({
  selector: 'jhi-user-competence-update-modal',
  templateUrl: './user-competence-update-modal.component.html',
  styleUrls: ['./user-competence-update-modal.component.scss'],
})
export class UserCompetenceUpdateModalComponent implements OnInit {
  isSaving = false;
  userCompetence: IUserCompetence;

  competences: ICompetence[] = [];
  userId: any;

  editForm = this.fb.group({
    id: [],
    userId: [],
    userLevel: [0, Validators.required],
    compId: [null, Validators.required],
  });

  constructor(
    protected userCompetenceService: UserCompetenceService,
    protected competenceService: CompetenceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    protected activeModal: NgbActiveModal
  ) {}

  ngOnInit(): void {
    if (this.userCompetence) {
      this.editForm.get('compId').disable();
      this.competenceService.find(this.userCompetence.compId).subscribe(res => {
        if (res) {
          this.competences.push(res.body);
        }
      });
      this.updateForm(this.userCompetence);
    } else {
      this.competenceService
        .findNewCompetencesForUser(this.userId)
        .subscribe((res: HttpResponse<ICompetence[]>) => (this.competences = res.body || []));
      this.editForm.patchValue({
        userId: this.userId,
      });
    }
  }

  updateForm(userCompetence: IUserCompetence): void {
    this.editForm.patchValue({
      id: userCompetence.id,
      userId: userCompetence.userId,
      userLevel: userCompetence.userLevel,
      compId: userCompetence.compId,
    });
  }

  save(): void {
    this.isSaving = true;
    const userCompetence = this.createFromForm();
    console.debug(userCompetence);
    if (userCompetence.id !== undefined && userCompetence.id !== null) {
      this.subscribeToSaveResponse(this.userCompetenceService.update(userCompetence));
    } else {
      this.subscribeToSaveResponse(this.userCompetenceService.create(userCompetence));
    }
  }

  private createFromForm(): IUserCompetence {
    return {
      ...new UserCompetence(),
      id: this.editForm.get(['id'])!.value,
      userId: this.userId,
      userLevel: this.editForm.get(['userLevel'])!.value,
      compId: this.editForm.get(['compId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserCompetence>>): void {
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

  trackById(index: number, item: ICompetence): any {
    return item.id;
  }

  cancel(): void {
    this.activeModal.dismiss();
  }

  close(): void {
    this.activeModal.close(true);
  }
}
