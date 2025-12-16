import { Component, inject, signal } from '@angular/core';
import { FormsModule, FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import { AuthService } from '../../services/auth-service';
import { UserLogin } from '../../types/user.type';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthenticationResponse } from '../../types/auth.type';
import { ProblemDetails } from '../../types/problemdetails';
import { Router, RouterLink } from '@angular/router';
import { VIOLATION_MESSAGE } from '../../constants/violation-messages';

@Component({
  selector: 'app-login',
  imports: [FormsModule, RouterLink, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  isLogin = signal<boolean>(false)

  constructor(private authService: AuthService,
              private router: Router) {}

  private formBuilder = inject(FormBuilder)

  authForm = this.formBuilder.group({
    email: ['', [Validators.required,  Validators.email, Validators.minLength(3), Validators.maxLength(70)]],
    password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]]
  })

  get email() {
    return this.authForm.get('email')
  }

  get password() {
    return this.authForm.get('password')
  }

  generalMessage = signal('')

  showGeneralMessage(msg: string) {
    this.generalMessage.set(msg)
  }

  setViolations(error: ProblemDetails) {
    const v = error.violations;

    if (v['email']) {
      this.email?.setErrors({api: VIOLATION_MESSAGE[v['email']]})
    }

    if (v['password']) {
      this.password?.setErrors({api: VIOLATION_MESSAGE[v['password']]})
    }
  }

  sendLogin() {
    this.isLogin.set(true)
    
    const userLogin: UserLogin = this.authForm.value as UserLogin

    this.authService.login(userLogin).subscribe({
      next: (res: AuthenticationResponse) => {
        console.log("Res: ", res)
        this.showGeneralMessage('')
        this.authService.setJwtToken(res.jwt_token)
        this.router.navigate(['/dashboard'])
        this.isLogin.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        console.log("Error: ", error)
        this.showGeneralMessage(error.title)
        this.isLogin.set(false)
        this.setViolations(error)
        this.authForm.markAllAsTouched()
      }
    })
  }

}
