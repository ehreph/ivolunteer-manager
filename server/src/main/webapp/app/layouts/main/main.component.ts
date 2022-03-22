import { Component, OnInit, RendererFactory2, Renderer2 } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRouteSnapshot, NavigationEnd, NavigationError } from '@angular/router';
import { TranslateService, LangChangeEvent } from '@ngx-translate/core';

import { AccountService } from 'app/core/auth/account.service';
import { SideBarItemConfig } from 'app/shared/admin/side-bar-item/side-bar-item.model';

@Component({
  selector: 'jhi-main',
  styleUrls: ['./main.component.scss'],
  templateUrl: './main.component.html',
})
export class MainComponent implements OnInit {
  private renderer: Renderer2;

  _opened = false;

  readonly items: SideBarItemConfig[] = [
    //add here new routing navigations
    new SideBarItemConfig('/admin/institution', 'building', 'global.menu.entities.institution'),
    new SideBarItemConfig('/admin/user-management', 'user', 'global.menu.admin.userManagement'),
    new SideBarItemConfig('/admin/goal', 'bullseye', 'global.menu.entities.goal'),
    new SideBarItemConfig('/admin/activity', 'running', 'global.menu.entities.activity'),
    new SideBarItemConfig('/admin/competence', 'book', 'global.menu.entities.competence'),
    new SideBarItemConfig('/admin/badge', 'award', 'global.menu.entities.badge'),
  ];

  constructor(
    private accountService: AccountService,
    private titleService: Title,
    private router: Router,
    private translateService: TranslateService,
    rootRenderer: RendererFactory2
  ) {
    this.renderer = rootRenderer.createRenderer(document.querySelector('html'), null);
  }

  _closeSidebar(): void {
    this._opened = false;
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();

    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.updateTitle();
      }
      if (event instanceof NavigationError && event.error.status === 404) {
        this.router.navigate(['/404']);
      }
    });

    this.translateService.onLangChange.subscribe((langChangeEvent: LangChangeEvent) => {
      this.updateTitle();

      this.renderer.setAttribute(document.querySelector('html'), 'lang', langChangeEvent.lang);
    });
  }

  private getPageTitle(routeSnapshot: ActivatedRouteSnapshot): string {
    let title: string = routeSnapshot.data && routeSnapshot.data['pageTitle'] ? routeSnapshot.data['pageTitle'] : '';
    if (routeSnapshot.firstChild) {
      title = this.getPageTitle(routeSnapshot.firstChild) || title;
    }
    return title;
  }

  private updateTitle(): void {
    let pageTitle = this.getPageTitle(this.router.routerState.snapshot.root);
    if (!pageTitle) {
      pageTitle = 'global.title';
    }
    this.translateService.get(pageTitle).subscribe(title => this.titleService.setTitle(title));
  }
}
