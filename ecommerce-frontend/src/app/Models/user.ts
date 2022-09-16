import { Role } from "./role";

export interface User {
    userId: number;
    firstName: string;
    lastName: string;
    email: string;
    userName: string;
    contact: string;
    password: string;
    roles: Role[];
    ssn: string;
}