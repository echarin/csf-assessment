import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArchiveService {
  private apiUrl = environment.sbApiUrl;
  // Do not set Content-Type, or else it will not give multipart boundaries
  // private headers = new HttpHeaders().set('Content-Type', 'multipart/form-data');

  constructor(private httpClient: HttpClient) { }

  // Probably returns Observable of Bundle
  postUpload(fd: FormData): Observable<any> {
    const url = `${this.apiUrl}/upload`;
    return this.httpClient.post(url, fd);
  }
}
