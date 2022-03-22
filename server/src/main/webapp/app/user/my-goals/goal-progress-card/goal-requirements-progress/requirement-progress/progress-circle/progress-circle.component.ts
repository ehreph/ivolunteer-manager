import { Component, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

export interface Progress {
  subTitle: string;
  title: string;
  unit: string;
  percentage: number;
  amount: number;
}

@Component({
  selector: 'jhi-progress-circle',
  templateUrl: './progress-circle.component.html',
  styleUrls: ['./progress-circle.component.scss'],
})
export class ProgressCircleComponent implements OnInit {
  @Input()
  showRelative: Observable<boolean>;
  @Input()
  data: Progress = {
    unit: '%',
    title: 'title',
    subTitle: 'progress',
    percentage: 0,
    amount: 0,
  };

  constructor() {}

  progressBar = document.querySelector('.progress-bar');
  intervalId;

  ngOnInit(): void {}
}
