package services.Services

import Entity.StoreUserAccount
import javax.inject.Inject
import services.write.{ProductForm, Productdto}
import Entity.Product
import Repository.{ProductRepository, StockRepository, StoreRepository}
import scala.util.Try
class ProductService {
  @Inject
  var storeService:StoreService=_
  @Inject
  var stockService:StockService=_
  @Inject
  var stockRepository:StockRepository=_
  @Inject
  var storeRepository:StoreRepository=_
  @Inject
  var productRepository:ProductRepository=_
def addproduct(data:ProductForm)(implicit  me:StoreUserAccount):Try[Productdto]={
for{
store<-stockService.checkIdStock(data.id_stock)
}yield{
val product:Product=new Product
  product.name_product=data.name
  product.price=data.price
  data.tax match {
    case Some(value)=>{
      product.tax=value
      product.price=data.price+((data.price*value)/100)
    }
    case None=>{}
  }
  product.outOfStock=data.outOfStock
  product.secret_ref=data.secret_ref
  Option(stockRepository.findStockById(data.id_stock))match {
  case Some(value)=>{
    product.id_stock=value
  }
  case None=>{}
}
 productRepository.save(product)
  Productdto(product)
}
}
}
