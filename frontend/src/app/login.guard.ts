import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const loginGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isLoggedIn()) {
    // Si l'utilisateur est déjà connecté, on le redirige vers le tableau de bord
    return router.parseUrl('/admin/dashboard');
  }

  // Sinon, on autorise l'accès à la page de connexion
  return true;
};
