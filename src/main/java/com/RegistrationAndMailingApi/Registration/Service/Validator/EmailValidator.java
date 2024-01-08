package com.RegistrationAndMailingApi.Registration.Service.Validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailValidator implements ValidatorRule {

    List<ValidatorRule> validatorRuleList;

    @Override
    public boolean validate(String s) {
        return validatorRuleList.stream().allMatch(validatorRule -> validatorRule.validate(s));
    }
}
