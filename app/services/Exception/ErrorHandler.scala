package services.Exception

import play.api.data.Form
import play.api.i18n.Messages
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results._
object ErrorHandler {
  def manageException(exception: Throwable): Result = {
    exception match {
      case StoreException(ErrorStatusCode.BadRequest, code, meta) => BadRequest(Json.toJson(FailResult(code, meta)))
      case exception: Throwable => throw exception
    }
  }
  def formProcessingError(form: Form[_])(implicit messages: Messages) = BadRequest(Json.toJson(FailResult(ErrorCode.FormErrors, Some(form.errorsAsJson))))
}
