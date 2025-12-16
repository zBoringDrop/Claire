export interface ProblemDetails {
    type: string,
    title: string,
    status: number;
    violations: {
        [field: string]: string
    }
}