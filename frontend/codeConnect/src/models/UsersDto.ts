import { Role } from './Role';

export type UsersDto = {
    id: number;
    username: string;
    email: string;
    password: string;
    role: Role;
    enabled: boolean;
    createdAt: Date;
};