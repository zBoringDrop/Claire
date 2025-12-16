import { CategoryIcon } from "../types/category.type"

export const DEFAULT_CATEGORIES: CategoryIcon[] = [
  {
    type: 'SECURITY',
    icon: 'fi-rr-shield-keyhole',
    color: 'text-yellow-400',
    bg_color: 'bg-yellow-400/20',
    border_color: 'border-yellow-400', 
    hasCheckedBg: 'has-checked:bg-yellow-400/30',
    hasCheckedBorder: 'has-checked:border-yellow-400',
    hasCheckedColor: 'has-checked:text-yellow-400',
    hoverBg: 'hover:bg-yellow-400/20',
    hoverBorder: 'hover:border-yellow-400'
  },
  {
    type: 'RELIABILITY_AND_BUGS',
    icon: 'fi-rr-bug',
    color: 'text-red-400',
    bg_color: 'bg-red-400/20',
    border_color: 'border-red-400',
    hasCheckedBg: 'has-checked:bg-red-400/30',
    hasCheckedBorder: 'has-checked:border-red-400',
    hasCheckedColor: 'has-checked:text-red-400',
    hoverBg: 'hover:bg-red-400/20',
    hoverBorder: 'hover:border-red-400'
  },
  {
    type: 'PERFORMANCE',
    icon: 'fi-rr-dashboard',
    color: 'text-teal-400',
    bg_color: 'bg-teal-400/20',
    border_color: 'border-teal-400',
    hasCheckedBg: 'has-checked:bg-teal-400/30',
    hasCheckedBorder: 'has-checked:border-teal-400',
    hasCheckedColor: 'has-checked:text-teal-400',
    hoverBg: 'hover:bg-teal-400/20',
    hoverBorder: 'hover:border-teal-400'
  },
  {
    type: 'ARCHITECTURE_AND_DESIGN',
    icon: 'fi-rr-drafting-compass',
    color: 'text-blue-400',
    bg_color: 'bg-blue-400/20',
    border_color: 'border-blue-400',
    hasCheckedBg: 'has-checked:bg-blue-400/30',
    hasCheckedBorder: 'has-checked:border-blue-400',
    hasCheckedColor: 'has-checked:text-blue-400',
    hoverBg: 'hover:bg-blue-400/20',
    hoverBorder: 'hover:border-blue-400'
  },
  {
    type: 'CODE_STRUCTURE_AND_COMPLEXITY',
    icon: 'fi-rr-curve-arrow',
    color: 'text-green-400',
    bg_color: 'bg-green-400/20',
    border_color: 'border-green-400',
    hasCheckedBg: 'has-checked:bg-green-400/30',
    hasCheckedBorder: 'has-checked:border-green-400',
    hasCheckedColor: 'has-checked:text-green-400',
    hoverBg: 'hover:bg-green-400/20',
    hoverBorder: 'hover:border-green-400'
  },
  {
    type: 'NAMING_AND_DOCUMENTATION',
    icon: 'fi-rr-features-alt',
    color: 'text-orange-400',
    bg_color: 'bg-orange-400/20',
    border_color: 'border-orange-400',
    hasCheckedBg: 'has-checked:bg-orange-400/30',
    hasCheckedBorder: 'has-checked:border-orange-400',
    hasCheckedColor: 'has-checked:text-orange-400',
    hoverBg: 'hover:bg-orange-400/20',
    hoverBorder: 'hover:border-orange-400'
  }
] as const;


export function getDefaultCategory(typeName: string): CategoryIcon | null {
  for (const category of DEFAULT_CATEGORIES) {
    if (category.type === typeName) {
      return category;
    }
  }
  return null;
}