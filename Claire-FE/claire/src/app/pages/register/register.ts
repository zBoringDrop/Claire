import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { UserService } from '../../services/user-service';
import { User, UserRegistration } from '../../types/user.type';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { ProblemDetails } from '../../types/problemdetails';
import { VIOLATION_MESSAGE } from '../../constants/violation-messages';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {

  isRegister = signal<boolean>(false)

  constructor(private userService: UserService,
              private router: Router
  ) {}

  private formBuilder = inject(FormBuilder)

  registrationForm = this.formBuilder.group({
    name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    surname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    nickname: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    email: ['', [Validators.required,  Validators.email, Validators.minLength(3), Validators.maxLength(70)]],
    password: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
    repeatPassword: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]]
  }, {
    validators: this.passwordsMatchValidator.bind(this)
  })

  get name() {
    return this.registrationForm.get('name')
  }
  get surname() {
    return this.registrationForm.get('surname')
  }
  get nickname() {
    return this.registrationForm.get('nickname')
  }
  get email() {
    return this.registrationForm.get('email')
  }
  get password() {
    return this.registrationForm.get('password')
  }
  get repeatPassword() {
    return this.registrationForm.get('repeatPassword')
  }

  passwordsMatchValidator(control: AbstractControl): ValidationErrors | null {
    const passw = control.get('password')?.value
    const repeatPassw = control.get('repeatPassword')?.value
    return passw === repeatPassw ? null : {passwordMismatch: true}
  };

  generalMessage = signal('')

  showGeneralMessage(msg: string) {
    this.generalMessage.set(msg)
  }
  
  setServerViolations(error: ProblemDetails) {
    const v = error.violations;

    if (v['name']) {
      this.name?.setErrors({ api: VIOLATION_MESSAGE[v['name']] });
    }

    if (v['surname']) {
      this.surname?.setErrors({ api: VIOLATION_MESSAGE[v['surname']] });
    }

    if (v['nickname']) {
      this.nickname?.setErrors({ api: VIOLATION_MESSAGE[v['nickname']] });
    }

    if (v['email']) {
      this.email?.setErrors({ api: VIOLATION_MESSAGE[v['email']] });
    }

    if (v['password']) {
      this.password?.setErrors({ api: VIOLATION_MESSAGE[v['password']] });
    }
  }

  clearSensitiveFields() {
    this.password?.setValue('')
    this.repeatPassword?.setValue('')
  }

  sendRegistration() {

    this.isRegister.set(true)

    if (this.registrationForm.invalid) {
      console.log('Form is invalid!')
      this.registrationForm.markAllAsTouched()
      this.isRegister.set(false)
      return
    }

    const newUser = this.registrationForm.value as UserRegistration

    this.userService.register(newUser).subscribe({
      next: (user: User) => {
        this.showGeneralMessage('')
        console.log("Registration completed: ", user)
        this.router.navigate(['/login'])
        this.isRegister.set(false)
      },
      error: (err: HttpErrorResponse) => {
        const error = err.error as ProblemDetails
        this.showGeneralMessage(error.title)
        this.setServerViolations(error)
        this.registrationForm.markAllAsTouched();
        this.isRegister.set(false)
      }
    })
  }
}
