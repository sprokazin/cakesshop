package com.sprokazin.cakesShop.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsUserEntityByNumber(String number);

    UserEntity findUserEntityByNumber(String number);
}
