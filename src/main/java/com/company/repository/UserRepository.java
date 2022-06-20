package com.company.repository;

import com.company.entity.UserEntity;
import com.company.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmailAndVisibleTrue(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByEmailAndPassword(String email, String pswd);

    @Transactional
    @Modifying
    @Query("update UserEntity as u set u.status = :status where u.id = :userId")
    void updateStatus(@Param("visible") UserStatus status, @Param("userId") String userId);
}
