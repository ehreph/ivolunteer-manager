import { IconProp } from '@fortawesome/fontawesome-svg-core';

export class SideBarItemConfig {
  routerLink: string;
  icon: IconProp;
  translationKey: string;

  constructor(routerLink: string, icon: IconProp, translationKey: string) {
    this.routerLink = routerLink;
    this.icon = icon;
    this.translationKey = translationKey;
  }
}
