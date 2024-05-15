package com.id.mobileuts.utils

import com.id.mobileuts.models.PasswordValidationModel

object ValidationUtil {
    fun passwordValidation(password: String): PasswordValidationModel {
        if (!RegexUtil.matchPassword(password)) {
            return PasswordValidationModel(
                false,
                "Kata sandi terdiri 8 sampai 40 karakter."
            )
        }

        if (!RegexUtil.matchPasswordAlphaNumeric(password)) {
            return PasswordValidationModel(
                false,
                "Kata sandi harus terdiri dari huruf dan angka."
            )
        }

        var currentChar = password[0]
        var total = 0

        password.forEachIndexed { index, char ->
            if (currentChar == char && index != 0 && total <= 2) {
                total += 1
                if (total >= 2) {
                    return PasswordValidationModel(
                        false,
                        "Kata sandi tidak boleh terdiri dari 3 karakter berurutan."
                    )
                }
            } else if (total <= 2) {
                currentChar = char
                total = 0
            }
        }

        return PasswordValidationModel(true, null)

    }
}