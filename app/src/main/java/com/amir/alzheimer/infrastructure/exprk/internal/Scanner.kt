package com.amir.alzheimer.infrastructure.exprk.internal

import com.amir.alzheimer.infrastructure.exprk.ExpressionException
import java.math.MathContext


private fun invalidToken(c: Char) {
    throw ExpressionException("Invalid token '$c'")
}

internal class Scanner(private val source: String,
                       private val mathContext: MathContext) {

    private val tokens: MutableList<Token> = mutableListOf()
    private var start = 0
    private var current = 0

    fun scanTokens(): List<Token> {
        while (!isAtEnd()) {
            scanToken()
        }

        tokens.add(Token(TokenType.EOF, "", null))
        return tokens
    }

    private fun isAtEnd(): Boolean {
        return current >= source.length
    }

    private fun scanToken() {
        start = current
        val c = advance()

        when (c) {
            ' ',
            '\r',
            '\t' -> {
                // Ignore whitespace.
            }
            '+' -> addToken(TokenType.PLUS)
            '-' -> addToken(TokenType.MINUS)
            '*' -> addToken(TokenType.STAR)
            '/' -> addToken(TokenType.SLASH)
            '%' -> addToken(TokenType.MODULO)
            '^' -> addToken(TokenType.EXPONENT)
            '=' -> if (match('=')) addToken(TokenType.EQUAL_EQUAL) else addToken(TokenType.ASSIGN)
            '!' -> if (match('=')) addToken(TokenType.NOT_EQUAL) else invalidToken(c)
            '>' -> if (match('=')) addToken(TokenType.GREATER_EQUAL) else addToken(TokenType.GREATER)
            '<' -> if (match('=')) addToken(TokenType.LESS_EQUAL) else addToken(TokenType.LESS)
            '|' -> if (match('|')) addToken(TokenType.BAR_BAR) else invalidToken(c)
            '&' -> if (match('&')) addToken(TokenType.AMP_AMP) else invalidToken(c)
            ',' -> addToken(TokenType.COMMA)
            '(' -> addToken(TokenType.LEFT_PAREN)
            ')' -> addToken(TokenType.RIGHT_PAREN)
            else -> {
                when {
                    c.isDigit() -> number()
                    c.isAlpha() -> identifier()
                    else -> invalidToken(c)
                }
            }
        }
    }

    private fun number() {
        while (peek().isDigit()) advance()

        if (peek() == '.' && peekNext().isDigit()) {
            advance()

            while (peek().isDigit()) advance()
        }

        val value = source
                .substring(start, current)
                .toBigDecimal(mathContext)

        addToken(TokenType.NUMBER, value)
    }

    private fun identifier() {
        while (peek().isAlphaNumeric()) advance()

        addToken(TokenType.IDENTIFIER)
    }

    private fun advance() = source[current++]

    private fun peek(): Char {
        return if (isAtEnd()) {
            '\u0000'
        } else {
            source[current]
        }
    }

    private fun peekNext(): Char {
        return if (current + 1 >= source.length) {
            '\u0000'
        } else {
            source[current + 1]
        }
    }

    private fun match(expected: Char): Boolean {
        if (isAtEnd()) return false
        if (source[current] != expected) return false

        current++
        return true
    }

    private fun addToken(type: TokenType) = addToken(type, null)

    private fun addToken(type: TokenType, literal: Any?) {
        val text = source.substring(start, current)
        tokens.add(Token(type, text, literal))
    }

    private fun Char.isAlphaNumeric() = isAlpha() || isDigit()

    private fun Char.isAlpha() = this in 'a'..'z'
            || this in 'A'..'Z'
            || this == '_'

    private fun Char.isDigit() = this in '0'..'9'

}