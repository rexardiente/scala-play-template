package models.dao

import javax.inject.{ Inject, Singleton }
import java.util.UUID
import java.time.Instant
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.utils.PostgresDriver
import models.domain.UserAccountInfo

@Singleton
final class UserAccountInfoDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) 
  extends HasDatabaseConfigProvider[PostgresDriver] {
  import profile.api._

  protected class UserAccountInfoTable(tag: Tag) extends Table[UserAccountInfo](tag, "USER_ACCOUNT_INFO") {
    def id = column[UUID] ("ID")
    def username = column[String] ("USERNAME")
    def password = column[String] ("PASSWORD")
    def email = column[String] ("EMAIL")
    def createdAt = column[Instant] ("CREATE_AT")

    def * = (id, username, password, email, createdAt) <> (UserAccountInfo.tupled, UserAccountInfo.unapply)
  }

  object Query extends TableQuery(new UserAccountInfoTable(_)) {
    def apply(id: UUID) = this.withFilter(_.id === id)
    // clear table datas
    def clearTbl = this.delete
  }
}