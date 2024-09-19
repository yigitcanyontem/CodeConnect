import {UsersDto} from "./UsersDto.ts";

export type AuthenticationResponse = {
    accessToken: string;
    refreshToken: string;
    id: number;
    user: UsersDto;
};