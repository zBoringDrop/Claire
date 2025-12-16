import { VIOLATION } from './validation-codes';

export const VIOLATION_MESSAGE: Record<string, string> = {
  [VIOLATION.NAME_BLANK]: "Name can't be empty",
  [VIOLATION.NAME_SIZE]: "Name must be between 3 and 30 characters",

  [VIOLATION.SURNAME_BLANK]: "Surname can't be empty",
  [VIOLATION.SURNAME_SIZE]: "Surname must be between 3 and 30 characters",

  [VIOLATION.NICKNAME_BLANK]: "Nickname can't be empty",
  [VIOLATION.NICKNAME_SIZE]: "Nickname must be between 3 and 30 characters",

  [VIOLATION.EMAIL_BLANK]: "Email can't be empty",
  [VIOLATION.EMAIL_INVALID]: "Email is invalid",
  [VIOLATION.EMAIL_SIZE]: "Email must be between 3 and 70 characters",

  [VIOLATION.PASSWORD_BLANK]: "Password can't be empty",
  [VIOLATION.PASSWORD_SIZE]: "Password must be between 3 and 30 characters",
};