package ru.maxima.libraryspringbootproject.validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.maxima.libraryspringbootproject.model.User;
import ru.maxima.libraryspringbootproject.service.UsersDetailsService;

@Component
public class UserValidator  implements Validator {
    private final UsersDetailsService userDetailsService;

    @Autowired
    public UserValidator(UsersDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            userDetailsService.loadUserByUsername(user.getEmail());
        } catch (UsernameNotFoundException e) {
            return;
        }

        errors.rejectValue("name", "Пользователь с таким именем не найден");
    }
}
