package com.RegistrationAndMailingApi.Registration.Service;

import com.RegistrationAndMailingApi.AppUser.Model.AppUser;
import com.RegistrationAndMailingApi.AppUser.Model.AppUserRole;
import com.RegistrationAndMailingApi.AppUser.Service.AppUserService;
import com.RegistrationAndMailingApi.Registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;

    public String register(RegistrationRequest request){

        appUserService.signUpUser(new AppUser(request.firstName(), request.lastName(),request.email(),request.password(), AppUserRole.USER));


        return "registered";
    }


}
