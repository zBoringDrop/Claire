export interface FileCode {
    id: number,
    user_id: number,
    filename: string,
    content_type: string,
    upload_datetime: Date,
    programming_language_id: number,
    size: number,
    data: string,
    deleted: boolean
}

export interface FileCodePreview {
    id: number,
    user_id: number,
    filename: string,
    content_type: string,
    upload_datetime: Date,
    language: string,
    size: number,
    data: string,
    analysis_count: number,
    deleted: boolean
}