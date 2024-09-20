import React, {useEffect, useState} from 'react';
import {TopicDto} from "@/models/content/TopicDto.ts";
import {ReplyDto} from "@/models/content/ReplyDto.ts";
import {ContentService} from "@/services/content-service.ts";
import {Link, useParams} from 'react-router-dom';
import {Label} from "@/components/ui/label.tsx";
import {ChatBubbleIcon} from "@radix-ui/react-icons";
import {Bars3BottomRightIcon, EyeIcon, HandThumbDownIcon, HandThumbUpIcon} from "@heroicons/react/24/outline";
import {Share} from "lucide-react";
import {Popover, PopoverContent, PopoverTrigger} from "@radix-ui/react-popover";
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Textarea} from "@/components/ui/textarea.tsx";
import {Card, CardContent, CardHeader} from "@/components/ui/card.tsx";
import {ReplyCreateDto} from "@/models/content/ReplyCreateDto.ts";
import {useToast} from "@/hooks/use-toast.ts";
import {ReplyVoteCreateDto} from "@/models/content/ReplyVoteCreateDto.ts";
import {VoteType} from "@/models/content/VoteType.ts";

const TopicPage = () => {
    const [topic, setTopic] = useState<TopicDto>();
    const [reply, setReply] = useState<string>();
    const [replies, setReplies] = useState<ReplyDto[]>([]);
    let {slug} = useParams();
    const [trendingTopics, setTrendingTopics] = useState<TopicDto[]>([]);
    const { toast } = useToast()
    const [error, setError] = useState<string | null>(null);
    const [response, setResponse] = useState<ReplyDto | null>(null);

    const voteReply = (replyId: number, type: string) => async () => {
        try {
            const request: ReplyVoteCreateDto = {
                replyId: replyId,
                voteType: type === 'upvote' ? VoteType.UPVOTE : VoteType.DOWNVOTE
            };

            const result = await ContentService.createReplyVote(request).then((response) => {
                //FIXME: This is not working properly
                setReplies((prevReplies) =>
                    prevReplies.map((reply) => {
                        if (reply.id === replyId) {
                            if (type === 'upvote') {
                                return {
                                    ...reply,
                                    upvoteCount: reply.upvoteCount + 1,
                                };
                            } else {
                                return {
                                    ...reply,
                                    downvoteCount: reply.downvoteCount + 1,
                                };
                            }
                        }

                        if (response.data == "deleted") {
                            if (type === 'upvote') {
                                return {
                                    ...reply,
                                    upvoteCount: reply.upvoteCount - 1,
                                };
                            }else {
                                return {
                                    ...reply,
                                    downvoteCount: reply.downvoteCount - 1,
                                };
                            }
                        }else if (response.data == "upvoted") {
                            return {
                                ...reply,
                                upvoteCount: reply.upvoteCount + 1,
                            }
                        }else if (response.data == "downvoted") {
                            return {
                                ...reply,
                                downvoteCount: reply.downvoteCount + 1,
                            }
                        }
                        return reply;
                    })
                );
            });
            toast({
                title: request.voteType === VoteType.UPVOTE ? "Upvoted" : "Downvoted"
            })
            setError(null);
        } catch (err) {
            setError("Upvote failed. Please try again.");
            setResponse(null);
        }
    }


    const addReply = async (e: React.FormEvent) => {
        e.preventDefault();

        const request: ReplyCreateDto = {
            content: reply!,
            parentReplyId: null,
            topicId: topic!.id
        };

        try {
            const result = await ContentService.createReply(request);
            toast({
                title: "Reply added"
            })
            setResponse(result);
            setReply('');
            setReplies([...replies, result]);
            setError(null);
        } catch (err) {
            toast({
                title: "Error while adding reply",
            })
            setError("Reply failed. Please try again.");
            setResponse(null);
        }
    };

    useEffect(() => {
        ContentService.getTrendingTopics(0, 20).then((response) => {
            setTrendingTopics(response.data);
        }, (error) => {
            console.error(error);
        });
    }, []);


    useEffect(() => {
        ContentService.getTopicBySlug(slug).then((response) => {
            setTopic(response);
            ContentService.getRepliesByTopicId(response.id).then((response) => {
                setReplies(response.data);
            }, (error) => {
                console.error(error);
            });
        }, (error) => {
            console.error(error);
        });
    }, [slug]);

    return (
        <div className={'common_container'}>
            <div style={{flexBasis: '25%', display: 'flex', flexDirection: 'column', alignSelf: 'start'}}>
                <h1 className="text-2xl font-bold mb-2 mt-8">Trending</h1>
                <div style={{overflowY: 'auto', height: '80vh'}} className={'custom_scrollbar pr-8'}>
                    {trendingTopics.map((topic) => (
                        <div className="p-3 rounded-lg shadow-md border mb-3" key={topic.id}>
                            <Link to={`/topic/${topic.slug}`}>
                                <Label className="cursor-pointer font-bold">{topic.name}</Label>
                            </Link>

                            <div className="flex items-center justify-end mt-2 text-gray-600">
                                <div className="flex items-center mr-4">
                                    <ChatBubbleIcon className="w-4 h-4 mr-1"/>
                                    <span>{topic.replyCount}</span>
                                </div>

                                <div className="flex items-center">
                                    <EyeIcon className="w-4 h-4 mr-1"/>
                                    <span>{topic.viewCountLastWeek}</span>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </div>

            <div style={{
                flexBasis: '75%',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'start',
                alignSelf: 'flex-start'
            }} className={'pl-6 mt-8'}>
                <Label className="text-2xl font-bold mb-5">{topic?.name}</Label>
                {replies.map((reply) => (
                    <div className={'mb-8 flex flex-col flex-grow width-full shadow-md p-5 border rounded-xl'}
                         key={reply.id}>

                        <Label className={'mb-3'}>
                            {reply.content}
                        </Label>

                        <div className={'flex flex-row justify-between'}>
                            <div className={'flex flex-col'}>
                                <div className={'flex flex-row'}>
                                    <div className={'flex items-center mr-6'}>
                                        <HandThumbUpIcon onClick={voteReply(reply.id, 'upvote')} className="w-4 h-4 mr-1 cursor-pointer"/>
                                        <span>{reply.upvoteCount}</span>
                                    </div>
                                    <div className={'flex items-center'}>
                                        <HandThumbDownIcon onClick={voteReply(reply.id, 'downvote')} className="w-4 h-4 mr-1 cursor-pointer"/>
                                        <span>{reply.downvoteCount}</span>
                                    </div>
                                </div>
                            </div>

                            <div className={'flex flex-col'}>
                                <div className={'flex flex-row'}>
                                    <div className={'flex items-center mr-6'}>
                                        <Share className={'w-4 h-4 cursor-pointer'}/>
                                    </div>
                                    <div className={'flex items-center'}>
                                        <Popover>
                                            <PopoverTrigger>
                                                <Bars3BottomRightIcon className={'w-4 h-4 cursor-pointer'}/>
                                            </PopoverTrigger>
                                            <PopoverContent>
                                                <div className={'mt-2 border rounded-xl pt-2 pb-2 pr-3 pl-3'}>
                                                    <Label className={'p-0 text-xs'}>Report</Label>
                                                </div>
                                            </PopoverContent>
                                        </Popover>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div className={'flex flex-row justify-end mt-2'}>
                            <div className={'flex flex-col items-end self-center mr-5'}>
                                <div className={'flex items-center'}>
                                    <Label className={'text-xs'}>{reply.createdByUsername}</Label>
                                </div>
                                <div className={'flex items-center'}>
                                    <Label className={'text-xs'}>{new Date(reply.createdAt).toLocaleString('tr-TR', {
                                        day: '2-digit',
                                        month: '2-digit',
                                        year: 'numeric',
                                        hour: '2-digit',
                                        minute: '2-digit'
                                    })}</Label>
                                </div>
                            </div>

                            <div className={'flex flex-col items-center'}>
                                <Avatar>
                                    <AvatarImage src={''} alt="profile pic"/>
                                    <AvatarFallback>{reply.createdByUsername.substring(0, 1)}</AvatarFallback>
                                </Avatar>
                            </div>
                        </div>

                    </div>
                ))}

                <div className={'flex flex-col width-full mb-8'}>
                    <Card>
                        <CardHeader className={'pb-2'}>
                            <Label className={'ms-2 text-lg font-bold'}>Reply</Label>
                        </CardHeader>
                        <CardContent className={'flex flex-col'}>
                            <form onSubmit={addReply} className="space-y-4">
                                <div>
                                    <Textarea
                                        id="reply"
                                        value={reply}
                                        onChange={(e) => setReply(e.target.value)}
                                        required
                                        placeholder={'Share your thought on the topic...'}
                                              className={'border p-3 rounded-xl w-full h-40'}/>
                                </div>
                                {error && <p className="text-red-500">{error}</p>}
                                <Button type="submit" className={'mt-5 w-fit'}>Add Reply</Button>
                            </form>
                        </CardContent>
                    </Card>
                </div>
            </div>
        </div>
    )
        ;
};

export default TopicPage;
