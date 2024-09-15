package org.yigitcanyontem.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yigitcanyontem.user.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByEmail(String email);

    Users findByUsername(String username);

    boolean existsByEmailOrUsername(String email, String username);
}
