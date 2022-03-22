import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RuleAttributeService } from 'app/entities/rule-attribute/rule-attribute.service';
import { IRuleAttribute, RuleAttribute } from 'app/shared/model/rule-attribute.model';
import { GlobalType } from '../../../../../../main/webapp/app/shared/model/enumerations/global-type.constants';

describe('Service Tests', () => {
  describe('RuleAttribute Service', () => {
    let injector: TestBed;
    let service: RuleAttributeService;
    let httpMock: HttpTestingController;
    let elemDefault: IRuleAttribute;
    let expectedResult: IRuleAttribute | IRuleAttribute[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(RuleAttributeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new RuleAttribute(0, GlobalType.GOAL, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a RuleAttribute', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RuleAttribute()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RuleAttribute', () => {
        const returnedFromService = Object.assign(
          {
            ruleType: 'BBBBBB',
            name: 'BBBBBB',
            displayType: 'BBBBBB',
            unitName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RuleAttribute', () => {
        const returnedFromService = Object.assign(
          {
            ruleType: 'BBBBBB',
            name: 'BBBBBB',
            displayType: 'BBBBBB',
            unitName: 'BBBBBB',
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

      it('should delete a RuleAttribute', () => {
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
