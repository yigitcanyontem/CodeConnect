export interface ReplyCreateDto {
    topicId: number;
    parentReplyId: number | null;content: string;
}
