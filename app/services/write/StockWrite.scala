package services.write

import Entity.Stock
import Repository.{StockRepository, StoreRepository}
import javax.inject.Inject
import play.api.libs.json.{Json, Writes}


case class Stockdto(var stock:Stock)
class StockWrite {
  @Inject
  var storeRepository:StoreRepository=_
  @Inject
  var storeWrite: StoreWrite = _
  implicit def implicitstorewrite=storeWrite.writes
  val writes: Writes[Stockdto] = Writes {
    (stock: Stockdto) => {
      var response =Json.obj()
      var res=Json.obj(
        "name"->stock.stock.name_stock,
        "category"->stock.stock.category,
        "product_value"->stock.stock.product_value
      )
      Option(storeRepository.findStoreById(stock.stock.id_store.id))match {
        case Some(value)=> response+=("Store"->Json.obj(
          "name"->value.name_store,
          "address"->value.address,
          "legal_situation"->value.legal_situation,
          "Price_of_loan"->value.Price_of_loan,
          "value_of_stock"->value.value_of_stock,
          "Stock"->res
        ))
        case None=>{}
      }
      response
    }
  }
}
