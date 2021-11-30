package models.service

import javax.inject.{ Inject, Singleton }
import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import models.dao.UserAccountInfoDAO
import models.domain.UserAccountInfo

@Singleton
class UserAccountService @Inject()(
		dao: UserAccountInfoDAO
	)(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[utils.PostgresDriver] {
  import profile.api._

  def all(): Future[Seq[UserAccountInfo]] =
    db.run(dao.Query.sortBy(_.createdAt.desc).result)

  def update(account: UserAccountInfo): Future[Int] =
    db.run(dao.Query.filter(_.id === account.id).update(account))

  def add(account: UserAccountInfo): Future[Int] =
    db.run(dao.Query += account)

  def delete(id: UUID): Future[Int] =
    db.run(dao.Query(id).delete)

  def isExistByID(id: UUID): Future[Boolean] =
    db.run(dao.Query(id).exists.result)

  def getSize(): Future[Int] =
    db.run(dao.Query.length.result)

  def findByID(id: UUID): Future[Option[UserAccountInfo]] =
    db.run(dao.Query(id).result.headOption)
}