import { Component, Input, OnInit } from '@angular/core';
import { ChartDataSets, ChartOptions, ChartType } from 'chart.js';
import { Label } from 'ng2-charts';
import * as pluginDataLabels from 'chartjs-plugin-datalabels';

@Component({
  selector: 'jhi-basic-bar-chart',
  templateUrl: './basic-bar-chart.component.html',
  styleUrls: ['./basic-bar-chart.component.scss'],
})
export class BasicBarChartComponent implements OnInit {
  @Input()
  timeUnit: 'month' | 'day';
  // override options in parent component
  //     @Input()
  public barChartOptions: ChartOptions;

  //initial labels
  @Input() public barChartLabels: Label[] = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
  public barChartType: ChartType = 'line';
  public barChartLegend = true;
  public barChartPlugins = [pluginDataLabels];

  // initial data
  @Input() public barChartData: ChartDataSets[] = [{ data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' }];

  constructor() {}

  ngOnInit(): void {
    console.debug('time unit: ' + this.timeUnit);

    this.barChartOptions = {
      responsive: true,
      // We use these empty structures as placeholders for dynamic theming.
      scales: {
        xAxes: [
          {
            type: 'time',
            time: {
              unit: this.timeUnit,
            },
          },
        ],
        yAxes: [
          {
            ticks: {
              beginAtZero: true,
              min: 0,
            },
          },
        ],
      },
      plugins: {
        datalabels: {
          anchor: 'start',
          align: 'center',
        },
      },
    };
  }

  // events
  public chartClicked({ event, active }: { event: MouseEvent; active: {}[] }): void {
    console.log(event, active);
  }

  public chartHovered({ event, active }: { event: MouseEvent; active: {}[] }): void {
    console.log(event, active);
  }
}
