import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { GoalRequirementsDetailComponent } from 'app/entities/goal-requirements/goal-requirements-detail.component';
import { GoalRequirements } from 'app/shared/model/goal-requirements.model';

describe('Component Tests', () => {
  describe('GoalRequirements Management Detail Component', () => {
    let comp: GoalRequirementsDetailComponent;
    let fixture: ComponentFixture<GoalRequirementsDetailComponent>;
    const route = ({ data: of({ goalRequirements: new GoalRequirements(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [GoalRequirementsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GoalRequirementsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoalRequirementsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goalRequirements on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goalRequirements).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
