package org.yigitcanyontem.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.clients.shared.dto.GenericResponse;
import org.yigitcanyontem.clients.users.dto.UserFollowDto;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.clients.users.profile.UsersProfileCreateDto;
import org.yigitcanyontem.clients.users.profile.UsersProfileDto;
import org.yigitcanyontem.clients.users.profile.UsersProfileUpdateDto;
import org.yigitcanyontem.user.domain.Users;
import org.yigitcanyontem.user.domain.UsersProfile;
import org.yigitcanyontem.user.repository.UsersProfileRepository;
import org.yigitcanyontem.user.repository.UsersRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersProfileService {
    private final UsersProfileRepository usersProfileRepository;
    private final UsersRepository usersRepository;
    private final ObjectMapper objectMapper;

    public UsersProfileDto getUsersProfileByEmail(String email) {
        return mapDomainToDto(usersProfileRepository.findUsersProfileByUsersIdEmail(email).orElse(null));
    }

    public UsersProfileDto getUsersProfileByUsersId(Integer id) {
        return mapDomainToDto(usersProfileRepository.findUsersProfileByUsersIdId(id).orElse(null));
    }

    public boolean existsByUsersId(Integer id) {
        return usersProfileRepository.existsByUsersIdId(id);
    }

    public UsersProfile getByUsersID(Integer id) {
        return usersProfileRepository.findUsersProfileByUsersIdId(id).orElse(null);
    }

    public UsersProfileDto save(UsersProfileCreateDto createDto, UsersDto user) {
        if (existsByUsersId(user.getId())) {
            throw new IllegalArgumentException("User profile already exists");
        }

        UsersProfile usersProfile = createUsersProfileFromCreateDto(createDto, user.getId());
        usersProfile.setCreatedAt(new Date());
        return mapDomainToDto(usersProfileRepository.saveAndFlush(usersProfile));
    }

    public UsersProfileDto update(UsersProfileUpdateDto updateDto, UsersDto user) {
        UsersProfile existingProfile = getByUsersID(user.getId());

        if (existingProfile == null) {
            throw new IllegalArgumentException("User profile does not exist");
        }

        updateExistingProfile(existingProfile, updateDto);

        return mapDomainToDto(usersProfileRepository.saveAndFlush(existingProfile));
    }

    private UsersProfile createUsersProfileFromCreateDto(UsersProfileCreateDto createDto, Integer userId) {
        UsersProfile usersProfile = new UsersProfile();
        usersProfile.setUsersId(usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User does not exist")
        ));
        usersProfile.setFirstName(createDto.getFirstName());
        usersProfile.setLastName(createDto.getLastName());
        usersProfile.setProfilePictureUrl(createDto.getProfilePictureUrl());
        usersProfile.setBio(createDto.getBio());
        usersProfile.setCity(createDto.getCity());
        usersProfile.setCountry(createDto.getCountry());
        usersProfile.setWebsite(createDto.getWebsite());
        usersProfile.setJobTitle(createDto.getJobTitle());
        usersProfile.setBirthDate(createDto.getBirthDate());
        usersProfile.setLinkedinProfile(createDto.getLinkedinProfile());
        usersProfile.setGithubProfile(createDto.getGithubProfile());
        usersProfile.setMediumProfile(createDto.getMediumProfile());
        return usersProfile;
    }

    private void updateExistingProfile(UsersProfile existingProfile, UsersProfileUpdateDto updateDto) {
        existingProfile.setFirstName(updateDto.getFirstName());
        existingProfile.setLastName(updateDto.getLastName());
        existingProfile.setProfilePictureUrl(updateDto.getProfilePictureUrl());
        existingProfile.setBio(updateDto.getBio());
        existingProfile.setCity(updateDto.getCity());
        existingProfile.setCountry(updateDto.getCountry());
        existingProfile.setWebsite(updateDto.getWebsite());
        existingProfile.setJobTitle(updateDto.getJobTitle());
        existingProfile.setBirthDate(updateDto.getBirthDate());
        existingProfile.setLinkedinProfile(updateDto.getLinkedinProfile());
        existingProfile.setGithubProfile(updateDto.getGithubProfile());
        existingProfile.setMediumProfile(updateDto.getMediumProfile());
    }

    private UsersProfileDto mapDomainToDto(UsersProfile usersProfile) {
        if (usersProfile == null) {
            return null;
        }

        return UsersProfileDto.builder()
                .id(usersProfile.getId())
                .usersId(usersProfile.getUsersId().getId())
                .firstName(usersProfile.getFirstName())
                .lastName(usersProfile.getLastName())
                .profilePictureUrl(usersProfile.getProfilePictureUrl())
                .bannerPictureUrl(usersProfile.getBannerPictureUrl())
                .bio(usersProfile.getBio())
                .city(usersProfile.getCity())
                .country(usersProfile.getCountry())
                .website(usersProfile.getWebsite())
                .jobTitle(usersProfile.getJobTitle())
                .birthDate(usersProfile.getBirthDate())
                .linkedinProfile(usersProfile.getLinkedinProfile())
                .githubProfile(usersProfile.getGithubProfile())
                .mediumProfile(usersProfile.getMediumProfile())
                .followersCount(usersProfile.getFollowersCount())
                .followingCount(usersProfile.getFollowingCount())
                .build();
    }

    public void delete(UsersDto usersProfile) {
        usersProfileRepository.deleteByUsersIdId(usersProfile.getId());
    }

    public void updateFollowCount(UserFollowDto followDto) {
        UsersProfile usersProfile = getByUsersID(followDto.getUserId());
        UsersProfile engagedUsersProfile = getByUsersID(followDto.getEngagedUserId());

        if (followDto.isFollowed()) {
            engagedUsersProfile.setFollowersCount(
                    (engagedUsersProfile.getFollowersCount() != null ? engagedUsersProfile.getFollowersCount() : 0) + 1
            );

            usersProfile.setFollowingCount(
                    (engagedUsersProfile.getFollowingCount() != null ? engagedUsersProfile.getFollowingCount() : 0) + 1
            );
        } else {
            engagedUsersProfile.setFollowersCount(
                    (engagedUsersProfile.getFollowersCount() != null ? engagedUsersProfile.getFollowersCount() : 0) - 1
            );

            usersProfile.setFollowingCount(
                    (engagedUsersProfile.getFollowingCount() != null ? engagedUsersProfile.getFollowingCount() : 0) - 1
            );
        }
    }

    public void saveNewUsersProfile(Users user) {
        usersProfileRepository.save(
                UsersProfile.builder()
                        .usersId(user)
                        .followersCount(0)
                        .followingCount(0)
                        .createdAt(user.getCreatedAt())
                        .bio("Hello, I am a new user")
                        .build()
        );

    }
}
