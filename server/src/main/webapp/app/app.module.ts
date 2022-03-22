import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { IvolunteerManagerSharedModule } from 'app/shared/shared.module';
import { IvolunteerManagerCoreModule } from 'app/core/core.module';
import { IvolunteerManagerAppRoutingModule } from './app-routing.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { SidebarModule } from 'ng-sidebar';
import { AccountDropdownComponent } from './layouts/navbar/account-dropdown/account-dropdown.component';
import { SwitchLanguageComponent } from './layouts/navbar/switch-language/switch-language.component';
import { AdminDropdownComponent } from './layouts/navbar/admin-dropdown/admin-dropdown.component';
import { UserNavigationComponent } from './layouts/navbar/user-navigation/user-navigation.component';
import { IvolunteerManagerHomeModule } from 'app/home/home.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormlyModule } from '@ngx-formly/core';
import { FormlyBootstrapModule } from '@ngx-formly/bootstrap';

@NgModule({
  imports: [
    BrowserModule,
    IvolunteerManagerSharedModule,
    IvolunteerManagerCoreModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    IvolunteerManagerHomeModule,
    IvolunteerManagerAppRoutingModule,
    SidebarModule,
    ReactiveFormsModule,
    FormlyModule.forRoot({ extras: { lazyRender: true } }),
    FormlyBootstrapModule,
  ],
  declarations: [
    MainComponent,
    NavbarComponent,
    ErrorComponent,
    PageRibbonComponent,
    ActiveMenuDirective,
    FooterComponent,
    AccountDropdownComponent,
    SwitchLanguageComponent,
    AdminDropdownComponent,
    UserNavigationComponent,
  ],
  bootstrap: [MainComponent],
})
export class IvolunteerManagerAppModule {}

declare global {
  // this is important to access it as global type String

  // @ts-ignore
  interface Array {
    unique(): any;
  }
}

Array.prototype.unique = function (): any {
  const a = this.concat();
  for (let i = 0; i < a.length; ++i) {
    for (let j = i + 1; j < a.length; ++j) {
      if (a[i] === a[j]) a.splice(j--, 1);
    }
  }

  return a;
};
