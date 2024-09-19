export interface TopicCreateDto {
    id: number;
    parentTopicId: number;
    name: string;
    description: string;
    tags: Set<number>;
}