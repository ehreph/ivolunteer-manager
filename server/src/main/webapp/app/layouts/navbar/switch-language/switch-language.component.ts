import { Component, Input, OnInit } from '@angular/core';
import { LANGUAGES } from 'app/core/language/language.constants';
import { JhiLanguageService } from 'ng-jhipster';
import { SessionStorageService } from 'ngx-webstorage';

@Component({
  selector: 'jhi-switch-language',
  templateUrl: './switch-language.component.html',
  styleUrls: ['./switch-language.component.scss'],
})
export class SwitchLanguageComponent implements OnInit {
  languages = LANGUAGES;

  @Input() collapseNavbar: () => void;

  constructor(private languageService: JhiLanguageService, private sessionStorage: SessionStorageService) {}

  ngOnInit(): void {}

  changeLanguage(languageKey: string): void {
    this.sessionStorage.store('locale', languageKey);
    this.languageService.changeLanguage(languageKey);
  }
}
