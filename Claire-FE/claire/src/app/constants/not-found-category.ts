export interface NotFoundCategory {
    type: NotFoundCategoryEnum,
    name: string,
    icon: string,
    backLink: string,
    p1: string,
    p2: string,
    p3: string
}

export enum NotFoundCategoryEnum {
    analysisId,
    fileId,
    snippetId
}

export const NOT_FOUND_CATEGORIES: NotFoundCategory[] = [
  {
    type: NotFoundCategoryEnum.analysisId,
    name: 'Analysis ID',
    icon: 'fi-rr-search-alt',
    backLink: '/history',
    p1: 'The requested code analysis is nowhere to be found.',
    p2: 'It may have been removed or an alien ship may have extracted it from the database.',
    p3: 'Please check the analysis ID and try again.'
  },
  {
    type: NotFoundCategoryEnum.fileId,
    name: 'File ID',
    icon: 'fi-rr-clip-file',
    backLink: '/sources',
    p1: 'The requested file is nowhere to be found.',
    p2: 'It may have been removed or an alien ship may have extracted it from the database.',
    p3: 'Please check the file ID and try again.'
  },
  {
    type: NotFoundCategoryEnum.snippetId,
    name: 'Snippet ID',
    icon: 'fi-rr-square-code',
    backLink: '/sources',
    p1: 'The requested snippet is nowhere to be found.',
    p2: 'It may have been removed or an alien ship may have extracted it from the database.',
    p3: 'Please check the snippet ID and try again.'
  }
] as const

export const getNotFoundCategoryByType = (type: NotFoundCategoryEnum): NotFoundCategory | undefined => {
  return NOT_FOUND_CATEGORIES.find(v => v.type === type);
};