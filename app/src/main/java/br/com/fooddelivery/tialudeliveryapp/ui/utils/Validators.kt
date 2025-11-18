package br.com.fooddelivery.tialudeliveryapp.ui.utils

import java.util.Calendar

object Validators {

    fun isValidCard(number: String): Boolean {
        if (number.length < 13) return false
        val digitsOnly = number.filter { it.isDigit() }

        var sum = 0
        var alternate = false
        for (i in digitsOnly.length - 1 downTo 0) {
            var n = digitsOnly[i].toString().toIntOrNull() ?: 0
            if (alternate) {
                n *= 2
                if (n > 9) n = (n % 10) + 1
            }
            sum += n
            alternate = !alternate
        }
        return (sum % 10 == 0)
    }

    fun isValidCPF(cpf: String): Boolean {
        val numbers = cpf.filter { it.isDigit() }
        if (numbers.length != 11) return false
        if (numbers.all { it == numbers[0] }) return false

        return try {
            val dv1 = (0..8).sumOf { (it + 1) * numbers[it].digitToInt() } % 11
            val d1 = if (dv1 >= 10) 0 else dv1
            if (d1 != numbers[9].digitToInt()) return false

            val dv2 = (0..9).sumOf { it * numbers[it].digitToInt() } % 11
            val d2 = if (dv2 >= 10) 0 else dv2
            d2 == numbers[10].digitToInt()
        } catch (e: Exception) {
            false
        }
    }

    fun isValidExpiryDate(date: String): Boolean {
        if (date.length != 4) return false
        val month = date.take(2).toIntOrNull() ?: return false
        val year = date.takeLast(2).toIntOrNull() ?: return false

        val fullYear = 2000 + year
        if (month !in 1..12) return false

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

        if (fullYear < currentYear) return false
        if (fullYear == currentYear && month < currentMonth) return false

        return true
    }
}