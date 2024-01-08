package com.RegistrationAndMailingApi.Registration.Service.Validator;

public class MaxLengthRule implements ValidatorRule{

    private int maxLength = 20;
    @Override
    public boolean validate(String s) {
        return s.length()<=maxLength;
    }
}
