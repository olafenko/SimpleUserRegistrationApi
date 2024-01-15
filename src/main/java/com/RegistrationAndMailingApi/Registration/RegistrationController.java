package com.RegistrationAndMailingApi.Registration;

import com.RegistrationAndMailingApi.Registration.Service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity registerNewUser(@RequestBody RegistrationRequest request){

        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public ResponseEntity confirmToken(@RequestParam String token){
        return registrationService.confirmToken(token);
    }






}
