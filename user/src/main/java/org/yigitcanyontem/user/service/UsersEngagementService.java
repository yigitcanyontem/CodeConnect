package org.yigitcanyontem.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yigitcanyontem.amqp.RabbitMQMessageProducer;
import org.yigitcanyontem.clients.auth.AuthClient;
import org.yigitcanyontem.clients.notification.NotificationCreateDto;
import org.yigitcanyontem.clients.shared.dto.GenericRabbitMQMessage;
import org.yigitcanyontem.clients.users.dto.UserFollowDto;
import org.yigitcanyontem.clients.users.dto.UserRegisterDTO;
import org.yigitcanyontem.clients.users.dto.UsersCompleteDto;
import org.yigitcanyontem.clients.users.dto.UsersDto;
import org.yigitcanyontem.clients.users.enums.UserEngagementType;
import org.yigitcanyontem.clients.users.profile.UsersProfileDto;
import org.yigitcanyontem.user.domain.Users;
import org.yigitcanyontem.user.domain.UsersEngagement;
import org.yigitcanyontem.user.domain.UsersProfile;
import org.yigitcanyontem.user.repository.UsersEngagementRepository;
import org.yigitcanyontem.user.repository.UsersProfileRepository;
import org.yigitcanyontem.user.repository.UsersRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsersEngagementService {
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    private final UsersEngagementRepository usersEngagementRepository;

    public long getUserFollowerCount(Integer userId) {
        return usersEngagementRepository.countUsersEngagementsByEngagedUserIdAndUserEngagementType(userId, UserEngagementType.FOLLOW);
    }

    public long getUserFollowingCount(Integer userId) {
        return usersEngagementRepository.countUsersEngagementsByUserIdAndUserEngagementType(userId, UserEngagementType.FOLLOW);
    }

    public List<UsersProfileDto> getUserFollowers(Integer userId) {
        List<UsersEngagement> usersEngagements = usersEngagementRepository.getUsersEngagementsByEngagedUserIdAndUserEngagementType(userId, UserEngagementType.FOLLOW);
        //TODO: Implement this method
        return null;
    }

    public List<UsersProfileDto> getUserFollowing(Integer userId) {
        List<UsersEngagement> usersEngagements = usersEngagementRepository.getUsersEngagementsByUserIdAndUserEngagementType(userId, UserEngagementType.FOLLOW);
        //TODO: Implement this method
        return null;
    }

    public void followUser(Integer userId, Integer engagedUserId) {
        UsersEngagement usersEngagement = UsersEngagement.builder()
                .userId(userId)
                .engagedUserId(engagedUserId)
                .userEngagementType(UserEngagementType.FOLLOW)
                .build();
        usersEngagementRepository.save(usersEngagement);
        rabbitMQMessageProducer.publish(
                new GenericRabbitMQMessage("api/v1/user-profile/update-following", new UserFollowDto(userId, engagedUserId, true)),
                "internal.exchange",
                "internal.user.routing-key"
        );
    }

    public void unfollowUser(Integer userId, Integer engagedUserId) {
        usersEngagementRepository.deleteUsersEngagementByUserIdAndEngagedUserIdAndUserEngagementType(userId, engagedUserId, UserEngagementType.FOLLOW);
        rabbitMQMessageProducer.publish(
                new GenericRabbitMQMessage("api/v1/user-profile/update-following", new UserFollowDto(userId, engagedUserId, false)),
                "internal.exchange",
                "internal.user.routing-key"
        );
    }

    public boolean isUserFollowing(Integer userId, Integer engagedUserId) {
        return usersEngagementRepository.existsUsersEngagementByUserIdAndEngagedUserIdAndUserEngagementType(userId, engagedUserId, UserEngagementType.FOLLOW);
    }

    public boolean isUserFollowed(Integer userId, Integer engagedUserId) {
        return usersEngagementRepository.existsUsersEngagementByUserIdAndEngagedUserIdAndUserEngagementType(engagedUserId, userId, UserEngagementType.FOLLOW);
    }

}
