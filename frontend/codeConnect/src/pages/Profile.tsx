import React, {useEffect, useState} from 'react';
import {UsersCompleteDto} from "@/models/user/UsersCompleteDto.ts";
import {UserService} from "@/services/user-service.ts";
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import {Card} from "@/components/ui/card.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Badge} from "@/components/ui/badge.tsx";
import {GithubLogo, LinkedinLogo, MediumLogo} from "@phosphor-icons/react";
import UserReplies from "@/shared/components/UserReplies.tsx";
import {ReplyDto} from "@/models/content/ReplyDto.ts";
import {ContentService} from "@/services/content-service.ts";
import Paginator from "@/shared/components/Paginator.tsx";

const Profile = () => {
    const [user, setUser] = useState<UsersCompleteDto | null>(null);
    const [replies, setReplies] = useState<ReplyDto[] | null>(null);
    const [page, setPage] = useState<number>(0);
    const [pageCount, setPageCount] = useState<number>(0);

    useEffect(() => {
        UserService.getLoggedInUser().then(
            (response) => {
                setUser(response);
            },
            (error) => {
                console.error(error);
            }
        );
    }, []);


    useEffect(() => {
        getReplies();
    }, [page]);

    const getReplies = function (){
        ContentService.getCurrentUserReplies(page, 5).then(
            (response) => {
                setReplies(response.data);
                setPageCount(response.totalPages);
            },
            (error) => {
                console.error(error);
            }
        );
    }

    if (!user) return <div>Loading...</div>;

    const {profile, user: userInfo} = user;
    const {username, email, role, enabled} = userInfo;
    const {
        firstName,
        lastName,
        profilePictureUrl,
        bannerPictureUrl,
        bio,
        city,
        country,
        website,
        jobTitle,
        birthDate,
        linkedinProfile,
        githubProfile,
        mediumProfile,
        followersCount,
        followingCount
    } = profile;

    return (
        <div className="common_container mt-6 mb-6 flex-col gap-14">
            <Card className="shadow-lg  width-full rounded-2xl">
                <div style={{height: '250px', position: 'relative'}}>
                    <img src={bannerPictureUrl} alt="banner" className="w-full h-full object-cover rounded-t-2xl"/>
                    <div style={{position: "absolute", top: "200px", left: "50px"}}>
                        <Avatar className={"border-2"}
                                style={{height: "100px", width: "100px", objectFit: "contain"}}>
                            <AvatarImage src={profilePictureUrl} alt="profile pic"/>
                            <AvatarFallback>{firstName.substring(0, 1)}{lastName.substring(0, 1)}</AvatarFallback>
                        </Avatar>
                    </div>
                </div>

                <div style={{padding: "20px 50px 0 50px"}}>
                    <div className="flex space-x-4 justify-end">
                        {linkedinProfile && <a href={linkedinProfile} target={"_blank"} rel={"noreferrer"} className="text-gray-600"><LinkedinLogo size={32} /></a>}
                        {githubProfile && <a href={githubProfile} target={"_blank"} rel={"noreferrer"} className="text-gray-600"><GithubLogo size={32} /></a>}
                        {mediumProfile && <a href={mediumProfile} target={"_blank"} rel={"noreferrer"} className="text-gray-600"><MediumLogo size={32} /></a>}
                    </div>
                </div>

                <div style={{minHeight: '250px', padding: "20px 50px"}}>
                    <h2 className="text-2xl font-bold">{firstName} {lastName}</h2>
                    <p className="text-gray-300 mt-1">{jobTitle} | {city}, {country}</p>
                    <p className="text-gray-400 mt-1">{bio}</p>
                    <div className="flex flex-row mt-4 gap-4">
                        <h2 className="text-gray-100">Follower: {followersCount}</h2>
                        <h2 className="text-gray-100">Following: {followingCount}</h2>
                    </div>

                    <div className="flex flex-row mt-6 gap-4 pb-4">
                        <Button>Edit Profile</Button>
                    </div>
                </div>
            </Card>

            <Card className="shadow-lg  width-full rounded-2xl pt-8 pb-8" style={{paddingLeft: "50px", paddingRight: "50px"}}>
                <h2 className="text-2xl font-bold mb-6">Replies</h2>
                <UserReplies replies={replies} />

                <Paginator
                    currentPage={page + 1}
                    totalPages={pageCount}
                    onPageChange={(pageNumber) => setPage(pageNumber - 1)}
                    showPreviousNext={true}
                />
            </Card>
        </div>
    );
};

export default Profile;
