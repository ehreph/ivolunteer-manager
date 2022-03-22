import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { UserStatisticsService } from '../../../shared/services/user-statistics.service';
import { IUserStatistics } from '../../../shared/model/statistics.model';
import { IUserMin  } from '../../../core/user/user.model';
import { UserService } from '../../../core/user/user.service';

@Component({
  selector: 'jhi-user-statistic-charts',
  templateUrl: './user-statistic-charts.component.html',
  styleUrls: ['./user-statistic-charts.component.scss'],
})
export class UserStatisticChartsComponent implements OnInit, OnChanges {
  active = 1;

  @Input() userId: any;
  @Input()
  userStats: IUserStatistics;

  users: IUserMin[] = [];
  compareStats: IUserStatistics;
  compareUserId: number = null;

  finishedGoalsPerMonthLabels: Label[];

  finishedGoalsPerMonthValues: ChartDataSets[];

  finishedGoalsPerDayLabels: Label[];

  finishedGoalsPerDayValues: ChartDataSets[];

  levelPerCompetenceLabels: Label[];

  levelPerCompetenceValues: ChartDataSets[];

  constructor(private userStatisticsService: UserStatisticsService,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.queryMin().subscribe(res => {
      this.users = res.body.filter(user => user.id !== this.userId);
    })
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.updateChartData();
  }

  updateChartData(): void {
    this.finishedGoalsPerDayValues = [];
    this.finishedGoalsPerMonthValues = [];
    this.levelPerCompetenceValues = [];

    let userMonthData;
    let userDayData;
    let userCompData;

    let userCompareMonthData;
    let userCompareDayData;
    let userCompareCompData;

    let username;
    let compareUsername;
    if (this.userStats != null) {
      username = this.getUsername(this.userStats);

      userMonthData = this.mapStatisticDataToChartData(this.userStats.finishedGoalsPerMonth);
      userDayData = this.mapStatisticDataToChartData(this.userStats.finishedGoalsPerDate);
      userCompData = this.mapCompetenceDataToChartData(this.userStats.levelPerCompetence);

      this.finishedGoalsPerDayValues.push({
        data: userMonthData[1],
        label: username,
      });
      this.finishedGoalsPerMonthValues.push({
        data: userDayData[1],
        label: username,
      });
      this.levelPerCompetenceValues.push({
        data: userCompData[1],
        label: username,
      });

      this.finishedGoalsPerMonthLabels = userMonthData[0];
      this.finishedGoalsPerDayLabels = userDayData[0];
      this.levelPerCompetenceLabels = userCompData[0];
    }

    if (this.compareStats != null) {
      compareUsername = this.getUsername(this.compareStats);

      userCompareMonthData = this.mapStatisticDataToChartData(this.compareStats.finishedGoalsPerMonth);
      userCompareDayData = this.mapStatisticDataToChartData(this.compareStats.finishedGoalsPerDate);
      userCompareCompData = this.mapCompetenceDataToChartData(this.compareStats.levelPerCompetence);

      this.finishedGoalsPerDayValues.push({
        data: userCompareMonthData[1],
        label: compareUsername,
      });
      this.finishedGoalsPerMonthValues.push({
        data: userCompareDayData[1],
        label: compareUsername,
      });
      this.levelPerCompetenceValues.push({
        data: userCompareCompData[1],
        label: compareUsername,
      });

      this.finishedGoalsPerMonthLabels = userMonthData[0].concat(userCompareMonthData[0]).unique();
      this.finishedGoalsPerDayLabels = userDayData[0].concat(userCompareDayData[0]).unique();
      this.levelPerCompetenceLabels = userCompData[0].concat(userCompareCompData[0]).unique();
    }
  }

  private getUsername(statistics: IUserStatistics): string {
    return statistics.firstName + ' ' + statistics.lastName;
  }

  private mapStatisticDataToChartData(dataMap: Map<Date, number>): any[] {
    const labels: string[] = Object.keys(dataMap).map(value => new Date(value).toDateString());
    const values: number[] = Object.values(dataMap);

    const arr = [];
    arr.push(labels);
    arr.push(values);
    return arr;
  }

  private mapCompetenceDataToChartData(dataMap: Map<String, number>): any[] {
    const labels: string[] = Object.keys(dataMap);
    const values: number[] = Object.values(dataMap);
    const arr = [];
    arr.push(labels);
    arr.push(values);
    return arr;
  }

  loadCompareUserStats(): void {
    if (this.compareUserId) {
      this.userStatisticsService.getOtherUserStatistics(this.compareUserId).subscribe(res => {
        console.debug(res.body);
        this.compareStats = res.body;
        this.updateChartData();
      });
    }
  }
}
