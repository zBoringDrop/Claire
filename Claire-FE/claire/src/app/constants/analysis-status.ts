export const ANALYSIS_STATUS = {
    COMPLETED: "COMPLETED",
    IN_PROGRESS: "IN_PROGRESS",
    VALIDATING: "VALIDATING",
    PARTIAL: "PARTIAL",
    FAILED: "FAILED"
} as const

export function inProgress(status: string): boolean {
    return (status !== ANALYSIS_STATUS.FAILED
    && status !== ANALYSIS_STATUS.COMPLETED
    && status !== ANALYSIS_STATUS.PARTIAL)
}

export function notRunning(status: string): boolean {
    return (status === ANALYSIS_STATUS.FAILED
        || status === ANALYSIS_STATUS.COMPLETED
        || status === ANALYSIS_STATUS.PARTIAL)
}

const analysisStatusColors = [
    {
        status: ANALYSIS_STATUS.COMPLETED,
        bgClass: 'bg-emerald-500/15',
        borderClass: 'border-emerald-500/50',
        textClass: 'text-emerald-400',
        icon: 'fi-rr-check-circle',
        iconClass: 'text-emerald-500',
        label: 'Completed'
    },
    {
        status: ANALYSIS_STATUS.IN_PROGRESS,
        bgClass: 'bg-blue-500/15',
        borderClass: 'border-blue-500/50',
        textClass: 'text-blue-400',
        icon: 'fi-rr-spinner',
        iconClass: 'text-blue-500 animate-spin',
        label: 'Analyzing...'
    },
    {
        status: ANALYSIS_STATUS.VALIDATING,
        bgClass: 'bg-violet-500/15',
        borderClass: 'border-violet-500/50',
        textClass: 'text-violet-400',
        icon: 'fi-rr-magic-wand',
        iconClass: 'text-violet-500 animate-pulse',
        label: 'Validating...'
    },
    {
        status: ANALYSIS_STATUS.PARTIAL,
        bgClass: 'bg-amber-500/15',
        borderClass: 'border-amber-500/50',
        textClass: 'text-amber-400',
        icon: 'fi-rr-exclamation',
        iconClass: 'text-amber-500',
        label: 'Partial Success'
    },
    {
        status: ANALYSIS_STATUS.FAILED,
        bgClass: 'bg-red-500/15',
        borderClass: 'border-red-500/50',
        textClass: 'text-red-400',
        icon: 'fi-rr-cross-circle',
        iconClass: 'text-red-500',
        label: 'Analysis Failed'
    }
]

export function getStatusConfig(status: string | undefined | null) {
    return analysisStatusColors.find(s => s.status === status) || analysisStatusColors.find(s => s.status === ANALYSIS_STATUS.FAILED);
}