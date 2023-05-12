import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArchiveService {
  private apiUrl = environment.sbApiUrl;
  private headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private httpClient: HttpClient) { }

  // Probably returns Observable of Bundle
  postUpload(fd: FormData): Observable<any> {
    const url = `${this.apiUrl}/upload`;
    return this.httpClient.post(url, fd, { headers: this.headers });
  }
}
