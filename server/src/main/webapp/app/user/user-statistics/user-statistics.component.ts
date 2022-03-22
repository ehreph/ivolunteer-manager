import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { IUserStatistics } from '../../shared/model/statistics.model';
import { UserStatisticsService } from '../../shared/services/user-statistics.service';

@Component({
  selector: 'jhi-user-statistics',
  templateUrl: './user-statistics.component.html',
  styleUrls: ['./user-statistics.component.scss'],
})
export class UserStatisticsComponent implements OnInit {

  userAccount: Account | null = null;
  isAdminPage: boolean = false;

  userStats: IUserStatistics;

  translationKey: string = 'label';

  constructor(private userStatisticsService: UserStatisticsService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => (this.userAccount = account));
    this.userStatisticsService.getUserStatistics().subscribe(res => {
      console.debug(res.body);
      this.userStats = res.body;
    });
  }
}
