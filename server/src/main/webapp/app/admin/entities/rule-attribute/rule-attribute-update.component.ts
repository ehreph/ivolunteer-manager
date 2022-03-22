import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRuleAttribute, RuleAttribute } from 'app/shared/model/rule-attribute.model';
import { RuleAttributeService } from '../../../shared/services/rule-attribute.service';
import { GlobalType } from '../../../shared/model/enumerations/global-type.constants';

@Component({
  selector: 'jhi-rule-attribute-update',
  templateUrl: './rule-attribute-update.component.html',
})
export class RuleAttributeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ruleType: [],
    name: [null, Validators.required],
    unitName: [null, Validators.required],
  });

  constructor(protected ruleAttributeService: RuleAttributeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ ruleAttribute }) => {
      console.log(ruleAttribute);
      if (ruleAttribute?.id) {
        this.updateForm(ruleAttribute);
      }
      // initial patch to set activity
      this.editForm.patchValue({
        ruleType: GlobalType.ACTIVITY.toString(),
      });
    });
  }

  updateForm(ruleAttribute: IRuleAttribute): void {
    this.editForm.patchValue({
      id: ruleAttribute.id,
      ruleType: ruleAttribute.ruleType,
      name: ruleAttribute.name,
      unitName: ruleAttribute.unitName,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const ruleAttribute = this.createFromForm();
    if (ruleAttribute?.id) {
      this.subscribeToSaveResponse(this.ruleAttributeService.update(ruleAttribute));
    } else {
      this.subscribeToSaveResponse(this.ruleAttributeService.create(ruleAttribute));
    }
  }

  private createFromForm(): IRuleAttribute {
    const r = this.editForm.value as RuleAttribute;
    //only ruleattributes for activities can be created
    r.ruleType = GlobalType.ACTIVITY;
    return r;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRuleAttribute>>): void {
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
