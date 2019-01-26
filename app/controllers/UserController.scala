package controllers

import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import services.Exception.{ErrorCode, ErrorHandler}
import services.Services.{TokenService, UserService}
import services.write.{RegistrationForm, RegistrationWrite}

import scala.util.{Failure, Success}

class UserController extends Controller with I18nSupport{
  @Inject
  implicit var messagesApi: MessagesApi = _
  @Inject
  var userService:UserService=_
  @Inject
  var registrationWrite: RegistrationWrite = _
  @Inject
  var tokenService: TokenService = _
  def registration = Action(parse.urlFormEncoded) {
    implicit request =>
      val form: Form[RegistrationForm] = Form(
        mapping(
          "firstname" -> nonEmptyText,
          "lastname" -> nonEmptyText,
          "email" -> email.verifying(ErrorCode.BadRequest_Email_Duplicated, value => !userService.isEmailExist(value)),
          "password" -> nonEmptyText,
          "confirm_password" -> nonEmptyText,
          "birthday" -> optional(longNumber),
          "gender" ->nonEmptyText,
          "userRole" ->nonEmptyText
        )(RegistrationForm.apply)(RegistrationForm.unapply)
          verifying("badrequest_password_confirmation", data => data.password.equals(data.confirm_password))
      )
      form.bindFromRequest.fold(
        formWithErrors => {
          ErrorHandler.formProcessingError(formWithErrors)
        },
        registration_data => {
          implicit val me = userService.registration(registration_data)
          val token = tokenService.generateAuthToken()
          Ok(Json.obj(
            "lcToken" -> token
          ))
        }
      )
  }
  def authenticate = Action(parse.urlFormEncoded) {
    implicit request =>
      case class SignInForm(email: String, password: String)

      val form = Form[SignInForm](
        mapping(
          "email" -> email,
          "password" -> nonEmptyText
        )(SignInForm.apply)(SignInForm.unapply)

      )
      form.bindFromRequest.fold(
        formWithErrors => {
          BadRequest(Json.toJson("form-error"))
        },
        data => {
          userService.authenticate(data.email, data.password) match {
            case Success((token)) => Ok(Json.obj(
              "lcToken" -> token
            ))
            case Failure(exception) => ErrorHandler.manageException(exception)
          }
        }
      )
  }
}
