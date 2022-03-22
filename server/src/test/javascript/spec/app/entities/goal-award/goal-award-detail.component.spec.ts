import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { GoalAwardDetailComponent } from 'app/entities/goal-award/goal-award-detail.component';
import { GoalAward } from 'app/shared/model/goal-award.model';

describe('Component Tests', () => {
  describe('GoalAward Management Detail Component', () => {
    let comp: GoalAwardDetailComponent;
    let fixture: ComponentFixture<GoalAwardDetailComponent>;
    const route = ({ data: of({ goalAward: new GoalAward(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [GoalAwardDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GoalAwardDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoalAwardDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goalAward on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goalAward).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
