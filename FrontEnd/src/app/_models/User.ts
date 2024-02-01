export interface User {
  email: string,
  password: string,
  fullName?: string,
  imageUrl?: string,
  isLoggedIn: boolean
}
