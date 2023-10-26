package com.pluralsight;

public class PasswordHashing {

    //https://www.codewars.com/kata/520b9d2ad5c005041100000f/java
    //Pig Latin Hash ('Hamza' -> 'amzaHay')


    /**
     * Converts a given password into Pig Latin Hash format by applying the Pig Latin transformation rules.
     *
     * @param password The input password to be converted to Pig Latin.
     * @return The password in Pig Latin format ('Hamza' -> 'amzaHay').
     */
    public static String passwordHashPigLatin(String password){
        // Split the input sentence into words array
        String[] words = password.split(" ");
        // Process each word and convert it to PigLatin.
        for (int i = 0; i < words.length; i++) {
            // If the word contains non-alphabet characters, leave it untouched.
            if (!words[i].matches("^[a-zA-Z]+$")) {
                continue;
            }
            // Move the first letter of the word to the end and add "ay" to the end.
            words[i] = words[i].substring(1) + words[i].charAt(0) + "ay";
        }
        // Reconstruct the transformed sentence.
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word).append(" ");
        }

        return sb.toString().trim();
    };
}
