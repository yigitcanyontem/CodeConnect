import axios from 'axios';
import { GlobalConstants } from "../utils/GlobalConstants.ts";
import { UsersDto } from "@/models/auth/UsersDto.ts";
import {UsersCompleteDto} from "@/models/user/UsersCompleteDto.ts";
import {UsersProfileDto} from "@/models/user/UsersProfileDto.ts";
import {UsersProfileCreateDto} from "@/models/user/UsersProfileCreateDto.ts";
import {UsersProfileUpdateDto} from "@/models/user/UsersProfileUpdateDto.ts";

export class UserService {
    static userBaseUrl: string = GlobalConstants.baseUrl + 'user';
    static userProfilesBaseUrl: string = GlobalConstants.baseUrl + 'user-profile';
    static token: string | null = sessionStorage.getItem('token');

    static getLoggedInUser(): Promise<UsersCompleteDto> {
        return axios.get(`${this.userBaseUrl}/me`, {
            headers: { 'Authorization': this.token }
        })
        .then(response => response.data)
        .catch(error => {
            console.error('Error while fetching logged in user:', error);
            throw error;
        });
    }

    static getUsersByUsername(username: string): Promise<UsersDto> {
        return axios.get(`${this.userBaseUrl}/username/${username}`)
        .then(response => response.data)
        .catch(error => {
            console.error('Error while fetching user profile by username:', error);
            throw error;
        });
    }

    static getUserProfileByUserId(userId: number): Promise<UsersProfileDto> {
        return axios.get(`${this.userProfilesBaseUrl}/user/${userId}`)
        .then(response => response.data)
        .catch(error => {
            console.error('Error while fetching user profile by user id:', error);
            throw error;
        });
    }

    static saveUserProfile(createDto: UsersProfileCreateDto): Promise<UsersProfileDto> {
        return axios.post(this.userProfilesBaseUrl, createDto, {
            headers: { 'Authorization': this.token }
        })
        .then(response => response.data)
        .catch(error => {
            console.error('Error while saving user profile:', error);
            throw error;
        });
    }

    static updateUserProfile(updateDto: UsersProfileUpdateDto): Promise<UsersProfileDto> {
        return axios.put(this.userProfilesBaseUrl, updateDto, {
            headers: { 'Authorization': this.token }
        })
        .then(response => response.data)
        .catch(error => {
            console.error('Error while updating user profile:', error);
            throw error;
        });
    }
}