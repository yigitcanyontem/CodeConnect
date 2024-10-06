import React, {useEffect, useState} from "react";
import {ContentService} from "@/services/content-service.ts";
import {TopicDto} from "@/models/content/TopicDto.ts";
import {ChatBubbleIcon} from "@radix-ui/react-icons";
import {Bars3BottomRightIcon, EyeIcon, HandThumbDownIcon, HandThumbUpIcon} from "@heroicons/react/24/outline";
import {Label} from "@/components/ui/label.tsx";
import {Link} from "react-router-dom";
import {ReplyDto} from "@/models/content/ReplyDto.ts";
import UserReplies from "@/shared/components/UserReplies.tsx";

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
                <UserReplies replies={latestReplies}/>
            </div>
        </div>
    );
};

export default HomePage;