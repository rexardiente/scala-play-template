package models.utils

import scala.util.matching.Regex

object RegexPatterns {
	// email validation based RFC 5322 compliance
	val email: Regex = "(?im)^(?=.{1,64}@)(?:(\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"@)|((?:[0-9a-z](?:\\.(?!\\.)|[-!#\\$%&'\\*\\+/=\\?\\^`\\{\\}\\|~\\w])*)?[0-9a-z]@))(?=.{1,255}$)(?:(\\[(?:\\d{1,3}\\.){3}\\d{1,3}\\])|((?:(?=.{1,63}\\.)[0-9a-z][-\\w]*[0-9a-z]*\\.)+[a-z0-9][\\-a-z0-9]{0,22}[a-z0-9])|((?=.{1,63}$)[0-9a-z][-\\w]*))$".r
}