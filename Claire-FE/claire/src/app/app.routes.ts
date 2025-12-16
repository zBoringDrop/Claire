import { Routes } from '@angular/router';
import { Login } from './pages/login/login';
import { Register } from './pages/register/register';
import { Notfound } from './pages/notfound/notfound';
import { Dashboard } from './pages/dashboard/dashboard';
import { authGuard } from './guards/auth-guard';
import { AuthLayout } from './layouts/auth-layout/auth-layout';
import { MainLayout } from './layouts/main-layout/main-layout';
import { Analysis } from './pages/analysis/analysis';
import { History } from './pages/history/history';
import { AnalysisDetails } from './pages/analysis-details/analysis-details';
import { Settings } from './pages/settings/settings';
import { Sources } from './pages/sources/sources';
import { FileDetails } from './pages/file-details/file-details';
import { SnippetDetails } from './pages/snippet-details/snippet-details';

export const routes: Routes = [

  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  {
    path: '',
    component: MainLayout,
    children: [
      { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },

      {
        path: 'analysis',
        children: [
          { path: '', component: Analysis, canActivate: [authGuard] }
        ]
      },

      { path: 'notfound', component: Notfound },

      { 
        path: 'history', 
        children: [
          { path: '', component: History, canActivate: [authGuard] },
          { path: 'details/:id', component: AnalysisDetails }
        ]
      },
      
      { path: 'stats', component: Dashboard, canActivate: [authGuard] },

      { 
        path: 'sources', 
        children: [
          { path: '', component: Sources, canActivate: [authGuard] },
          { path: 'file/:id', component: FileDetails },
          { path: 'snippet/:id', component: SnippetDetails }
        ]
      },

      { path: 'settings', component: Settings, canActivate: [authGuard] },
    ]
  },

  {
    path: '',
    component: AuthLayout,
    children: [
      { path: 'login', component: Login },
      { path: 'register', component: Register }
    ]
  },

  { path: '**', redirectTo: 'notfound', pathMatch: 'full' }
];

