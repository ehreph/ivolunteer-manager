import { Component, Input, OnInit } from '@angular/core';

// tslint:disable-next-line:component-selector
@Component({
  selector: '[jhi-info-item]',
  templateUrl: './info-item.component.html',
  styleUrls: ['./info-item.component.scss'],
})
export class InfoItemComponent implements OnInit {
  @Input() translationKey: string;

  @Input() value: any;

  constructor() {}

  ngOnInit(): void {}
}
