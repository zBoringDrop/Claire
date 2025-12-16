export interface Tool {
    id: number,
    name: string,
    type: string
}

export interface AI extends Tool {
    model: string,
    family: string  | null | undefined,
    parameter_size: string | null | undefined,
    size: number | null | undefined,
    ai_type: string,
    ai_provider: string,
    is_active: boolean,
    modified_at: Date,
    last_db_sync: Date,
    json_extra: JSON
}

export interface AIPreview extends Tool {
    ai_id: number;
    model: string;
    family: string | null | undefined,
    parameter_size: string | null | undefined,
    size: number | null | undefined,
    provider_id: number;
    ai_description: string | null | undefined,
    active: boolean;
    modified_at: Date;
    last_db_sync: Date;
    ai_json_extra: string | null | undefined,
    provider_name: string;
    base_url: string;
    provider_type: string;
    provider_description: string | null | undefined,
}

export interface GoogleAiJsonExtra {
  name: string | null | undefined,
  displayName: string | null | undefined,
  description: string | null | undefined,
  version: string | null | undefined,
  inputTokenLimit: number | null | undefined,
  outputTokenLimit: number | null | undefined,
  supportedGenerationMethods: string[] | null | undefined,
  temperature: number | null | undefined,
  topP: number | null | undefined,
  topK: number | null | undefined,
  thinking: any | null | undefined,
}