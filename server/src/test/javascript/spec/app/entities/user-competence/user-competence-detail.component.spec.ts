import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { UserCompetenceDetailComponent } from 'app/entities/user-competence/user-competence-detail.component';
import { UserCompetence } from 'app/shared/model/user-competence.model';

describe('Component Tests', () => {
  describe('UserCompetence Management Detail Component', () => {
    let comp: UserCompetenceDetailComponent;
    let fixture: ComponentFixture<UserCompetenceDetailComponent>;
    const route = ({ data: of({ userCompetence: new UserCompetence(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [UserCompetenceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserCompetenceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserCompetenceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userCompetence on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userCompetence).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
