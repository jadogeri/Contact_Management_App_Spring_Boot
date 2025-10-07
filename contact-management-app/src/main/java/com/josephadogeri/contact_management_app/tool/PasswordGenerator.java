package com.josephadogeri.contact_management_app.tool;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PasswordGenerator {

    // Define a custom alphabet without lookalike characters

    private static final String UPPERCASE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARS = "abcdefghijkmnpqrstuvwxyz";
    private static final String DIGIT_CHARS = "23456789";
    private static final String SPECIAL_CHARS = "@";

    private static final String ALL_CHARS = UPPERCASE_CHARS + LOWERCASE_CHARS + DIGIT_CHARS + SPECIAL_CHARS;
    private static final SecureRandom random = new SecureRandom();

    public static String generateStrongPassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException("Password length must be at least 4 to include all required character types.");
        }

        char[] password = new char[length];

        // Ensure at least one of each required type
        password[0] = UPPERCASE_CHARS.charAt(random.nextInt(UPPERCASE_CHARS.length()));
        password[1] = LOWERCASE_CHARS.charAt(random.nextInt(LOWERCASE_CHARS.length()));
        password[2] = DIGIT_CHARS.charAt(random.nextInt(DIGIT_CHARS.length()));
        password[3] = SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));

        // Fill the rest of the password with random characters from the combined pool
        for (int i = 4; i < length; i++) {
            password[i] = ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length()));
        }

        // Shuffle the password to randomize the positions of the required characters
        List<Character> charList = IntStream.range(0, password.length)
                .mapToObj(i -> password[i])
                .collect(Collectors.toList());
        Collections.shuffle(charList);

        return charList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

}
