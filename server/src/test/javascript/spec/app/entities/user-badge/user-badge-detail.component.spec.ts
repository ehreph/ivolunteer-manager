import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { UserBadgeDetailComponent } from 'app/entities/user-badge/user-badge-detail.component';
import { UserBadge } from 'app/shared/model/user-badge.model';

describe('Component Tests', () => {
  describe('UserBadge Management Detail Component', () => {
    let comp: UserBadgeDetailComponent;
    let fixture: ComponentFixture<UserBadgeDetailComponent>;
    const route = ({ data: of({ userBadge: new UserBadge(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [UserBadgeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserBadgeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserBadgeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userBadge on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userBadge).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
