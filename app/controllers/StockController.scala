package controllers

import play.api.data.Forms.{mapping, of}
import services.write.{StockForm, StockWrite, StoreForm}

import scala.util.{Failure, Success}
import Repository.UserRepository
import services.Exception.ErrorHandler
import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats.{doubleFormat, floatFormat}
import play.api.libs.json.Json
import services.Services.StockService
class StockController extends FilterController {
  @Inject
  var userRepository:UserRepository=_
  @Inject
  var stockService:StockService=_
  @Inject
  var stockWrite: StockWrite = _
  implicit def implicitstockWrite=stockWrite.writes
  def addStock() = IsAuthenticated(parse.urlFormEncoded) {
    me_id =>
      implicit request =>
        implicit val me = userRepository.findOne(me_id)
        val form = Form(
          mapping(
            "name"->nonEmptyText,
            "category"->nonEmptyText,
            "product_value"->of(floatFormat),
            "id_store"-> nonEmptyText
          )(StockForm.apply)(StockForm.unapply)
        )
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          data =>
            stockService.addStock(data) match {
              case Success(response) => {
                Ok(Json.toJson(response))
              }
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
        )
  }
  def getStockByStore() = IsAuthenticated{
    connected_user_id =>
      implicit request =>
        implicit val me = userAccountRepository.findOne(connected_user_id)
        val form = Form(
          single(
            "id" -> text
          )
        )
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          succeeded => {
            val id = succeeded
            stockService.getStockByStore(id) match {
              case Success(response) => Ok(Json.toJson(response))
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
          })
  }
}
