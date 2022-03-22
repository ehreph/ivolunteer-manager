import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { GoalAwardService } from 'app/entities/goal-award/goal-award.service';
import { IGoalAward, GoalAward } from 'app/shared/model/goal-award.model';
import { GlobalType } from 'app/shared/model/enumerations/global-type.model';

describe('Service Tests', () => {
  describe('GoalAward Service', () => {
    let injector: TestBed;
    let service: GoalAwardService;
    let httpMock: HttpTestingController;
    let elemDefault: IGoalAward;
    let expectedResult: IGoalAward | IGoalAward[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GoalAwardService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new GoalAward(0, GlobalType.GOAL, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a GoalAward', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new GoalAward()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a GoalAward', () => {
        const returnedFromService = Object.assign(
          {
            awardType: 'BBBBBB',
            generalId: 1,
            increaseLevel: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of GoalAward', () => {
        const returnedFromService = Object.assign(
          {
            awardType: 'BBBBBB',
            generalId: 1,
            increaseLevel: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a GoalAward', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
