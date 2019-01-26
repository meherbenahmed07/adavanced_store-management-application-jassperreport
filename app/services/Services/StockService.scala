package services.Services

import Entity.{Stock, StoreUserAccount, UserRole}
import Repository.{ProductRepository, StockRepository, StoreRepository}
import javax.inject.Inject
import services.Exception.{ErrorCode, ErrorStatusCode, StoreException}
import services.write.{StockForm, Stockdto}
import scala.collection.JavaConversions._

import scala.util.{Failure, Success, Try}

class StockService {
  @Inject
  var storeRepository:StoreRepository=_
  @Inject
  var storeService:StoreService=_
  @Inject
  var stockRepository:StockRepository=_
  @Inject
  var productRepository:ProductRepository=_
  def checkIdStock(id:String):Try[Stock]={
    Option(stockRepository.findStockById(id)) match{
      case Some(nameStore)=>Success(nameStore)
      case None=>Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.NotFound_Store))
    }
  }
  def addStock(data:StockForm)(implicit me:StoreUserAccount):Try[Stockdto]={
    var d:String=me.user_role
    for{
      is_able_to_edit <- {
        if(d=="Agent" || d=="StoreManager")
          Success(storeService.checkRole(d,me.id))
        else
          Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.NotFound_Store_manager))
      }
    store_manager<-storeService.checkRole(UserRole.StoreManager,me.id)
    }
      yield{
        var stock:Stock=new Stock()
        stock.name_stock=data.name
        stock.category=data.category
        Option(storeRepository.findStoreById(data.id_store)) match {
          case Some(value)=>{stock.id_store=value}
          case None=>{}
        }
        Option(productRepository.findPoductByStock(stock.id))match {
          case Some(values)=>{
            for(value<-values){
              stock.product_value=stock.product_value+value.price
            }
          }
          case None=>{}
        }
        stockRepository.save(stock)
       Stockdto(stock)
      }
  }
  def getStockByStore(id:String)(implicit me:StoreUserAccount):Try[List[Stockdto]]={
    for{
      filter<-storeService.checkIdStore(id)
    }
      yield{
        var list_dto:List[Stockdto]=List()
        Option(stockRepository.findStockByStore(id))match {
          case Some(values)=>{
            for(value<-values){
              list_dto=list_dto:+Stockdto(value)
            }
          }
          case None=>{}
        }
        list_dto
      }
  }
}
