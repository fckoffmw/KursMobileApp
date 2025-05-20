package com.example.kurs.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.kurs.R;
import com.example.kurs.utils.UserPreferences;

public class LoginFragment extends Fragment {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Находим поля и кнопку
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);

        // ✅ Инициализация UserPreferences
        UserPreferences userPreferences = new UserPreferences(requireContext());

        // Обработчик кнопки входа
        buttonLogin.setOnClickListener(v -> {
            String inputEmail = editTextEmail.getText().toString().trim();
            String inputPassword = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(inputEmail) || TextUtils.isEmpty(inputPassword)) {
                Toast.makeText(getActivity(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Проверка данных
            String savedEmail = userPreferences.getEmail();
            String savedPassword = userPreferences.getPassword();

            if (inputEmail.equals(savedEmail) && inputPassword.equals(savedPassword)) {
                Toast.makeText(getActivity(), "Вход выполнен", Toast.LENGTH_SHORT).show();

                // Переход на главный экран
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MovieListFragment())
                        .commit();
            } else {
                Toast.makeText(getActivity(), "Неверный email или пароль", Toast.LENGTH_SHORT).show();
            }
        });

        // Переход на регистрацию при клике на текст
        view.findViewById(R.id.textRegister).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }
}
