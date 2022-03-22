import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEMS_PER_PAGE } from '../../../shared/constants/pagination.constants';
import { UserBadgeService } from '@services/user-badge.service';
import { IUserBadge } from '@model/user-badge.model';
import { UserBadgeUpdateModalComponent } from 'app/shared/general/user-badges-overview/user-badges-update-modal/user-badge-update-modal.component';
import { UserBadgeDeleteDialogComponent } from 'app/shared/general/user-badges-overview/user-badge-delete-dialog/user-badge-delete-dialog.component';

@Component({
  selector: 'jhi-user-badges-overview',
  templateUrl: './user-badges-overview.component.html',
  styleUrls: ['./user-badges-overview.component.scss'],
})
export class UserBadgesOverviewComponent implements OnInit, OnDestroy {
  userBadges?: IUserBadge[];
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
    protected userBadgeService: UserBadgeService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.userBadgeService
      .queryByUserId(this.userId, {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IUserBadge[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.handleNavigation();
    this.registerChangeInUserBadges();
  }

  protected handleNavigation(): void {
    const pageNumber = 1;
    const predicate = 'id';
    const ascending = true;
    if (pageNumber !== this.page || predicate !== this.predicate || ascending !== this.ascending) {
      this.predicate = predicate;
      this.ascending = ascending;
      this.loadPage(pageNumber, true);
    }
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserBadge): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserBadges(): void {
    this.eventSubscriber = this.eventManager.subscribe('userBadgeListModification', () => this.loadPage());
  }

  delete(iUserBadge: IUserBadge): void {
    const modalRef = this.modalService.open(UserBadgeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userBadge = iUserBadge;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IUserBadge[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.userBadges = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  openUpdateModal(userBadge: IUserBadge & any): void {
    const modalRef = this.modalService.open(UserBadgeUpdateModalComponent);
    modalRef.componentInstance.userBadge = userBadge;
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
