import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompetence } from 'app/shared/model/competence.model';

@Component({
  selector: 'jhi-competence-detail',
  templateUrl: './competence-detail.component.html',
})
export class CompetenceDetailComponent implements OnInit {
  competence: ICompetence | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ competence }) => (this.competence = competence));
  }

  previousState(): void {
    window.history.back();
  }
}
