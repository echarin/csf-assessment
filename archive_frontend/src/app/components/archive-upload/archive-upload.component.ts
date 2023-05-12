import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ArchiveService } from 'src/app/services/archive.service';

@Component({
  selector: 'app-archive-upload',
  templateUrl: './archive-upload.component.html',
  styleUrls: ['./archive-upload.component.css']
})
export class ArchiveUploadComponent implements OnInit, OnDestroy {
  uploadForm!: FormGroup

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
      
  }

  onCancel(): void {
    this.router.navigate(['/list']);
  }
  

  onSubmit(): void {
    // Call upon aSvc
  }
}
