import {VoteType} from "@/models/content/VoteType.ts";

export interface ReplyVoteCreateDto {
    replyId: number;
    voteType: VoteType;
}
