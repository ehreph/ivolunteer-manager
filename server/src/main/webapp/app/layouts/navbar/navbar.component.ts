import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/core/login/login.service';
import { IconProp } from '@fortawesome/fontawesome-svg-core';

@Component({
  selector: 'jhi-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['navbar.scss'],
})
export class NavbarComponent implements OnInit {
  isNavbarCollapsed = true;
  version: string;
  @Output() openedChange = new EventEmitter<boolean>();
  @Input() opened = false;

  constructor(
    private loginService: LoginService,
    private languageService: JhiLanguageService,
    private sessionStorage: SessionStorageService,
    private accountService: AccountService
  ) {
    this.version = VERSION ? (VERSION.toLowerCase().startsWith('v') ? VERSION : 'v' + VERSION) : '';
  }

  ngOnInit(): void {}

  toggleSidebar(): void {
    this.openedChange.emit(!this.opened);
    this.isNavbarCollapsed = true;
  }

  getSideBarIcon(): IconProp {
    return this.opened ? 'times' : 'bars';
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }
}
