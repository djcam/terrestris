package com.terrestris.map.form;

import com.terrestris.map.service.inter.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by dcampbell2 on 2/26/2015.
 */

@Component
public class ProfileFormValidator implements Validator {

    private final ProfileService profileService;

    @Autowired
    public ProfileFormValidator(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ProfileForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProfileForm form = (ProfileForm) target;
        validateHandle(errors, form);
    }

    private void validateHandle(Errors errors, ProfileForm form) {
        if (profileService.getProfileByHandle(form.getHandle()).isPresent()) {
            errors.reject("handle.exists", "A profile with this handle already exists");
        }
    }
}
