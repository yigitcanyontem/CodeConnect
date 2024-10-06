import React, {useEffect, useState} from 'react';
import {ReplyDto} from "@/models/content/ReplyDto.ts";
import {Share} from "lucide-react";
import {Popover, PopoverContent, PopoverTrigger} from "@radix-ui/react-popover";
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar";
import {Label} from "@/components/ui/label.tsx";
import {Link} from "react-router-dom";
import {Bars3BottomRightIcon, EyeIcon, HandThumbDownIcon, HandThumbUpIcon} from "@heroicons/react/24/outline";

interface UserRepliesProps {
    replies: ReplyDto[] | null;
}

const UserReplies: React.FC<UserRepliesProps> = ({replies}) => {

    return (
        <div className={"width-full"}>
            {replies?.map((reply) => (
                <div className={'mb-8 flex flex-col flex-grow width-full p-5'} key={reply.id}>

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
    );
};

export default UserReplies;
