import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { UserGoalsDetailComponent } from 'app/entities/user-goals/user-goals-detail.component';
import { UserGoals } from 'app/shared/model/user-goals.model';

describe('Component Tests', () => {
  describe('UserGoals Management Detail Component', () => {
    let comp: UserGoalsDetailComponent;
    let fixture: ComponentFixture<UserGoalsDetailComponent>;
    const route = ({ data: of({ userGoals: new UserGoals(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [UserGoalsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserGoalsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserGoalsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userGoals on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userGoals).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
