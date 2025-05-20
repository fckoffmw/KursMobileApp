package com.example.kurs.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.kurs.R;
import com.example.kurs.utils.UserPreferences;

public class RegisterFragment extends Fragment {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageButton buttonBack;

    public RegisterFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextName = view.findViewById(R.id.editTextName);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonBack = view.findViewById(R.id.buttonBack);

        // Инициализируем UserPreferences
        UserPreferences userPreferences = new UserPreferences(requireContext());

        // Обработчик кнопки "Назад"
        buttonBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Обработка нажатия кнопки "Зарегистрироваться"
        view.findViewById(R.id.buttonRegister).setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(getActivity(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getActivity(), "Введите корректный email", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Сохраняем данные пользователя
            userPreferences.saveUser(email, password);

            Toast.makeText(getActivity(), "Регистрация успешна", Toast.LENGTH_SHORT).show();

            // Переход на экран входа
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        });
    }
}
