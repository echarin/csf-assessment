import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Bundle } from '../models/bundle';

@Injectable({
  providedIn: 'root'
})
export class ArchiveService {
  private apiUrl = environment.sbApiUrl;
  private jsonHeaders = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private httpClient: HttpClient) { }

  // Probably returns Observable of Bundle
  postUpload(fd: FormData): Observable<Bundle> {
    const url = `${this.apiUrl}/upload`;
    // Do not set Content-Type, or else it will not give multipart boundaries
    return this.httpClient.post<Bundle>(url, fd);
  }

  getBundleById(bundleId: number): Observable<Bundle> {
    const url = `${this.apiUrl}/bundle/${bundleId}`;
    return this.httpClient.get<Bundle>(url, { headers: this.jsonHeaders });
  }

  getBundles(): Observable<Bundle[]> {
    const url = `${this.apiUrl}/bundles`;
    return this.httpClient.get<Bundle[]>(url, { headers: this.jsonHeaders });
  }
}
