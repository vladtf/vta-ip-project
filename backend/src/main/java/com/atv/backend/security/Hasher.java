package com.atv.backend.security;
import org.mindrot.jbcrypt.BCrypt;

 public  class Hasher {
    public static String hashPassword(String password) {

        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(password, salt);

        return hashedPassword;
    }

    public static boolean verifyPassword(String password, String hashedPassword) {
        // Verify the password against the stored hashed password
        return BCrypt.checkpw(password, hashedPassword);
    }

}
