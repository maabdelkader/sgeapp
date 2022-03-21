import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocialOrganization, getSocialOrganizationIdentifier } from '../social-organization.model';

export type EntityResponseType = HttpResponse<ISocialOrganization>;
export type EntityArrayResponseType = HttpResponse<ISocialOrganization[]>;

@Injectable({ providedIn: 'root' })
export class SocialOrganizationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/social-organizations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(socialOrganization: ISocialOrganization): Observable<EntityResponseType> {
    return this.http.post<ISocialOrganization>(this.resourceUrl, socialOrganization, { observe: 'response' });
  }

  update(socialOrganization: ISocialOrganization): Observable<EntityResponseType> {
    return this.http.put<ISocialOrganization>(
      `${this.resourceUrl}/${getSocialOrganizationIdentifier(socialOrganization) as number}`,
      socialOrganization,
      { observe: 'response' }
    );
  }

  partialUpdate(socialOrganization: ISocialOrganization): Observable<EntityResponseType> {
    return this.http.patch<ISocialOrganization>(
      `${this.resourceUrl}/${getSocialOrganizationIdentifier(socialOrganization) as number}`,
      socialOrganization,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISocialOrganization>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISocialOrganization[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSocialOrganizationToCollectionIfMissing(
    socialOrganizationCollection: ISocialOrganization[],
    ...socialOrganizationsToCheck: (ISocialOrganization | null | undefined)[]
  ): ISocialOrganization[] {
    const socialOrganizations: ISocialOrganization[] = socialOrganizationsToCheck.filter(isPresent);
    if (socialOrganizations.length > 0) {
      const socialOrganizationCollectionIdentifiers = socialOrganizationCollection.map(
        socialOrganizationItem => getSocialOrganizationIdentifier(socialOrganizationItem)!
      );
      const socialOrganizationsToAdd = socialOrganizations.filter(socialOrganizationItem => {
        const socialOrganizationIdentifier = getSocialOrganizationIdentifier(socialOrganizationItem);
        if (socialOrganizationIdentifier == null || socialOrganizationCollectionIdentifiers.includes(socialOrganizationIdentifier)) {
          return false;
        }
        socialOrganizationCollectionIdentifiers.push(socialOrganizationIdentifier);
        return true;
      });
      return [...socialOrganizationsToAdd, ...socialOrganizationCollection];
    }
    return socialOrganizationCollection;
  }
}
