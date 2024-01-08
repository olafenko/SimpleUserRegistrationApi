package com.RegistrationAndMailingApi.Registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    public ConfirmationToken saveToken(ConfirmationToken token){
        return confirmationTokenRepository.save(token);
    }
    public Optional<ConfirmationToken>  findToken(String token){

        return confirmationTokenRepository.findByToken(token);
    }

    public int setTokenConfirmed (String token){
        return confirmationTokenRepository.updateTokenConfirmed(token,LocalDateTime.now());
    }






}
