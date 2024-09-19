import React, {useEffect, useState} from "react";
import {ContentService} from "@/services/content-service.ts";
import {TopicDto} from "@/models/content/TopicDto.ts";
import {ChatBubbleIcon} from "@radix-ui/react-icons";
import {Bars3BottomRightIcon, EyeIcon, HandThumbDownIcon, HandThumbUpIcon} from "@heroicons/react/24/outline";
import {Label} from "@/components/ui/label.tsx";
import {Link} from "react-router-dom";
import {ReplyDto} from "@/models/content/ReplyDto.ts";
import {Share} from "lucide-react";
import {Popover, PopoverContent, PopoverTrigger} from "@radix-ui/react-popover";
import {Card, CardContent, CardHeader} from "@/components/ui/card.tsx";
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar";

const HomePage = () => {
    const [trendingTopics, setTrendingTopics] = useState<TopicDto[]>([]);
    const [latestReplies, setLatestReplies] = useState<ReplyDto[]>([]);

    useEffect(() => {
        ContentService.getTrendingTopics(0, 20).then((response) => {
            setTrendingTopics(response.data);
        }, (error) => {
            console.error(error);
        });

        ContentService.getLatestReplies(0, 10).then((response) => {
            setLatestReplies(response.data);
        }, (error) => {
            console.error(error);
        });
    }, []);

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
                {latestReplies.map((reply) => (
                    <div className={'mb-8 flex flex-col flex-grow width-full shadow-md p-5 border rounded-xl'} key={reply.id}>

                        <Label className="mb-5 cursor-pointer font-bold text-lg">
                            <Link to={`/topic/${reply.topic.slug}`}>
                                {reply.topic.name}
                            </Link>
                        </Label>


                        <Label className={'mb-3'}>
                            {reply.content}
                        </Label>

                        <div className={'flex flex-row justify-between'}>
                            <div className={'flex flex-col'}>
                                <div className={'flex flex-row'}>
                                    <div className={'flex items-center mr-6'}>
                                        <HandThumbUpIcon className="w-4 h-4 mr-1"/>
                                        <span>{reply.upvoteCount}</span>
                                    </div>
                                    <div className={'flex items-center'}>
                                        <HandThumbDownIcon className="w-4 h-4 mr-1"/>
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
            </div>
        </div>
    );
};

export default HomePage;