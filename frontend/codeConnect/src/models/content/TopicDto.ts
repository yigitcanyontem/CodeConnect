import {TagDto} from "@/models/content/TagDto.ts";

export interface TopicDto {
    id: number;
    parentTopicId: number;
    name: string;
    description: string;
    createdByUserId: number;
    createdByUsername: string;
    updatedByUserId: number;
    updatedAt: Date;
    updatedByUsername: string;
    createdAt: Date;
    viewCountTotal: number;
    viewCountLastWeek: number;
    slug: string;
    tags: Set<TagDto>;
    replyCount: number;
}
