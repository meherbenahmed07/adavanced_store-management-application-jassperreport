package services.write

import Entity.Store
import play.api.libs.json.{Json, Writes}
case class Storedto(var store:Store)
class StoreWrite {
  val writes: Writes[Storedto] = Writes {
    (store: Storedto) => {
      var response = Json.obj(
        "id" -> store.store.id,
        "name" -> store.store.name_store,
        "legalSituation" -> store.store.legal_situation,
        "valueOfStock"->store.store.value_of_stock,
         "PriceOfLoan"->store.store.Price_of_loan
      )
      response
    }
  }
}
