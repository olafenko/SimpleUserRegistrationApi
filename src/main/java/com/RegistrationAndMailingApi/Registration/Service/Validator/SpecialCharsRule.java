package com.RegistrationAndMailingApi.Registration.Service.Validator;

import java.util.regex.Pattern;

public class SpecialCharsRule implements ValidatorRule{

    private final String regexPattern = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    @Override
    public boolean validate(String s) {
        return Pattern.compile(regexPattern,Pattern.CASE_INSENSITIVE).matcher(s).matches();
    }
}
