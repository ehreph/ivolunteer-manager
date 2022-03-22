import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstitution } from 'app/shared/model/institution.model';

@Component({
  selector: 'jhi-institution-detail',
  templateUrl: './institution-detail.component.html',
})
export class InstitutionDetailComponent implements OnInit {
  institution: IInstitution | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ institution }) => (this.institution = institution));
  }

  previousState(): void {
    window.history.back();
  }
}
