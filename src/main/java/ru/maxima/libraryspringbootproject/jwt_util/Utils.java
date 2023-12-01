package ru.maxima.libraryspringbootproject.jwt_util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.maxima.libraryspringbootproject.dto.UserDTO;
import ru.maxima.libraryspringbootproject.model.User;

@Component
public class Utils {
    private final ModelMapper modelMapper;
    @Autowired
    public Utils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
