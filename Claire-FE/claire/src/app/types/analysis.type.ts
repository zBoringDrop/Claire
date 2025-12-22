import { UserOllamaConfig } from "./userollamaconfig.type";

export interface AnalysisPreview {
    id: number;
    status: string;
    message: string;
    file_id: number | null | undefined;
    codesnippet_id: number | null | undefined;
    source_name: string;
    source_preview: string | null;
    source_deleted: boolean | null;
    tool_name: string;
    overall_severity: number;
    issues_count: number;
    categories: string[];
    created_at: Date
}

export interface AnalysisRequest {
    source_id: number,
    tool_id: number,
    analysis_categories: number[],
    use_cot: boolean
}

// Issue generico
export interface AnalysisIssue {
  location: string;
  type: string;
  severity: number;
  desc: string;
  why?: string;
  fix?: string;
  before?: string;
  after?: string;
  scenario?: string;
  principle?: string;
  benefit?: string;
  impact?: string;
  gain?: string;
  metric?: string;
  reduced?: string;
  current?: string;
  suggested?: string;
  clarity?: string;
}

// Meta informazioni per ogni categoria
export interface AnalysisMeta {
  category: string;
  prompt: string;
  json_schema: string;
  start_datetime: string;
  end_datetime: string;
  execution_ms: number;
  input_length: number;
}

// Oggetto dentro result_json
export interface AnalysisResultItem {
  meta: AnalysisMeta;
  output_length: number;
  status: string;
  response: AnalysisResponse;
}

export interface AnalysisResponse {
    thought_process: string;
    overall_severity: number;
    issues: AnalysisIssue[];
}

// Analisi completa
export interface AnalysisSchema {
  id: number;
  status: string;
  message: string;
  file_id?: number | null;
  codesnippet_id?: number | null;
  tool_id?: number | null;
  created_at: string;
  end_datetime: string;
  execution_ms: number;
  output_length: number;
  issues_count: number;
  overall_severity: number;
  result_json: AnalysisResultWrapper; 
  analysis_categoryIds?: number[];
  use_cot: boolean;
}

export interface AnalysisResultWrapper {
  configuration: UserOllamaConfig | null; 
  results: AnalysisResultItem[]; 
}