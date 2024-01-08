package com.RegistrationAndMailingApi.Registration.Service.Validator;

import java.util.regex.Pattern;

public class SpecialCharsRule implements ValidatorRule{

    private final String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";
    @Override
    public boolean validate(String s) {
        return Pattern.compile(regexPattern).matcher(s).matches();
    }
}
