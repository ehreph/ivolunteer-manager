import { Component, Input, OnInit } from '@angular/core';

import { ChartDataSets, ChartType, RadialChartOptions } from 'chart.js';
import { Label } from 'ng2-charts';

@Component({
  selector: 'jhi-basic-radar-chart',
  templateUrl: './basic-radar-chart.component.html',
  styleUrls: ['./basic-radar-chart.component.scss'],
})
export class BasicRadarChartComponent implements OnInit {
  // Radar
  public radarChartOptions: RadialChartOptions = {
    responsive: true,
    scale: {
      ticks: {
        beginAtZero: true,
        min: 0,
        stepSize: 1,
      },
    },
  };
  @Input()
  public radarChartLabels: Label[] = ['Eating', 'Drinking', 'Sleeping', 'Designing', 'Coding', 'Cycling', 'Running'];

  @Input()
  public radarChartData: ChartDataSets[] = [
    { data: [65, 59, 90, 81, 56, 55, 40], label: 'Series A' },
    { data: [28, 48, 40, 19, 96, 27, 100], label: 'Series B' },
  ];
  public radarChartType: ChartType = 'radar';

  constructor() {}

  ngOnInit(): void {}

  // events
  public chartClicked({ event, active }: { event: MouseEvent; active: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent; active: {}[] }): void {
    console.log(event, active);
  }
}
