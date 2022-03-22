import { Component, OnDestroy, OnInit } from '@angular/core';
import { IVisibleGoal } from '../../shared/model/goal.model';
import { combineLatest, Subscription } from 'rxjs';
import { ITEMS_PER_PAGE } from '../../shared/constants/pagination.constants';
import { ActivatedRoute, Data, ParamMap, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { VisibleGoalsService } from '../../shared/services/visible-goals.service';
import { UserGoalsService } from '../../shared/services/user-goals.service';

@Component({
  selector: 'jhi-visible-goals',
  templateUrl: './visible-goals.component.html',
  styleUrls: ['./visible-goals.component.scss'],
})
export class VisibleGoalsComponent implements OnInit, OnDestroy {
  goals?: IVisibleGoal[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected visibleGoalsService: VisibleGoalsService,
    protected userGoalsService: UserGoalsService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.visibleGoalsService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IVisibleGoal[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInGoals();
  }

  protected handleNavigation(): void {
    combineLatest(this.activatedRoute.data, this.activatedRoute.queryParamMap, (data: Data, params: ParamMap) => {
      const page = params.get('page');
      const pageNumber = page !== null ? +page : 1;
      const sort = (params.get('sort') ?? data['defaultSort']).split(',');
      const predicate = sort[0];
      const ascending = sort[1] === 'asc';
      if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
        this.predicate = predicate;
        this.ascending = ascending;
        this.loadPage(pageNumber, true);
      }
    }).subscribe();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVisibleGoal): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGoals(): void {
    this.eventSubscriber = this.eventManager.subscribe('visibleGoalListModification', () => this.loadPage());
  }

  // delete(goal: IVisibleGoal): void {
  //   const modalRef = this.modalService.open(GoalDeleteDialogComponent, {size: 'lg', backdrop: 'static'});
  //   modalRef.componentInstance.goal = goal;
  // }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IVisibleGoal[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    if (navigate) {
      this.router.navigate(['/overview'], {
        queryParams: {
          page: this.page,
          size: this.itemsPerPage,
          sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc'),
        },
      });
    }
    this.goals = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
