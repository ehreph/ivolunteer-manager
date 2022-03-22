import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { SideBarItemConfig } from 'app/shared/admin/side-bar-item/side-bar-item.model';

// tslint:disable-next-line:component-selector
@Component({
  selector: '[jhi-side-bar-item]',
  templateUrl: './side-bar-item.component.html',
  styleUrls: ['./side-bar-item.component.scss'],
})
export class SideBarItemComponent implements OnInit {
  click = new EventEmitter<void>();

  @Input()
  item: SideBarItemConfig;

  constructor() {}

  ngOnInit(): void {}

  _click(): void {
    this.click.emit();
  }
}
