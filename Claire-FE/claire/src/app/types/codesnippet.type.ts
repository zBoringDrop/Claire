export interface CodeSnippet {
    id: number,
    user_id: number,
    title: string,
    creation_datetime: Date,
    programming_language_id: number,
    code_text: string,
    deleted: boolean
}

export interface CodeSnippetPreview {
    id: number,
    user_id: number,
    title: string,
    creation_datetime: Date,
    language: string,
    code_text: string
    analysis_count: number,
    deleted: boolean
}