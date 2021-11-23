package models.utils

import com.github.tminglei.slickpg._
import play.api.libs.json.{ JsValue, Json }

/***************************************************************************************
*    Title: slick-pg
*    Author: timnglie
*    Availability: https://github.com/tminglei/slick-pg
*
***************************************************************************************/
trait PostgresDriver extends ExPostgresProfile 
                        with PgArraySupport
                        with PgDate2Support
                        with PgRangeSupport
                        with PgHStoreSupport
                        with PgPlayJsonSupport
                        with PgSearchSupport
                        with PgNetSupport
                        with PgLTreeSupport {
  override val api = MyAPI
  def pgjson = "jsonb"
  object MyAPI extends API with ArrayImplicits
    with DateTimeImplicits
    with NetImplicits
    with LTreeImplicits
    with RangeImplicits
    with HStoreImplicits
    with SearchImplicits
    with SearchAssistants {

    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val playJsonArrayTypeMapper = new AdvancedArrayJdbcType[JsValue](pgjson,
      (s) => utils.SimpleArrayUtils.fromString[JsValue](Json.parse(_))(s).orNull,
      (v) => utils.SimpleArrayUtils.mkString[JsValue](_.toString())(v)
    ).to(_.toList)
    // custom type mapper
    implicit val intListTypeMapper = new SimpleArrayJdbcType[Int]("integer").to(_.toList)
    implicit val intListListTypeMapper = new AdvancedArrayJdbcType[List[Int]]("integer[]",
      utils.SimpleArrayUtils.fromString[List[Int]](s =>
        scala.util.Try(s.substring(5, s.length - 1).split(",").map(_.trim.toInt).toList).getOrElse(List())
      )(_).orNull,
      utils.SimpleArrayUtils.mkString[List[Int]](_.toString)
    ).to(_.toList)
  }
}

object PostgresDriver extends PostgresDriver
