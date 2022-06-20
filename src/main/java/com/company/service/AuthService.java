package com.company.service;

import com.company.dto.RegistrationDTO;
import com.company.dto.request.AuthRequestDTO;
import com.company.dto.response.UserResponseDTO;
import com.company.entity.UserEntity;
import com.company.enums.UserRole;
import com.company.enums.UserStatus;
import com.company.exp.AppBadRequestException;
import com.company.exp.AppForbiddenException;
import com.company.exp.PasswordOrEmailWrongException;
import com.company.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AttachService attachService;
    @Autowired
    private EmailService emailService;

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
        profile.setJwt(JwtUtil.encode(entity.getId(), entity.getRole()));
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
        entity.setStatus(UserStatus.NOT_ACTIVE);
        userRepository.save(entity);

        // >>>>>>>>>>>>>>>>>
        Thread thread = new Thread() {
            @Override
            public void run() {
                sendVerificationEmail(entity);
            }
        };
        thread.start();
        return "Confirm your email address !";
    }

    private void sendVerificationEmail(UserEntity entity) {
        StringBuilder builder = new StringBuilder();
        String jwt = JwtUtil.encode(entity.getId());
        builder.append("To verify your registration click to next link.");
        builder.append("http://localhost:8080/auth/verification/").append(jwt);
        emailService.send(entity.getEmail(), "Activate Your Registration", builder.toString());
    }

    public void verification(String jwt) {
        String userId = null;
        try {
            userId = JwtUtil.decodeAndGetId(jwt);
        } catch (JwtException e) {
            throw new AppBadRequestException("Verification not completed");
        }

        userRepository.updateStatus(UserStatus.ACTIVE, userId);
    }
}
