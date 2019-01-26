package controllers

import Repository.UserRepository
import javax.inject.Inject
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import play.mvc.Http
import services.Exception.ErrorHandler
import services.Services.TokenService

import scala.util.{Failure, Success}

class FilterController extends Controller with  I18nSupport{
  @Inject
  var userAccountRepository: UserRepository = _
  @Inject
  var tokenService: TokenService = _
  @Inject
  implicit var messagesApi: MessagesApi = _
  def IsAuthenticated[A](b: BodyParser[A])(f: => String => Request[A] => Result) = {
    Security.Authenticated(getToken, onUnauthorized) { JWToken =>
      tokenService.getUserFromToken(JWToken) match {
        case Success(user_id) => Action(b)(request => f(user_id)(request))
        case Failure(exception) => Action(b)(request => ErrorHandler.manageException(exception))
      }
    }
  }
  private def getToken(request: RequestHeader) = request.headers.get(Http.HeaderNames.AUTHORIZATION)
  private def onUnauthorized(request: RequestHeader) = Results.Unauthorized.withHeaders(Http.HeaderNames.WWW_AUTHENTICATE -> "token not found in Authorization http request header")
  def IsAuthenticated(f: => String => Request[AnyContent] => Result) = IsAuthenticated[AnyContent](parse.anyContent)(f)

}
