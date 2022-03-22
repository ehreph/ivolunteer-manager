import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEMS_PER_PAGE } from '../../../shared/constants/pagination.constants';
import { UserGoalUpdateModalComponent } from './user-goal-update-modal/user-goal-update-modal.component';
import { IUserGoals } from '../../../shared/model/user-goals.model';
import { UserGoalsService } from '@services/user-goals.service';
import { UserGoalsDeleteDialogComponent } from 'app/shared/general/user-goals-overview/user-goal-delete-dialog/user-goals-delete-dialog.component';

@Component({
  selector: 'jhi-user-goals-overview',
  templateUrl: './user-goals-overview.component.html',
  styleUrls: ['./user-goals-overview.component.scss'],
})
export class UserGoalsOverviewComponent implements OnInit, OnDestroy {
  iUserGoals?: IUserGoals[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  @Input() userId: any;
  @Input() isAdminPage: boolean = false;

  constructor(
    protected userGoalService: UserGoalsService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page || 1;

    this.userGoalService
      .queryByUserId(this.userId, {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IUserGoals[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInUserCompetences();
  }

  protected handleNavigation(): void {
    const pageNumber = 1;
    const predicate = 'id';
    const ascending = true;
    if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
      this.predicate = predicate;
      this.ascending = ascending;
      this.loadPage(pageNumber);
    }
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserGoals): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserCompetences(): void {
    this.eventSubscriber = this.eventManager.subscribe('userGoalsListModification', () => this.loadPage());
  }

  delete(userGoals: IUserGoals): void {
    const modalRef = this.modalService.open(UserGoalsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userGoals = userGoals;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IUserGoals[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.iUserGoals = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  openUpdateModal(userGoals: IUserGoals & any): void {
    const modalRef = this.modalService.open(UserGoalUpdateModalComponent);
    modalRef.componentInstance.userGoal = userGoals;
    modalRef.componentInstance.userId = this.userId;
    modalRef.result.then(
      res => {
        if (res) {
          this.loadPage();
        }
      },
      reason => {}
    );
  }
}
