export interface AIProvider {
  id: number;
  name: string;
  base_url: string;
  type: string;
  description: string;
}

export interface UserProviderApiKey {
  id: number | null;
  user_id: number;
  provider_id: number;
  api_key: string;
  active: boolean;
}

export interface UserProviderApiKeyPreview {
  id: number | null;
  user_id: number;
  provider_id: number;
  provider_name: string;
  provider_type: string;
  base_url: string;
  description: string;
  api_key: string | null;
  active: boolean | null;
}

