package com.josephadogeri.contact_management_app.interfaces;

public interface Validatable {
    // Regex pattern for a strong password:
    // - At least 8 characters long
    // - Contains at least one digit (?=.*[0-9])
    // - Contains at least one lowercase letter (?=.*[a-z])
    // - Contains at least one uppercase letter (?=.*[A-Z])
    // - Contains at least one special character (?=.*[@#$%^&+=!])
    // - Does not contain any whitespace characters (\\S+$)
    // - Overall length of 8 to 20 characters (.{8,20})
    static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";

    // Regex: Starts with an alphabet, followed by 6 to 29 alphanumeric characters or underscores.
    // Total length 7 to 30 characters.
    static final String USERNAME_REGEX = "^[A-Za-z][A-Za-z0-9_]{6,29}$";

    //    It allows numeric values from 0 to 9.
    //    Both uppercase and lowercase letters from a to z are allowed.
    //    Allowed are underscore “_”, hyphen “-“, and dot “.”
    //    Dot isn’t allowed at the start and end of the local part.
    //    Consecutive dots aren’t allowed.
    //    For the local part, a maximum of 64 characters are allowed.
    static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";


    public boolean isValidEmail(String email);

    public boolean isValidPassword(String password);

    public boolean isValidUsername(String username);

}
