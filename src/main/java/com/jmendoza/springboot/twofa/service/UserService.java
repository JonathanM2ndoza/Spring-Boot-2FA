package com.jmendoza.springboot.twofa.service;

import com.jmendoza.springboot.twofa.exception.MissingTOTPKeyAuthenticatorException;
import com.jmendoza.springboot.twofa.model.User;
import com.jmendoza.springboot.twofa.repository.UserRepository;
import com.jmendoza.springboot.twofa.security.TOTPAuthenticator;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TOTPAuthenticator totpAuthenticator;

    @Autowired
    private Environment env;

    @Bean
    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    public User createUser(User user) {
        user.setPassword(encoder().encode(user.getPassword()));
        user.setSecret(totpAuthenticator.generateSecret());
        return userRepository.save(user);
    }

    public String generateOTPProtocol(String userName) {
        User user = userRepository.findById(userName).get();
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", userName, userName + "@domain.com", user.getSecret(),env.getRequiredProperty("2fa.application.name"));
    }

    public String generateQRCode(String otpProtocol) throws Throwable {
        return totpAuthenticator.generateQRCode(otpProtocol);
    }

    public boolean validateTotp(String userName, Integer totpKey) {
        User user = userRepository.getOne(userName);
        String secret = user.getSecret();
        if (StringUtils.hasText(secret)) {
            if (totpKey != null) {
                try {
                    if (!totpAuthenticator.verifyCode(secret, totpKey, Integer.parseInt(env.getRequiredProperty("2fa.application.time")))) {
                        System.out.printf("Code %d was not valid", totpKey);
                        throw new BadCredentialsException(
                                "Invalid TOTP code");
                    }
                } catch (InvalidKeyException | NoSuchAlgorithmException e) {
                    throw new InternalAuthenticationServiceException(
                            "TOTP code verification failed", e);
                }
            } else {
                throw new MissingTOTPKeyAuthenticatorException(
                        "TOTP code is mandatory");
            }
        }
        return true;
    }
}
