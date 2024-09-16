import axios from 'axios';
import {GlobalConstants} from "../utils/GlobalConstants.ts";
import {UserRegisterDTO} from "../models/UserCreateDto.ts";
import {AuthenticationResponse} from "../models/AuthenticationResponse.ts";

export class AuthService {

    static baseUrl: string = GlobalConstants.baseUrl + 'auth';

    static authenticate(request: AuthenticationResponse): Promise<AuthenticationResponse>{
        return axios.post(`${this.baseUrl}/login`, request)
            .then(response => {
                this.setSessionStorage(response.data);
                return response.data;
            })
            .catch(error => {
                console.error('Login failed:', error);
                throw error;
            });
    }

    static register(request: UserRegisterDTO): Promise<AuthenticationResponse> {
        return axios.post(`${this.baseUrl}/register`, request)
            .then(response => {
                this.setSessionStorage(response.data);
                return response.data;
            })
            .catch(error => {
                console.error('Registration failed:', error);
                throw error;
            });
    }

    static logout() {
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('refreshToken');
        sessionStorage.removeItem('email');
        sessionStorage.removeItem('username');
    }

    static setSessionStorage(authenticationResponse: AuthenticationResponse) {
        sessionStorage.setItem('token', 'Basic ' + authenticationResponse.accessToken);
        sessionStorage.setItem('email', authenticationResponse.user.email);
        sessionStorage.setItem('username', authenticationResponse.user.username);

        const refreshToken = authenticationResponse.refreshToken;
        document.cookie = `refreshToken=${encodeURIComponent(refreshToken)}; path=/api/refresh-token; Secure; HttpOnly; SameSite=Lax`;
    }
}
