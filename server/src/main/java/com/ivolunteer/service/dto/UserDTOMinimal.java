package com.ivolunteer.service.dto;

import com.ivolunteer.domain.User;

import javax.validation.constraints.Size;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTOMinimal {

    private Long id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;


    public UserDTOMinimal() {
        // Empty constructor needed for Jackson.
    }

    public UserDTOMinimal(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
