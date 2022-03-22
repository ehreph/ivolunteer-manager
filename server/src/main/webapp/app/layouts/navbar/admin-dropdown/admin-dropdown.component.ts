import { Component, Input, OnInit } from '@angular/core';
import { ProfileService } from 'app/layouts/profiles/profile.service';

@Component({
  selector: 'jhi-admin-dropdown',
  templateUrl: './admin-dropdown.component.html',
  styleUrls: ['./admin-dropdown.component.scss'],
})
export class AdminDropdownComponent implements OnInit {
  inProduction?: boolean;
  swaggerEnabled?: boolean;
  @Input() collapseNavbar: () => void;

  constructor(private profileService: ProfileService) {}

  ngOnInit(): void {
    this.profileService.getProfileInfo().subscribe(profileInfo => {
      this.inProduction = profileInfo.inProduction;
      this.swaggerEnabled = profileInfo.swaggerEnabled;
    });
  }
}
