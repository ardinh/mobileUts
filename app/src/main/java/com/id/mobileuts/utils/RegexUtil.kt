package com.id.mobileuts.utils

object RegexUtil {
    fun matchEmail(input: String): Boolean {
        val regex = Regex("^[\\w._%+-]+@[\\w.-]+\\.[a-z]+\$")
        return regex.matches(input)
    }

    fun matchPassword(input: String): Boolean {
        val regex = Regex("^.{8,40}$")
        return regex.matches(input)
    }

    fun matchPasswordAlphaNumeric(input: String): Boolean {
        val regex = Regex("^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9\\W_]+)\$")
        return regex.matches(input)
    }

    fun matchPhone(input: String): Boolean {
        val regex = Regex("^[0-9][0-9]{9,13}")
        return regex.matches(input)
    }

    fun matchTelephone(input: String): Boolean {
        val regex = Regex("^[1-9][0-9]{6,13}")
        return regex.matches(input)
    }

    fun matchTimeZone(input: String): Boolean {
        val regex = Regex("[+-]\\d{1,2}:\\d{1,2}")
        return regex.matches(input)
    }
}