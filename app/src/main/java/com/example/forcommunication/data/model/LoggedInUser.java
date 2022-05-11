package com.example.forcommunication.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoggedInUser {

    private String userId;

    private String displayName;

}