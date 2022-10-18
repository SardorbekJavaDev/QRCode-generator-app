package com.company.repository;

import com.company.entity.QRCodeEntity;
import com.company.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface QRCodeRepository extends JpaRepository<QRCodeEntity, String> {
    @Transactional
    @Modifying
    @Query("update QRCodeEntity as q set q.status = :status where q.id = :id")
    void updateVisible(@Param("status") Boolean status, @Param("id") String id);
}
