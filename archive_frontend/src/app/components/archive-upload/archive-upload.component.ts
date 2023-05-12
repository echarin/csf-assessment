import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { ArchiveService } from 'src/app/services/archive.service';

@Component({
  selector: 'app-archive-upload',
  templateUrl: './archive-upload.component.html',
  styleUrls: ['./archive-upload.component.css']
})
export class ArchiveUploadComponent implements OnInit, OnDestroy {
  uploadForm!: FormGroup
  @ViewChild('archive') archiveFile!: ElementRef;
  private unsubscribe$ = new Subject<void>();

  constructor (
    private fb: FormBuilder, 
    private router: Router, 
    private aSvc: ArchiveService
  ) { }

  ngOnInit(): void {
    this.uploadForm = this.fb.group({
      name: this.fb.control('', [ Validators.required ]),
      title: this.fb.control('', [ Validators.required ]),
      comments: this.fb.control(''),
      archive: this.fb.control('', [ Validators.required ]),
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  onCancel(): void {
    this.router.navigate(['/home']);
  }
  

  onSubmit(): void {
    // Extract form values
    // Pass in values to aSvc
    if (this.uploadForm.valid) {
      const formData = new FormData();
      formData.set('name', this.uploadForm.get('name')?.value);
      formData.set('title', this.uploadForm.get('title')?.value);
      formData.set('comments', this.uploadForm.get('comments')?.value);
      formData.set('archive', this.archiveFile.nativeElement.files[0]);
      this.aSvc.postUpload(formData).pipe(
        takeUntil(this.unsubscribe$),
      ).subscribe({
        next: (bundle) => {
          this.router.navigate(['/display', bundle.bundleId]);
        },
        error: (err) => {
          // Todo
          console.log(err.error['error']);
          alert(err.error['error']);
        },
        complete: () => {}
      });
    }
  }
}
