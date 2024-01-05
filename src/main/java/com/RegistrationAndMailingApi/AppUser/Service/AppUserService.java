package com.RegistrationAndMailingApi.AppUser.Service;

import com.RegistrationAndMailingApi.AppUser.Model.AppUser;
import com.RegistrationAndMailingApi.AppUser.Repo.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final String APP_USER_NOT_FOUND_MESSAGE = "User with email %s not found";
;
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(APP_USER_NOT_FOUND_MESSAGE,email)));
    }

    public AppUser signUpUser(AppUser appUser){

        if (appUserRepository.findByEmail(appUser.getEmail()).isPresent()){
            throw new IllegalStateException("Email already taken");
        }

        return appUserRepository.save(appUser);

    }


}
