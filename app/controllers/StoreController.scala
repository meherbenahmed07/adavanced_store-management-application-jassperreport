package controllers

import Repository.UserRepository
import services.Exception.ErrorHandler
import services.write._
import views.html.helper.form
import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats.{doubleFormat, floatFormat}
import play.api.libs.json.Json
import services.Services.StoreService

import scala.util.{Failure, Success}
class StoreController extends FilterController {
  @Inject
  var userRepository:UserRepository=_
  @Inject
  var storeService:StoreService=_
  @Inject
  var storeWrite: StoreWrite = _
  implicit def implicitstorewrite=storeWrite.writes
  def addStore() = IsAuthenticated(parse.urlFormEncoded) {
    me_id =>
      implicit request =>
        implicit val me = userRepository.findOne(me_id)
        val form = Form(
          mapping(
            "name"->nonEmptyText,
            "address"->nonEmptyText,
            "legal_situation"->nonEmptyText,
            "Price_of_loan"-> of(floatFormat),
            "value_of_stock"-> of(floatFormat)
          )(StoreForm.apply)(StoreForm.unapply)
        )
        // handle the other form data
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          data =>
            storeService.addStore(data) match {
              case Success(response) => {
                Ok(Json.toJson(response))
              }
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
        )
  }
  def UpdateStore() = IsAuthenticated(parse.urlFormEncoded) {
    me_id =>
      implicit request =>
        implicit val me = userRepository.findOne(me_id)
        val form = Form(
          mapping(
            "name"->nonEmptyText,
            "newname"->optional(text),
            "address"->optional(text),
            "legal_situation"->optional(text),
            "Price_of_loan"-> optional(of(floatFormat)),
            "value_of_stock"-> optional(of(floatFormat))
          )(UpdateStoreForm.apply)(UpdateStoreForm.unapply)
        )
        // handle the other form data
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          data =>
            storeService.updateStore(data) match {
              case Success(response) => {
                Ok(Json.obj())
              }
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
        )
  }


  def getStores() = IsAuthenticated{
    connected_user_id =>
      implicit request =>
        implicit val me = userAccountRepository.findOne(connected_user_id)
        val form = Form(
          single(
            "name" -> optional(text)
          )
        )
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          succeeded => {
            val name = succeeded
            storeService.getStores(name) match {
              case Success(response) => Ok(Json.toJson(response))
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
          })
  }
  def deleteStore() = IsAuthenticated(parse.urlFormEncoded) {
    me_id =>
      implicit request =>
        implicit val me = userRepository.findOne(me_id)
        val form = Form(
          mapping(
            "name"->nonEmptyText,
            "id"->nonEmptyText
          )(deleteStoreForm.apply)(deleteStoreForm.unapply)
        )
        // handle the other form data
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          data =>
            storeService.deleteStore(data) match {
              case Success(response) => {
                Ok(Json.obj())
              }
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
        )
  }
  def getStoresInformation() = IsAuthenticated{
    connected_user_id =>
      implicit request =>
        implicit val me = userAccountRepository.findOne(connected_user_id)
        val form = Form(
          single(
            "name" -> text
          )
        )
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          succeeded => {
            val name = succeeded
            storeService.getStoreDetails(name) match {
              case Success(response) => Ok(Json.toJson(response))
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
          })
  }
}
