package controllers

import Repository.UserRepository
import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms.{mapping, of}
import play.api.data.format.Formats.floatFormat
import play.api.libs.json.Json
import services.Exception.ErrorHandler
import services.write.{ProductForm, ProductWrite, StoreForm}
import services.Exception.ErrorHandler
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats.{doubleFormat, floatFormat}
import play.api.libs.json.Json
import services.Services.ProductService

import scala.util.{Failure, Success}
class ProductController extends FilterController {
  @Inject
  var userRepository:UserRepository=_
  @Inject
  var productService:ProductService=_
  @Inject
  var productWrite: ProductWrite = _
  implicit def implicitproductWrite=productWrite.writes
  def addProduct() = IsAuthenticated(parse.urlFormEncoded) {
    me_id =>
      implicit request =>
        implicit val me = userRepository.findOne(me_id)
        val form = Form(
          mapping(
            "name"->nonEmptyText,
            "price"->of(floatFormat),
            "tax"->optional(number),
            "outOfStock"-> boolean,
            "isFavorite"-> boolean,
            "secret_ref"-> nonEmptyText,
            "id_stock"-> nonEmptyText
          )(ProductForm.apply)(ProductForm.unapply)
        )
        // handle the other form data
        form.bindFromRequest.fold(
          formWithErrors => ErrorHandler.formProcessingError(formWithErrors),
          data =>
            productService.addproduct(data) match {
              case Success(response) => {
                Ok(Json.toJson(response))
              }
              case Failure(exception) => ErrorHandler.manageException(exception)
            }
        )
  }

}
