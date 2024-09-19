import { Role } from './Role.ts';

export type UsersDto = {
    id: number;
    username: string;
    email: string;
    password: string;
    role: Role;
    enabled: boolean;
    createdAt: Date;
};