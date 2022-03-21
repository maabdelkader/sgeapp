export interface ISocialOrganization {
  id?: number;
  name?: string | null;
  adminQuota?: number | null;
  commissionQuota?: number | null;
  proximityQuota?: number | null;
  numberOfAdmins?: number | null;
}

export class SocialOrganization implements ISocialOrganization {
  constructor(
    public id?: number,
    public name?: string | null,
    public adminQuota?: number | null,
    public commissionQuota?: number | null,
    public proximityQuota?: number | null,
    public numberOfAdmins?: number | null
  ) {}
}

export function getSocialOrganizationIdentifier(socialOrganization: ISocialOrganization): number | undefined {
  return socialOrganization.id;
}
