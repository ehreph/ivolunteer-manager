import { Component, Input, OnInit } from '@angular/core';
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons/faTimesCircle';
import { faCheckCircle } from '@fortawesome/free-solid-svg-icons/faCheckCircle';
@Component({
  selector: 'jhi-completed-extension',
  templateUrl: './completed-extension.component.html',
  styleUrls: ['./completed-extension.component.scss'],
})
export class CompletedExtensionComponent implements OnInit {
  @Input()
  completed: boolean;

  faCheckCircle = faCheckCircle;
  faTimesCircle = faTimesCircle;
  constructor() {}

  ngOnInit(): void {}
}
