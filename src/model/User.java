package model;

import java.sql.*;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User {
    private static final int ITERATIONS=600000;
    private final int id;
    private final String username;

    public User(int id,String username) { this.id=id; this.username=username; }
    public int getId() { return id; }
    public String getUsername() { return username; }

    public static User authenticate(String username,char[] password) throws SQLException {
        try (Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement("SELECT id,username,password_hash FROM users WHERE username=?")) {
            s.setString(1,username.trim());
            try (ResultSet r=s.executeQuery()) {
                if (r.next() && verify(password,r.getString("password_hash"))) return new User(r.getInt("id"),r.getString("username"));
            }
        }
        return null;
    }

    public static void createFirst(String username,char[] password) throws SQLException {
        username=username.trim();
        if (!username.matches("[A-Za-z0-9_.-]{3,50}")) throw new IllegalArgumentException("Username: use 3 to 50 letters, digits, underscores, dots or hyphens.");
        validatePassword(password);
        String encoded=hash(password);
        try (Connection c=DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement lock=c.prepareStatement("SELECT id FROM app_lock WHERE id=1 FOR UPDATE")) { lock.executeQuery().close(); }
                try (PreparedStatement s=c.prepareStatement("SELECT id FROM users LIMIT 1"); ResultSet r=s.executeQuery()) {
                    if (r.next()) throw new IllegalArgumentException("An account already exists. Sign in with that account.");
                }
                try (PreparedStatement s=c.prepareStatement("INSERT INTO users (username,password_hash) VALUES (?,?)")) {
                    s.setString(1,username); s.setString(2,encoded); s.executeUpdate();
                }
                c.commit();
            } catch (SQLException | RuntimeException e) {
                try { c.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        }
    }

    public void changePassword(char[] current,char[] replacement) throws SQLException {
        validatePassword(replacement);
        String encoded=hash(replacement);
        try (Connection c=DBConnection.getConnection()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement s=c.prepareStatement("SELECT password_hash FROM users WHERE id=? FOR UPDATE")) {
                    s.setInt(1,id);
                    try (ResultSet r=s.executeQuery()) {
                        if (!r.next() || !verify(current,r.getString(1))) throw new IllegalArgumentException("Current password is incorrect.");
                    }
                }
                try (PreparedStatement s=c.prepareStatement("UPDATE users SET password_hash=? WHERE id=?")) {
                    s.setString(1,encoded); s.setInt(2,id); s.executeUpdate();
                }
                c.commit();
            } catch (SQLException | RuntimeException e) {
                try { c.rollback(); } catch (SQLException rollback) { e.addSuppressed(rollback); }
                throw e;
            }
        }
    }

    private static void validatePassword(char[] password) {
        if (password.length<8 || password.length>128) throw new IllegalArgumentException("Use a password of 8 to 128 characters.");
    }

    private static String hash(char[] password) throws SQLException {
        byte[] salt=new byte[16]; new SecureRandom().nextBytes(salt);
        return ITERATIONS+":"+Base64.getEncoder().encodeToString(salt)+":"+Base64.getEncoder().encodeToString(derive(password,salt,ITERATIONS));
    }

    private static boolean verify(char[] password,String encoded) throws SQLException {
        try {
            String[] parts=encoded.split(":");
            if (parts.length!=3) return false;
            int iterations=Integer.parseInt(parts[0]);
            if (iterations<100000 || iterations>2000000) return false;
            byte[] salt=Base64.getDecoder().decode(parts[1]);
            byte[] expected=Base64.getDecoder().decode(parts[2]);
            byte[] actual=derive(password,salt,iterations);
            boolean matches=MessageDigest.isEqual(expected,actual);
            Arrays.fill(actual,(byte)0);
            return matches;
        } catch (IllegalArgumentException e) { return false; }
    }

    private static byte[] derive(char[] password,byte[] salt,int iterations) throws SQLException {
        PBEKeySpec spec=new PBEKeySpec(password,salt,iterations,256);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        catch (GeneralSecurityException e) { throw new SQLException("Password hashing is unavailable in this Java runtime.",e); }
        finally { spec.clearPassword(); }
    }
}
