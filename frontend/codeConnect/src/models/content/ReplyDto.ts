import {TopicDto} from "@/models/content/TopicDto.ts";

export interface ReplyDto {
    id: number;
    topicId: number;
    topic: TopicDto;
    createdByUserId: number;
    createdByUsername: string;
    createdAt: Date;
    updatedAt: Date;
    parentReplyId: number;
    content: string;
    upvoteCount: number;
    downvoteCount: number;
    childReplyCount: number;
}
