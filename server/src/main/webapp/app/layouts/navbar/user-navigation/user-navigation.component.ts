import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-user-navigation',
  templateUrl: './user-navigation.component.html',
  styleUrls: ['./user-navigation.component.scss'],
})
export class UserNavigationComponent implements OnInit {
  @Input() collapseNavbar: () => void;

  constructor() {}

  ngOnInit(): void {}
}
