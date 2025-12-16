const overallScoreColors = [
    { 
        value: 0, 
        bgValueClass: 'bg-green-400/80', 
        bgTitleClass: 'bg-green-400/25', 
        borderClass: 'border-green-400',
        hoverBgClass: 'hover:bg-green-500/70'
    },
    { 
        value: 1, 
        bgValueClass: 'bg-emerald-400/80', 
        bgTitleClass: 'bg-emerald-400/25', 
        borderClass: 'border-emerald-400',
        hoverBgClass: 'hover:bg-emerald-500/70'
    },
    { 
        value: 2, 
        bgValueClass: 'bg-yellow-300/80', 
        bgTitleClass: 'bg-yellow-300/25', 
        borderClass: 'border-yellow-300',
        hoverBgClass: 'hover:bg-yellow-400/70'
    },
    { 
        value: 3, 
        bgValueClass: 'bg-orange-400/80', 
        bgTitleClass: 'bg-orange-400/25', 
        borderClass: 'border-orange-400',
        hoverBgClass: 'hover:bg-orange-500/70'
    },
    { 
        value: 4, 
        bgValueClass: 'bg-red-400/80', 
        bgTitleClass: 'bg-red-400/25', 
        borderClass: 'border-red-400',
        hoverBgClass: 'hover:bg-red-500/70'
    },
    { 
        value: 5, 
        bgValueClass: 'bg-red-600/80', 
        bgTitleClass: 'bg-red-600/25', 
        borderClass: 'border-red-600',
        hoverBgClass: 'hover:bg-red-700/70'
    }
];

export function getScoreColor(score: number) {
    return overallScoreColors.find(c => c.value === score) || overallScoreColors[0];
}