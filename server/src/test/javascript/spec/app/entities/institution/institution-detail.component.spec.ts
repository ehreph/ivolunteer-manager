import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IvolunteerManagerTestModule } from '../../../test.module';
import { InstitutionDetailComponent } from 'app/entities/institution/institution-detail.component';
import { Institution } from 'app/shared/model/institution.model';

describe('Component Tests', () => {
  describe('Institution Management Detail Component', () => {
    let comp: InstitutionDetailComponent;
    let fixture: ComponentFixture<InstitutionDetailComponent>;
    const route = ({ data: of({ institution: new Institution(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IvolunteerManagerTestModule],
        declarations: [InstitutionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(InstitutionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InstitutionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load institution on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.institution).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
