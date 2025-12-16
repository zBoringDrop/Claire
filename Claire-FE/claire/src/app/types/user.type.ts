export interface User {
    id: number,
    nickname: string,
    name: string,
    surname: string,
    email: string,
    role: string
}

export interface UserRegistration {
    nickname: string,
    name: string,
    surname: string,
    email: string,
    password: string
}

export interface UserLogin {
    email: string,
    password: string
}