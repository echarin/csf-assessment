import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArchiveListComponent } from './components/archive-list/archive-list.component';
import { ArchiveUploadComponent } from './components/archive-upload/archive-upload.component';
import { ArchiveDisplayComponent } from './components/archive-display/archive-display.component';

const routes: Routes = [
  { path: 'home', component: ArchiveListComponent },
  { path: 'form', component: ArchiveUploadComponent },
  { path: 'display/:bundleId', component: ArchiveDisplayComponent },
  { path: '**', redirectTo: '/home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
