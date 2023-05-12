import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Bundle } from 'src/app/models/bundle';
import { ArchiveService } from 'src/app/services/archive.service';

@Component({
  selector: 'app-archive-list',
  templateUrl: './archive-list.component.html',
  styleUrls: ['./archive-list.component.css']
})
export class ArchiveListComponent implements OnInit, OnDestroy {
  archives!: Bundle[];
  private unsubscribe$ = new Subject<void>();

  constructor(private aSvc: ArchiveService, private router: Router) { }

  ngOnInit(): void {
    this.aSvc.getBundles().pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe({
      next: (bundles) => {
        if (bundles)
          this.archives = bundles;
      },
      error: (err) => {
        console.log(err);
      },
      complete: () => { }
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  goUpload(): void {
    this.router.navigate(['/form']);
  }
}
