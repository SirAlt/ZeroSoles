package com.example.zerosoles.data.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zerosoles.data.Connections;
import com.example.zerosoles.data.dao.UserDao;
import com.example.zerosoles.data.entity.User;
import com.example.zerosoles.utils.Emails;
import com.example.zerosoles.utils.PasswordAuthenticator;

import org.jetbrains.annotations.Contract;

import java.nio.CharBuffer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class UserModel {
    public static final int MIN_PASSWORD_LENGTH = 12;
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{" + MIN_PASSWORD_LENGTH + ",}$";

    public static class LoginResult {
        private @Nullable User user;
        private List<LoginError> errors = new LinkedList<>();

        public enum LoginError {
            EMPTY_USERNAME,
            EMPTY_PASSWORD,
            INVALID_CREDENTIALS,
            USER_NOT_ACTIVE,
            CONNECTION_FAILED,
            DATA_ACCESS_FAILED,
            OTHER,
        }

        @NonNull
        @Contract("_ -> new")
        public static LoginResult succeed(User user) {
            return new LoginResult(user);
        }

        @NonNull
        @Contract("_ -> new")
        public static LoginResult fail(LoginError... errors) {
            return new LoginResult(Arrays.asList(errors));
        }

        private LoginResult() {
        }

        private LoginResult(@Nullable User user) {
            this.user = user;
        }

        private LoginResult(List<LoginError> errors) {
            this.errors = errors;
        }

        private void addErrors(LoginError... errors) {
            this.errors.addAll(Arrays.asList(errors));
        }

        public boolean success() {
            return errors.isEmpty() && user != null;
        }

        @Nullable
        public User getUser() {
            return user;
        }

        public List<LoginError> getErrors() {
            return errors;
        }
    }

    public static class SignupResult {
        private boolean success = false;
        private List<SignupError> errors = new LinkedList<>();

        public enum SignupError {
            EMPTY_USERNAME,
            EMPTY_PASSWORD,
            INVALID_EMAIL,
            INVALID_PASSWORD,
            DUPLICATE_USERNAME,
            DUPLICATE_EMAIL,
            CONNECTION_FAILED,
            DATA_ACCESS_FAILED,
            DATA_PERSIST_FAILED,
            OTHER,
        }

        @NonNull
        @Contract("_ -> new")
        public static SignupResult succeed(User user) {
            return new SignupResult(true);
        }

        @NonNull
        @Contract("_ -> new")
        public static SignupResult fail(SignupError... errors) {
            return new SignupResult(Arrays.asList(errors));
        }

        private SignupResult() {
        }

        private SignupResult(boolean success) {
            this.success = success;
        }

        private SignupResult(List<SignupError> errors) {
            this.errors = errors;
        }

        private void addErrors(SignupError... errors) {
            this.errors.addAll(Arrays.asList(errors));
        }

        public boolean success() {
            return success && errors.isEmpty();
        }

        public List<SignupError> getErrors() {
            return errors;
        }
    }

    public LoginResult authenticate(@Nullable String usernameOrEmail, @Nullable char[] password) {
        final String TAG = "LoginAuth";

        // Do not short-circuit and return the failing result here.
        // Doing so opens us up to timing attacks.
        LoginResult inputValidationResult = new LoginResult();
        if (usernameOrEmail == null || usernameOrEmail.trim().isBlank()) {
            inputValidationResult.addErrors(LoginResult.LoginError.EMPTY_USERNAME);
            usernameOrEmail = "";
        }
        if (password == null || password.length == 0) {
            inputValidationResult.addErrors(LoginResult.LoginError.EMPTY_PASSWORD);
            password = new char[0];
        }
        usernameOrEmail = usernameOrEmail.trim();


        List<User> users = new ArrayList<>();
        try (Connection conn = Connections.getDefaultConnection()) {
            if (conn == null) {
                return LoginResult.fail(LoginResult.LoginError.CONNECTION_FAILED);
            }

            UserDao dao = new UserDao(conn);
            dao.setIncludeInactive(true);

            // Try to guess if the identifier is an email, then check against storage accordingly.
            if (Emails.isValidEmailAddress(usernameOrEmail)) {
                users.addAll(dao.getByEmail(usernameOrEmail));
                if (users.isEmpty()) users.addAll(dao.getByName(usernameOrEmail));
            } else {
                users.addAll(dao.getByName(usernameOrEmail));
                if (users.isEmpty()) users.addAll(dao.getByEmail(usernameOrEmail));
            }
        } catch (SQLException e) {
            String err = e.getMessage();
            Log.e(TAG, err != null ? err : e.toString());
            return LoginResult.fail(LoginResult.LoginError.DATA_ACCESS_FAILED);
        }

        PasswordAuthenticator authenticator = new PasswordAuthenticator();
        if (users.isEmpty()) {
            // Perform a trivial call to our hash service to mask our timing.
            char[] fakePwd = new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
            authenticator.authenticate(fakePwd, authenticator.hash(fakePwd));
            Arrays.fill(fakePwd, '\0');
            return LoginResult.fail(LoginResult.LoginError.INVALID_CREDENTIALS);
        } else {
            // Usernames and emails are unique. There should be at most 1 result.
            User user = users.get(0);

            try {
                boolean passwordMatches = authenticator.authenticate(password, users.get(0).getPasswordHash());

                // We've completed all time-consuming processes.
                // Actually return the failing result from the start of the method here.
                if (!inputValidationResult.getErrors().isEmpty()) {
                    return inputValidationResult;
                }

                if (!passwordMatches) {
                    return LoginResult.fail(LoginResult.LoginError.INVALID_CREDENTIALS);
                }
            } catch (Exception e) {
                String err = e.getMessage();
                Log.e(TAG, err != null ? err : e.toString());
                return LoginResult.fail(LoginResult.LoginError.OTHER);
            }

            if (!user.isActive()) {
                return LoginResult.fail(LoginResult.LoginError.USER_NOT_ACTIVE);
            }

            return LoginResult.succeed(user);
        }
    }

    public SignupResult register(@Nullable String username, @Nullable String email, @Nullable char[] password) {
        final String TAG = "SignupAuth";

        SignupResult inputValidationResult = new SignupResult();
        boolean hasEmail = false;
        if (username == null || username.trim().isBlank()) {
            inputValidationResult.addErrors(SignupResult.SignupError.EMPTY_USERNAME);
        }
        if (email != null && !email.isEmpty()) {
            hasEmail = true;
            if (!Emails.isValidEmailAddress(email)) {
                inputValidationResult.addErrors(SignupResult.SignupError.INVALID_EMAIL);
            }
        }
        if (password == null || password.length == 0) {
            inputValidationResult.addErrors(SignupResult.SignupError.EMPTY_PASSWORD);
        } else if (!Pattern.matches(PASSWORD_REGEX, CharBuffer.wrap(password))) {
            inputValidationResult.addErrors(SignupResult.SignupError.INVALID_PASSWORD);
        }
        if (!inputValidationResult.getErrors().isEmpty()) {
            return inputValidationResult;
        }

        assert username != null;
        username = username.trim();
        if (hasEmail) email = email.trim();

        try (Connection conn = Connections.getDefaultConnection()) {
            if (conn == null) {
                return SignupResult.fail(SignupResult.SignupError.CONNECTION_FAILED);
            }
            UserDao dao = new UserDao(conn);
            dao.setIncludeInactive(true);

            List<User> duplicateUsernames = new ArrayList<>(dao.getByName(username));
            if (!duplicateUsernames.isEmpty()) {
                return SignupResult.fail(SignupResult.SignupError.DUPLICATE_USERNAME);
            }

            if (hasEmail) {
                List<User> duplicateEmails = new ArrayList<>(dao.getByEmail(email));
                if (!duplicateEmails.isEmpty()) {
                    return SignupResult.fail(SignupResult.SignupError.DUPLICATE_EMAIL);
                }
            }

            try {
                PasswordAuthenticator authenticator = new PasswordAuthenticator();
                String passwordHash = authenticator.hash(password);

                User newUser = new User.Builder()
                        .withUsername(username)
                        .withPasswordHash(passwordHash)
                        .withRole(User.Role.CUSTOMER)
                        .build();
                if (hasEmail) newUser.setEmail(email);

                if (dao.add(newUser) != 1) {
                    return SignupResult.fail(SignupResult.SignupError.DATA_PERSIST_FAILED);
                }
                return SignupResult.succeed(newUser);
            } catch (Exception e) {
                String err = e.getMessage();
                Log.e(TAG, err != null ? err : e.toString());
                return SignupResult.fail(SignupResult.SignupError.OTHER);
            }
        } catch (SQLException e) {
            String err = e.getMessage();
            Log.e(TAG, err != null ? err : e.toString());
            return SignupResult.fail(SignupResult.SignupError.DATA_ACCESS_FAILED);
        }
    }
}
