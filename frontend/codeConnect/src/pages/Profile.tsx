import React, { useEffect, useState } from 'react';
import { UsersCompleteDto } from "@/models/user/UsersCompleteDto.ts";
import { UserService } from "@/services/user-service.ts";
import {Avatar, AvatarFallback, AvatarImage} from "@/components/ui/avatar.tsx";
import {Card} from "@/components/ui/card.tsx";
import {Button} from "@/components/ui/button.tsx";
import {Badge} from "@/components/ui/badge.tsx";

const Profile = () => {
    const [user, setUser] = useState<UsersCompleteDto | null>(null);

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

    if (!user) return <div>Loading...</div>;

    const { profile, user: userInfo } = user;
    const { username, email, role, enabled } = userInfo;
    const {
        firstName,
        lastName,
        profilePictureUrl,
        bio,
        city,
        country,
        website,
        jobTitle,
        birthDate,
        linkedinProfile,
        githubProfile,
        mediumProfile,
    } = profile;

    return (
        <div className="common_container p-8">
            <Card className="shadow-lg p-6">
                <div className="flex items-center">
                    <Avatar>
                        <AvatarImage src={profilePictureUrl} alt="profile pic"/>
                        <AvatarFallback>{firstName.substring(0, 1)}{lastName.substring(0,1)}</AvatarFallback>
                    </Avatar>
                    <div>
                        <h2 className="text-2xl font-bold">{firstName} {lastName}</h2>
                        <p className="text-gray-600">{jobTitle} | {city}, {country}</p>
                        <p className="text-gray-500">{bio}</p>
                    </div>
                </div>

                <div className="mt-6">
                    <h3 className="text-xl font-semibold">Contact Information</h3>
                    <p className="text-gray-700">Email: {email}</p>
                    {website && <p className="text-gray-700">Website: <a href={website} className="text-blue-600">{website}</a></p>}
                </div>

                <div className="mt-6">
                    <h3 className="text-xl font-semibold">Social Profiles</h3>
                    <div className="flex space-x-4">
                        {linkedinProfile && <a href={linkedinProfile} className="text-blue-600">LinkedIn</a>}
                        {githubProfile && <a href={githubProfile} className="text-gray-600">GitHub</a>}
                        {mediumProfile && <a href={mediumProfile} className="text-gray-600">Medium</a>}
                    </div>
                </div>

                <div className="mt-6">
                    <h3 className="text-xl font-semibold">Account Information</h3>
                    <p>Username: {username}</p>
                    <p>Role: <Badge>{role}</Badge></p>
                    <p>Status: {enabled ? <Badge className="bg-green-500">Enabled</Badge> : <Badge className="bg-red-500">Disabled</Badge>}</p>
                </div>

                <div className="mt-6">
                    <Button>Edit Profile</Button>
                </div>
            </Card>
        </div>
    );
};

export default Profile;
