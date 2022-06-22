package com.company.service;

import com.company.dto.RegistrationDTO;
import com.company.dto.UserJWTDTO;
import com.company.dto.request.AuthRequestDTO;
import com.company.dto.response.UserResponseDTO;
import com.company.entity.UserEntity;
import com.company.enums.UserRole;
import com.company.enums.UserStatus;
import com.company.exception.AppBadRequestException;
import com.company.exception.AppForbiddenException;
import com.company.exception.EmailAlreadyExistsException;
import com.company.exception.PasswordOrEmailWrongException;
import com.company.repository.UserRepository;
import com.company.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${user.verification.url}")
    private String verificationUrl;

    private final UserRepository userRepository;
    private final AttachService attachService;

    public UserResponseDTO login(AuthRequestDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPassword());

        Optional<UserEntity> optional =
                userRepository.findByEmailAndPassword(dto.getEmail(), pswd);
        if (optional.isEmpty()) {
            log.warn("login: Password or email wrong!");
            throw new PasswordOrEmailWrongException("Password or email wrong!");
        }

        UserEntity entity = optional.get();
        if (!entity.getStatus().equals(UserStatus.ACTIVE)) {
            log.warn("Blocked User try Login in System !");
            throw new AppForbiddenException("Not Access !");
        }

        UserResponseDTO profile = new UserResponseDTO(); // TODO: 20.06.2022 I
        profile.setName(entity.getName());
        profile.setSurname(entity.getSurname());
        profile.setPhone(entity.getPhone());
        profile.setEmail(entity.getEmail());
        profile.setPassword(entity.getPassword());
        profile.setCompanyName(entity.getCompanyName());
        profile.setCountry(entity.getCountry());
        profile.setStreet(entity.getStreet());
        profile.setWebsite(entity.getWebsite());
        profile.setZipCode(entity.getZipCode());
        profile.setJwt(JwtUtil.encode(entity.getEmail()));
        return profile;
    }

    public String registration(RegistrationDTO dto) {
        Optional<UserEntity> optional = userRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new EmailAlreadyExistsException("Email Already Exits");
        }
        UserEntity entity = new UserEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());

        String pswd = DigestUtils.md5Hex(dto.getPassword());
        entity.setPassword(pswd);

        entity.setRole(UserRole.USER);
        entity.setStatus(UserStatus.ACTIVE);
        userRepository.save(entity);

        StringBuilder builder = new StringBuilder();
        String jwt = JwtUtil.encode(entity.getId());
        builder.append(verificationUrl).append(jwt);
        return "Activate Your Registration " + builder;  // we used activation via 'response'
    }


    public void verification(String jwt) {
        UserJWTDTO user = null;
        try {
            user = JwtUtil.decode(jwt);
        } catch (JwtException e) {
            throw new AppBadRequestException("Verification not completed");
        }

        userRepository.updateStatus(UserStatus.ACTIVE, user.getEmail());
    }
}
