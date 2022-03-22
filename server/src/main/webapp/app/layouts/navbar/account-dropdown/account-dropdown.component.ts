import { Component, Input, OnInit } from '@angular/core';
import { LoginService } from 'app/core/login/login.service';
import { AccountService } from 'app/core/auth/account.service';
import { LoginModalService } from 'app/core/login/login-modal.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-account-dropdown',
  templateUrl: './account-dropdown.component.html',
  styleUrls: ['./account-dropdown.component.scss'],
})
export class AccountDropdownComponent implements OnInit {
  @Input() collapseNavbar: () => void;

  constructor(
    private loginService: LoginService,
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private profileService: ProfileService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['overview']);
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }

  login(): void {
    this.loginModalService.open();
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }
}
