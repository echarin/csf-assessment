import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArchiveListComponent } from './components/archive-list/archive-list.component';
import { ArchiveUploadComponent } from './components/archive-upload/archive-upload.component';
import { ArchiveDisplayComponent } from './components/archive-display/archive-display.component';

const routes: Routes = [
  { path: 'list', component: ArchiveListComponent },
  { path: 'upload', component: ArchiveUploadComponent },
  { path: 'display', component: ArchiveDisplayComponent },
  { path: '**', redirectTo: '/list', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
