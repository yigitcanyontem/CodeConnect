import {UsersProfileDto} from "@/models/user/UsersProfileDto.ts";
import {UsersDto} from "@/models/auth/UsersDto.ts";

export interface UsersCompleteDto {
    user: UsersDto;
    profile: UsersProfileDto;
}