package utils

import java.time.{ Instant, LocalTime, LocalDate, LocalDateTime, ZoneOffset }
import java.util.UUID
import scala.jdk.CollectionConverters._
import com.typesafe.config.{ ConfigFactory, ConfigList, ConfigValue }
import play.api.{ ConfigLoader, Configuration }

object SystemDefaultConfig {
	// Contains static methods for creating Config instances.
	val config = ConfigFactory.load()

	// Set UTC as sytem timezone
	// https://stackoverflow.com/questions/15799713/how-to-set-timezone-to-utc-in-play-framework-2-0-for-both-production-and-tests
}