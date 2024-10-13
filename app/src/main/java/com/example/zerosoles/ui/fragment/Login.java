package com.example.zerosoles.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.zerosoles.R;
import com.example.zerosoles.auth.SessionManager;
import com.example.zerosoles.data.model.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class Login extends Fragment {

    private static final String TAG = "LoginFragment";

    private final SpannableString loginNormal = new SpannableString("Login");
    private final SpannableString signupNormal = new SpannableString("Signup");

    private final SpannableString loginUnderlined = new SpannableString("Login");
    private final SpannableString signupUnderlined = new SpannableString("Signup");

    private int blackColor;
    private int greyColor;

    {
        loginUnderlined.setSpan(new UnderlineSpan(), 0, loginUnderlined.length(), 0);
        signupUnderlined.setSpan(new UnderlineSpan(), 0, signupUnderlined.length(), 0);
    }

    private MaterialButton btnTabLogin;
    private MaterialButton btnTabSignup;

    private ViewGroup layoutLogin;
    private ViewGroup layoutSignup;

    private TextInputLayout etUsernameOrEmail;
    private TextInputLayout etPassword;
    private TextInputLayout etSignupUsername;
    private TextInputLayout etSignupEmail;
    private TextInputLayout etSignupPassword;
    private TextInputLayout etConfirmPassword;

    private MaterialButton btnActionLogin;
    private MaterialButton btnActionSignup;

    private UserModel userModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userModel = new UserModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        blackColor = getResources().getColor(R.color.black, requireActivity().getTheme());
        greyColor = getResources().getColor(R.color.grey_disabled, requireActivity().getTheme());

        layoutLogin = fragmentView.findViewById(R.id.layoutLogin);
        layoutSignup = fragmentView.findViewById(R.id.layoutSignup);

        btnTabLogin = fragmentView.findViewById(R.id.btnTabLogin);
        btnTabSignup = fragmentView.findViewById(R.id.btnTabSignup);

        etUsernameOrEmail = fragmentView.findViewById(R.id.etLayoutUsernameOrEmail);
        etPassword = fragmentView.findViewById(R.id.etLayoutPassword);

        etSignupUsername = fragmentView.findViewById(R.id.etLayoutSignupUsername);
        etSignupEmail = fragmentView.findViewById(R.id.etLayoutSignupEmail);
        etSignupPassword = fragmentView.findViewById(R.id.etLayoutSignupPassword);
        etConfirmPassword = fragmentView.findViewById(R.id.etLayoutConfirmPassword);

        btnActionLogin = fragmentView.findViewById(R.id.btnActionLogin);
        btnActionSignup = fragmentView.findViewById(R.id.btnActionSignup);

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switchToLogin();

        btnTabLogin.setOnClickListener(v -> switchToLogin());
        btnTabSignup.setOnClickListener(v -> switchToSignup());

        btnActionLogin.setOnClickListener(v -> handleLogin());
        btnActionSignup.setOnClickListener(v -> handleSignup());
    }

    private void switchToLogin() {
        btnTabLogin.setText(loginUnderlined);
        btnTabLogin.setTextColor(blackColor);
        btnTabSignup.setText(signupNormal);
        btnTabSignup.setTextColor(greyColor);
        layoutLogin.setVisibility(View.VISIBLE);
        layoutSignup.setVisibility(View.GONE);
    }

    private void switchToSignup() {
        btnTabLogin.setText(loginNormal);
        btnTabLogin.setTextColor(greyColor);
        btnTabSignup.setText(signupUnderlined);
        btnTabSignup.setTextColor(blackColor);
        layoutLogin.setVisibility(View.GONE);
        layoutSignup.setVisibility(View.VISIBLE);
    }

    private void handleLogin() {
        etUsernameOrEmail.setError(null);
        etPassword.setError(null);

        Editable usernameOrEmailText =
                etUsernameOrEmail.getEditText() != null
                        ? etUsernameOrEmail.getEditText().getText()
                        : null;
        Editable passwordText =
                etPassword.getEditText() != null
                        ? etPassword.getEditText().getText()
                        : null;

        boolean err = false;
        if (usernameOrEmailText == null || TextUtils.isEmpty(usernameOrEmailText)) {
            etUsernameOrEmail.setError(getString(R.string.err_required_field));
            err = true;
        }
        if (passwordText == null || TextUtils.isEmpty(passwordText)) {
            etPassword.setError(getString(R.string.err_required_field));
            err = true;
        }
        if (err) return;

        String usernameOrEmail = usernameOrEmailText.toString();
        char[] pwdChars;
        int length = passwordText.length();
        pwdChars = new char[length];
        passwordText.getChars(0, length, pwdChars, 0);
        passwordText.clear();

        executeLogin(usernameOrEmail, pwdChars);
    }

    private void executeLogin(String usernameOrEmail, char[] pwdChars) {
        CompletableFuture.supplyAsync(() -> userModel.authenticate(usernameOrEmail, pwdChars))
                .thenAccept(result -> {
                    requireActivity().runOnUiThread(() -> {
                        Arrays.fill(pwdChars, '\0');
                        if (result.success()) {
                            SessionManager.getInstance().setLoggedInUser(result.getUser());
                        } else {
                            for (UserModel.LoginResult.LoginError error : result.getErrors()) {
                                switch (error) {
                                    case EMPTY_USERNAME:
                                        etUsernameOrEmail.setError(getString(R.string.err_required_field));
                                        break;
                                    case EMPTY_PASSWORD:
                                        etPassword.setError(getString(R.string.err_required_field));
                                        break;
                                    case INVALID_CREDENTIALS:
                                        showErrorAlert(getString(R.string.err_invalid_login_credentials));
                                        break;
                                    case USER_NOT_ACTIVE:
                                        showErrorAlert(getString(R.string.err_account_deactivated));
                                        break;
                                    case CONNECTION_FAILED:
                                    case DATA_ACCESS_FAILED:
                                        showErrorAlert(getString(R.string.err_network));
                                        break;
                                    case OTHER:
                                        showErrorAlert(getString(R.string.err_other));
                                        break;
                                }
                            }
                        }
                    });
                });
    }

    private void handleSignup() {
        etSignupUsername.setError(null);
        etSignupEmail.setError(null);
        etSignupPassword.setError(null);
        etConfirmPassword.setError(null);

        Editable usernameText =
                etSignupUsername.getEditText() != null
                        ? etSignupUsername.getEditText().getText()
                        : null;
        Editable passwordText =
                etSignupPassword.getEditText() != null
                        ? etSignupPassword.getEditText().getText()
                        : null;
        Editable confirmPasswordText =
                etConfirmPassword.getEditText() != null
                        ? etConfirmPassword.getEditText().getText()
                        : null;

        boolean err = false;
        if (usernameText == null || TextUtils.isEmpty(usernameText)) {
            etSignupUsername.setError(getString(R.string.err_required_field));
            err = true;
        }
        if (passwordText == null || TextUtils.isEmpty(passwordText)) {
            etSignupPassword.setError(getString(R.string.err_required_field));
            err = true;
        }
        if (confirmPasswordText == null || TextUtils.isEmpty(confirmPasswordText)) {
            etConfirmPassword.setError(getString(R.string.err_required_field));
            err = true;
        }
        if (err) return;

        String password = passwordText.toString();
        String confirmPassword = confirmPasswordText.toString();
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.err_confirm_password_mismatch));
            return;
        }

        String username = usernameText.toString();
        Editable emailText =
                etSignupEmail.getEditText() != null
                        ? etSignupEmail.getEditText().getText()
                        : null;
        String email = emailText != null ? emailText.toString() : null;
        char[] pwdChars;
        int length = passwordText.length();
        pwdChars = new char[length];
        passwordText.getChars(0, length, pwdChars, 0);
        passwordText.clear();

        CompletableFuture.supplyAsync(() -> userModel.register(username, email, pwdChars))
                .thenAccept(result -> {
                    requireActivity().runOnUiThread(() -> {
                        if (result.success()) {
                            new AlertDialog.Builder(requireContext())
                                    .setMessage(R.string.prompt_signup_success)
                                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                        switchToLogin();
                                        executeLogin(username, pwdChars);
                                    })
                                    .setNegativeButton(android.R.string.no, (dialog, which) ->
                                            requireActivity().getSupportFragmentManager().popBackStack())
                                    .show();
                        } else {
                            for (UserModel.SignupResult.SignupError error : result.getErrors()) {
                                switch (error) {
                                    case EMPTY_USERNAME:
                                        etUsernameOrEmail.setError(getString(R.string.err_required_field));
                                        break;
                                    case EMPTY_PASSWORD:
                                        etPassword.setError(getString(R.string.err_required_field));
                                        break;
                                    case INVALID_EMAIL:
                                        etSignupEmail.setError(getString(R.string.err_invalid_email));
                                        break;
                                    case INVALID_PASSWORD:
                                        etSignupPassword.setError(String.format(
                                                Locale.getDefault(),
                                                getString(R.string.err_invalid_password),
                                                UserModel.MIN_PASSWORD_LENGTH));
                                        break;
                                    case DUPLICATE_USERNAME:
                                        showErrorAlert(getString(R.string.err_duplicate_username));
                                        break;
                                    case DUPLICATE_EMAIL:
                                        showErrorAlert(getString(R.string.err_duplicate_email));
                                        break;
                                    case CONNECTION_FAILED:
                                    case DATA_PERSIST_FAILED:
                                    case DATA_ACCESS_FAILED:
                                        showErrorAlert(getString(R.string.err_network));
                                        break;
                                    case OTHER:
                                        showErrorAlert(getString(R.string.err_other));
                                        break;
                                }
                            }
                        }
                    });
                });
    }

    private void showErrorAlert(String msg) {
        requireActivity().runOnUiThread(() -> {
            new AlertDialog.Builder(requireContext())
                    .setMessage(msg)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}
