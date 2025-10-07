package com.josephadogeri.contact_management_app.utils;

public class CredentialsValidatorUtil {
    // Regex pattern for a strong password:
    // - At least 8 characters long
    // - Contains at least one digit (?=.*[0-9])
    // - Contains at least one lowercase letter (?=.*[a-z])
    // - Contains at least one uppercase letter (?=.*[A-Z])
    // - Contains at least one special character (?=.*[@#$%^&+=!])
    // - Does not contain any whitespace characters (\\S+$)
    // - Overall length of 8 to 20 characters (.{8,20})
    private static final String PASSWORD_REGEX =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";

    // Regex: Starts with an alphabet, followed by 7 to 29 alphanumeric characters or underscores.
    // Total length 8 to 30 characters.
    private static final String USERNAMED_REGEX = "^[A-Za-z][A-Za-z0-9_]{7,29}$";

    //Regular Expression by RFC 5322 for Email Validation
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static boolean isValidEmail(String email){
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidPassword(String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidUsername(String username){
        return username.matches(USERNAMED_REGEX);
    }
}
