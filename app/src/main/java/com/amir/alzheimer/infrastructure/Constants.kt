package com.amir.alzheimer.infrastructure

object Constants {
    const val IMAGE_REQUEST_CODE = 15203
    const val ANIMATION_DURATION: Long = 350
    var TO_REMEMBER_SOURCE: List<String> = listOf(
            "خورشید", "آفتاب", "روز", "سواد", "فریاد", "ماشین", "تلوزیون", "ساعت", "بوق", "دیوار",
            "دیو", "ترس", "تنفر", "شاذی", "کمند", "آرزو", "آبی", "مو", "کمد", "لامپ", "صدا", "مداد",
            "بوق", "خودکار", "نوک", "طلایی", "کتاب"
    ).shuffled()

    val TO_REMEMBER_NUMBER: List<String> = (0..999).shuffled().map { it.toString() }

    private val OPERATOS = listOf("*", "/", "+", "-", "+", "-", "+", "-", "+", "-")
    private val NUMBERS = (1..10).toList()

    fun getExpression(length: Int): String {
        var result = ""
        for (i in 0..length) {
            result += "${NUMBERS.random()}${OPERATOS.random()}"
        }
        result += NUMBERS.random()
        return result
    }
}
