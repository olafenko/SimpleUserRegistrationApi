package com.RegistrationAndMailingApi.Registration.Service;

import com.RegistrationAndMailingApi.AppUser.Model.AppUser;
import com.RegistrationAndMailingApi.AppUser.Model.AppUserRole;
import com.RegistrationAndMailingApi.AppUser.Service.AppUserService;
import com.RegistrationAndMailingApi.Mailing.EmailSender;
import com.RegistrationAndMailingApi.Mailing.EmailService;
import com.RegistrationAndMailingApi.Registration.RegistrationRequest;
import com.RegistrationAndMailingApi.Registration.Service.Validator.*;
import com.RegistrationAndMailingApi.Registration.token.ConfirmationToken;
import com.RegistrationAndMailingApi.Registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final EmailService emailService;


    public ResponseEntity register(RegistrationRequest request){

        boolean emailValidate = createEmailValidator().validate(request.email());

        if (!emailValidate){
            return ResponseEntity.badRequest().body("Incorrect email.");
        }

        ResponseEntity token = appUserService.signUpUser(new AppUser(request.firstName(), request.lastName(), request.email(), request.password(), AppUserRole.USER));

        if (!token.getStatusCode().isSameCodeAs(HttpStatus.OK)){
            return token;
        }

        String link ="http://localhost:8080/api/v1/registration/confirm?token=" + token.getBody();
        emailSender.send(request.email(),emailService.buildEmail(request.firstName(),link));


        return ResponseEntity.ok("User registered successfully!\nConfirm your email to get access to login.");
    }

    @Transactional
    public ResponseEntity confirmToken(String token){

        ConfirmationToken confirmationToken = confirmationTokenService.findToken(token).orElseThrow(()-> new IllegalStateException("Incorrect token"));

        if (confirmationToken.getConfirmedAt() !=null){
            return ResponseEntity.ok("Token already confirmed.");
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Confirmation token expired.");
        }

        confirmationTokenService.setTokenConfirmed(token);
        appUserService.setUserEnabled(confirmationToken.getAppUser().getEmail());

        return ResponseEntity.ok("Token confirmed!");
    }


    private EmailValidator createEmailValidator(){
        EmailValidator validator = new EmailValidator(List.of(new MinLengthRule(), new MaxLengthRule(),new SpecialCharsRule()));
        return validator;
    }




}
