package com.company.repository;

import com.company.entity.QRCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QRCodeRepository extends JpaRepository<QRCodeEntity, String> {

}
