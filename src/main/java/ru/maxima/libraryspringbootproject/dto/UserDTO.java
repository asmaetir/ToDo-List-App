package ru.maxima.libraryspringbootproject.dto;


import org.springframework.stereotype.Component;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Component
public class UserDTO {
    private int id;
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 4,max = 50,message = "Фамилия, имя и отчество должны быть не менее 5 символов и не более 50 символов")
    private String username;

    @NotEmpty(message = "Возраст не может быть меньше 5 лет")
    private String email;
    @NotEmpty(message = "Поле электронной почты не может быть пустым")
    private String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
