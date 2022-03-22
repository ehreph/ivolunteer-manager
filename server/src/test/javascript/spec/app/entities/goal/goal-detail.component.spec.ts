import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { GoalDetailComponent } from 'app/entities/goal/goal-detail.component';
import { Goal } from 'app/shared/model/goal.model';

describe('Component Tests', () => {
  describe('Goal Management Detail Component', () => {
    let comp: GoalDetailComponent;
    let fixture: ComponentFixture<GoalDetailComponent>;
    const route = ({ data: of({ goal: new Goal(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [GoalDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GoalDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoalDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goal on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goal).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
