import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ArchiveListComponent } from './components/archive-list/archive-list.component';
import { ArchiveUploadComponent } from './components/archive-upload/archive-upload.component';
import { ArchiveDisplayComponent } from './components/archive-display/archive-display.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    ArchiveListComponent,
    ArchiveUploadComponent,
    ArchiveDisplayComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
