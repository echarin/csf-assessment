import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subject, of, switchMap, takeUntil } from 'rxjs';
import { Bundle } from 'src/app/models/bundle';
import { ArchiveService } from 'src/app/services/archive.service';

@Component({
  selector: 'app-archive-display',
  templateUrl: './archive-display.component.html',
  styleUrls: ['./archive-display.component.css']
})
export class ArchiveDisplayComponent implements OnInit, OnDestroy {
  bundle!: Bundle;
  private unsubscribe$ = new Subject<void>();

  constructor(
    private aSvc: ArchiveService,
    private activatedRoute: ActivatedRoute, 
    private router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.pipe(
      switchMap((params) => {
        const bundleId: String = params['bundleId'];
        return bundleId? this.aSvc.getBundleById(bundleId) : of(null);
      }),
      takeUntil(this.unsubscribe$)
    ).subscribe({
      next: (bundle) => {
        if (bundle)
          this.bundle = bundle;
      },
      error: (err) => {
        console.log(err);
        this.router.navigate(['/home']);
      }
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  // This is view 2: details; go back to view 1: upload
  goBack(): void {
    this.router.navigate(['/form']);
  }
}
