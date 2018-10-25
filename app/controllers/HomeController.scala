package controllers

import java.io.{File, FileWriter}

import javax.inject._
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

import scala.io.Source


/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)(implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc) with play.api.i18n.I18nSupport {

  val urlForm = Form(
    mapping(
      "url" -> text
    )(UrlData.apply)(UrlData.unapply)
  )

  private def urlDatabase: Option[File] = {
    val db = new File("urls.txt")
    Option(db) filter (_.exists())
  }

  private def findIndex(url: String) = {
    def loop(index: Int, rest: List[String]): Option[Int] = {
      rest match {
        case Nil => None
        case x :: _ if x == url => Option(index)
        case _ :: xs => loop(index + 1, xs)
      }
    }

    urlDatabase flatMap { db =>
      val f = Source.fromFile(db)
      val data = f.getLines().toList
      f.close()
      loop(0, data)
    }
  }

  private def findUrl(i: Int) = {
    def loop(index: Int, rest: List[String]): Option[String] = {
      rest match {
        case Nil => None
        case x :: _ if index == i => Option(x)
        case _ :: xs => loop(index + 1, xs)
      }
    }

    urlDatabase flatMap { db =>
      val f = Source.fromFile(db)
      val data = f.getLines().toList
      f.close()
      loop(0, data)
    }
  }

  def urlPost: Action[UrlData] = Action(parse.form(urlForm)) { implicit request =>
    val userData = request.body
    val index = insert(userData.url)
    index map { i =>
      Ok(views.html.indexshow(urlForm, (userData.url, i), "Url-Shortner"))
    } getOrElse {
      Redirect(routes.HomeController.index)
    }
  }

  def index: Action[AnyContent] = Action { implicit request =>
    Ok(views.html.index(urlForm, "Url-Shortner"))
  }

  def redirect(id: Int): Action[AnyContent] = Action {
    findUrl(id) match {
      case Some(url) => Redirect(url)
      case None => Redirect("/")
    }
  }

  def insert(url: String): Option[Int] = {

    Option(url) map (_.trim) filter (_.nonEmpty) flatMap { u =>
      findIndex(u) match {
        case Some(i) => Option(i)
        case _ =>
          val fw = new FileWriter("urls.txt", true)
          fw.write(System.lineSeparator() + u)
          fw.close()
          findIndex(u)
      }
    }
  }

}

case class UrlData(url: String)