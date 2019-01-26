package services.write

import play.api.libs.json.{JsNull, Json, Writes}
import Entity.{Product, Stock, Store}
import Repository.{ProductRepository, StockRepository, StoreRepository}
import javax.inject.Inject
//case class allstoredto(var store:Store)
//class AllStoreWrite {
//  @Inject
//  var stockRepository:StockRepository=_
//  @Inject
//  var storeRepository:StoreRepository=_
//  @Inject
//  var productRepository:ProductRepository=_
//  val writes: Writes[allstoredto] = Writes {
//    (allstoredto: allstoredto) => {
//      var response=Json.obj()
//      var res=Json.obj()
//      Option(storeRepository.findStoreById(allstoredto.store.id))match {
//        case Some(value)=>{
//          Option(stockRepository.findStockByStore1(value.id))match {
//            case Some(values)=>{
//              for(valuestock<-values)
//              response+=("Store"->Json.obj(
//                "name"->value.name,
//                "address"->value.address,
//                "legal_situation"->value.legal_situation,
//                "Price_of_loan"->value.Price_of_loan,
//                "value_of_stock"->value.value_of_stock,
//                "Stock"->Json.obj(
//                  "name"->valuestock.name
//                  )
//                 )
//                )
//            }
//          }
//        }
//        case None=>{}
//      }
//      response
//    }
//  }
//}