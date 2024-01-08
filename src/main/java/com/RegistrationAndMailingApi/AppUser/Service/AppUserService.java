package com.RegistrationAndMailingApi.AppUser.Service;

import com.RegistrationAndMailingApi.AppUser.Model.AppUser;
import com.RegistrationAndMailingApi.AppUser.Repo.AppUserRepository;
import com.RegistrationAndMailingApi.Registration.token.ConfirmationToken;
import com.RegistrationAndMailingApi.Registration.token.ConfirmationTokenRepository;
import com.RegistrationAndMailingApi.Registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final String APP_USER_NOT_FOUND_MESSAGE = "User with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(APP_USER_NOT_FOUND_MESSAGE,email)));
    }

    public ResponseEntity signUpUser(AppUser appUser){

        if (appUserRepository.findByEmail(appUser.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already taken!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(),LocalDateTime.now().plusMinutes(15),appUser);
        confirmationTokenService.saveToken(confirmationToken);


        return ResponseEntity.ok(token);

    }

    public int setUserEnabled(String email){
       return appUserRepository.enableAppUser(email);
    }


}
