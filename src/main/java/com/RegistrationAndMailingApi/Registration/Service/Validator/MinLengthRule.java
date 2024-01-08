package com.RegistrationAndMailingApi.Registration.Service.Validator;

public class MinLengthRule implements ValidatorRule{

    private final int minLength = 2;
    @Override
    public boolean validate(String s) {
        return s.length()>=minLength;
    }
}
