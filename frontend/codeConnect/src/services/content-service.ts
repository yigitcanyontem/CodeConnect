import axios from 'axios';
import { GlobalConstants } from "../utils/GlobalConstants.ts";
import {PaginatedResponse} from "@/models/shared/PaginatedResponse.ts";
import {TopicDto} from "@/models/content/TopicDto.ts";
import {TopicCreateDto} from "@/models/content/TopicCreateDto.ts";
import {ReplyDto} from "@/models/content/ReplyDto.ts";
import {ReplyCreateDto} from "@/models/content/ReplyCreateDto.ts";
import {ReplyVoteCreateDto} from "@/models/content/ReplyVoteCreateDto.ts";
import {GenericResponse} from "@/models/shared/GenericResponse.ts";

export class ContentService {
    static baseUrl: string = GlobalConstants.baseUrl + 'content';
    static token: string | null = sessionStorage.getItem('token');

    static getLatestReplies(page: number = 0, size: number = 10): Promise<PaginatedResponse> {
        return axios.get(`${this.baseUrl}/latest-replies`, { params: { page, size } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting replies:', error);
                throw error;
            });
    }

    static getTrendingTopics(page: number = 0, size: number = 10): Promise<PaginatedResponse> {
        return axios.get(`${this.baseUrl}/topics-trending`, { params: { page, size } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting topics:', error);
                throw error;
            });
    }

    static searchTopics(query: string, page: number = 0, size: number = 10): Promise<PaginatedResponse> {
        return axios.get(`${this.baseUrl}/topics/search`, { params: { query, page, size } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while searching topics:', error);
                throw error;
            });
    }

    static getTopicBySlug(slug: string | undefined): Promise<TopicDto> {
        return axios.get(`${this.baseUrl}/topic/${slug}`)
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting topic:', error);
                throw error;
            });
    }

    static updateTopic(topicUpdateDto: TopicCreateDto): Promise<TopicDto> {
        return axios.put(`${this.baseUrl}/topic`, topicUpdateDto, { headers: { Authorization: this.token } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while updating topic:', error);
                throw error;
            });
    }

    static createTopic(topicCreateDto: TopicCreateDto): Promise<void> {
        return axios.post(`${this.baseUrl}/topic`, topicCreateDto, { headers: { Authorization: this.token } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while creating topic:', error);
                throw error;
            });
    }

    static deleteTopic(id: number): Promise<void> {
        return axios.delete(`${this.baseUrl}/topic/${id}`, { headers: { Authorization: this.token } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while deleting topic:', error);
                throw error;
            });
    }

    static getReplyById(id: number): Promise<ReplyDto> {
        return axios.get(`${this.baseUrl}/reply/${id}`)
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting reply:', error);
                throw error;
            });
    }

    static getRepliesByTopicId(topicId: number, page: number = 0, size: number = 10): Promise<PaginatedResponse> {
        return axios.get(`${this.baseUrl}/replies/topic/${topicId}`, { params: { page, size } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting replies:', error);
                throw error;
            });
    }

    static getRepliesByUserId(userId: number, page: number = 0, size: number = 10): Promise<PaginatedResponse> {
        return axios.get(`${this.baseUrl}/replies/user/${userId}`, { params: { page, size } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting replies:', error);
                throw error;
            });
    }

    static getChildRepliesByReplyId(replyId: number, page: number = 0, size: number = 10): Promise<PaginatedResponse> {
        return axios.get(`${this.baseUrl}/replies/reply/${replyId}`, { params: { page, size } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting replies:', error);
                throw error;
            });
    }

    static getCurrentUserReplies(page: number = 0, size: number = 10): Promise<PaginatedResponse> {
        return axios.get(`${this.baseUrl}/current-user-replies`, { headers: { Authorization: this.token }, params: { page, size } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while getting replies:', error);
                throw error;
            });
    }

    static updateReply(replyDto: ReplyDto): Promise<ReplyDto> {
        return axios.put(`${this.baseUrl}/reply`, replyDto, { headers: { Authorization: this.token } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while updating reply:', error);
                throw error;
            });
    }

    static createReply(createDto: ReplyCreateDto): Promise<ReplyDto> {
        return axios.post(`${this.baseUrl}/reply`, createDto, { headers: { Authorization: this.token } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while creating reply:', error);
                throw error;
            });
    }

    static deleteReply(id: number): Promise<void> {
        return axios.delete(`${this.baseUrl}/reply/${id}`, { headers: { Authorization: this.token } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while deleting reply:', error);
                throw error;
            });
    }

    static createReplyVote(createDto: ReplyVoteCreateDto): Promise<GenericResponse> {
        return axios.post(`${this.baseUrl}/reply-vote`, createDto, { headers: { Authorization: this.token } })
            .then(response => response.data)
            .catch(error => {
                console.error('Error while creating reply vote:', error);
                throw error;
            });
    }
}