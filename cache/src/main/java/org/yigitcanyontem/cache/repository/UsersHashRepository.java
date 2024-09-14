package org.yigitcanyontem.cache.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yigitcanyontem.cache.hash.UsersHash;

import java.util.Optional;

@Repository
public interface UsersHashRepository extends CrudRepository<UsersHash, Integer>{
    Optional<UsersHash> findByEmail(String email);

    void deleteByEmail(String email);
}
