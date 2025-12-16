export interface Category {
    id: number,
    type: string,
    description: string,
    prompt: string,
    json_schema: string,
    icon: string
}

export interface CategoryIcon {
  type: string;
  icon: string;
  color: string;
  bg_color: string;
  border_color: string;
  hasCheckedBg: string;
  hasCheckedBorder: string;
  hasCheckedColor: string;
  hoverBg: string;
  hoverBorder: string;
}

export interface AnalysisIdCategoryName {
    category_id: number,
    category_name: string
}

export interface AnalysisIdsCategoryName {
    analysis_id: number,
    category_name: string
}