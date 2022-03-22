import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IUserCompetence } from '../../../shared/model/user-competence.model';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ITEMS_PER_PAGE } from '../../../shared/constants/pagination.constants';
import { UserCompetenceUpdateModalComponent } from './user-competence-update-modal/user-competence-update-modal.component';
import { UserCompetenceService } from '@services/user-competence.service';
import { UserCompetenceDeleteDialogComponent } from 'app/shared/general/user-competences-overview/user-competence-delete-dialog/user-competence-delete-dialog.component';

@Component({
  selector: 'jhi-user-competences-overview',
  templateUrl: './user-competences-overview.component.html',
  styleUrls: ['./user-competences-overview.component.scss'],
})
export class UserCompetencesOverviewComponent implements OnInit, OnDestroy {
  userCompetences?: IUserCompetence[];
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
    protected userCompetenceService: UserCompetenceService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number, dontNavigate?: boolean): void {
    const pageToLoad: number = page || this.page || 1;

    this.userCompetenceService
      .queryByUserId(this.userId, {
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe(
        (res: HttpResponse<IUserCompetence[]>) => this.onSuccess(res.body, res.headers, pageToLoad, !dontNavigate),
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
      this.loadPage(pageNumber, true);
    }
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserCompetence): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserCompetences(): void {
    this.eventSubscriber = this.eventManager.subscribe('userCompetenceListModification', () => this.loadPage());
  }

  delete(userCompetence: IUserCompetence): void {
    const modalRef = this.modalService.open(UserCompetenceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userCompetence = userCompetence;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IUserCompetence[] | null, headers: HttpHeaders, page: number, navigate: boolean): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.userCompetences = data || [];
    this.ngbPaginationPage = this.page;
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }

  openUpdateModal(userCompetence: IUserCompetence & any): void {
    const modalRef = this.modalService.open(UserCompetenceUpdateModalComponent);
    modalRef.componentInstance.userCompetence = userCompetence;
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
