package services.write

import Entity.Product
import Repository.StockRepository
import javax.inject.Inject
import play.api.libs.json.{Json, Writes}

case class Productdto(var product:Product)
class ProductWrite {
  @Inject
  var stockRepository:StockRepository=_
  val writes: Writes[Productdto] = Writes {
    (product: Productdto) => {
      var response=Json.obj()
      var response_product = Json.obj(
        "id" -> product.product.id,
        "name" -> product.product.name_product,
        "tax" -> product.product.tax,
        "outOfStock"->product.product.outOfStock,
        "secret_ref"->product.product.secret_ref
      )
      Option(stockRepository.findStockById(product.product.id_stock.id))match {
        case Some(value)=>response+={"Stock"->Json.obj(
          "name"->product.product.id_stock.name_stock,
          "category"->product.product.id_stock.category,
          "product_value"->product.product.id_stock.product_value,
          "Product"->response_product
        )}
        case None=>{}
      }
      response
    }
  }
}
