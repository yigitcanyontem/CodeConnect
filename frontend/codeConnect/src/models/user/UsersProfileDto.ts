export interface UsersProfileDto {
    id: number;
    usersId: number;
    firstName: string;
    lastName: string;
    profilePictureUrl: string;
    bio: string;
    city: string;
    country: string;
    website: string;
    jobTitle: string;
    birthDate: Date;
    createdAt: Date;
    linkedinProfile: string;
    githubProfile: string;
    mediumProfile: string;
}
