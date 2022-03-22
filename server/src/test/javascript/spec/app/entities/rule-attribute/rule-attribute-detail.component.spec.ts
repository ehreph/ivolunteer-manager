import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { RuleAttributeDetailComponent } from 'app/entities/rule-attribute/rule-attribute-detail.component';
import { RuleAttribute } from 'app/shared/model/rule-attribute.model';

describe('Component Tests', () => {
  describe('RuleAttribute Management Detail Component', () => {
    let comp: RuleAttributeDetailComponent;
    let fixture: ComponentFixture<RuleAttributeDetailComponent>;
    const route = ({ data: of({ ruleAttribute: new RuleAttribute(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [RuleAttributeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(RuleAttributeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RuleAttributeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load ruleAttribute on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.ruleAttribute).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
