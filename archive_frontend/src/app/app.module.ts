import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ArchiveDisplayComponent } from './components/archive-display/archive-display.component';
import { ArchiveListComponent } from './components/archive-list/archive-list.component';
import { ArchiveUploadComponent } from './components/archive-upload/archive-upload.component';

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
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
