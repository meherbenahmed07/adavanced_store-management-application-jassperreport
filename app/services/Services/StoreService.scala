package services.Services

import Entity.{Store, StoreUserAccount, UserRole, legal_situation}
import Repository._
import javax.inject.Inject
import org.springframework.transaction.annotation.Transactional
import play.api.libs.json.{JsArray, JsValue, Json}
import services.Exception.{ErrorCode, ErrorStatusCode, StoreException}
import services.write._

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

class StoreService {
  @Inject
  var userRoleRepository:UserRoleRepository=_
  @Inject
  var storeRepository:StoreRepository=_
  @Inject
  var stockRepository:StockRepository=_
  @Inject
  var userRepository:UserRepository=_
  @Inject
  var productRepository:ProductRepository=_
  @Inject
  var legalSituationRepository:LegalSituationRepository=_
  @Inject
  var stockWrite: StockWrite = _
  implicit def implicitstockWrite=stockWrite.writes
def addStore(data:StoreForm)(implicit me:StoreUserAccount):Try[Storedto]={
for{
role<-checkRole(UserRole.StoreManager,me.id)
legal_situation<-getlegal_situation(data.legal_situation)
} yield {
  val store:Store=new Store()
 store.name_store=data.name
  store.address=data.address
  store.legal_situation=legalSituationRepository.findlegal_situation(data.legal_situation).eKey
  store.Price_of_loan=data.Price_of_loan
  Option(stockRepository.findStocValuekByStore(store.id))match {
    case Some(values)=>{
      for(value<-values){
        store.value_of_stock=store.value_of_stock+value.product_value
      }
    }
  }
  storeRepository.save(store)
  Storedto(store)
}
}
  def checkRole(role:String,id:String):Try[String]={
    Option(userRepository.findRoleByUser(role,id)) match{
    case Some(userRole)=>Success(userRole)
    case None=>Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.NotFound_Store_manager))
  }
  }
  def checkNameStore(name:String):Try[Store]={
    Option(storeRepository.findStoreByName(name)) match{
    case Some(nameStore)=>Success(nameStore)
    case None=>Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.NotFound_Store))
  }
  }
  def checkIdStore(id:String):Try[Store]={
    Option(storeRepository.findStoreById(id)) match{
    case Some(nameStore)=>Success(nameStore)
    case None=>Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.NotFound_Store))
  }
  }
  def getlegal_situation(legal_situation:String):Try[legal_situation]={
    Option(legalSituationRepository.findlegal_situation(legal_situation)) match{
    case Some(legal_situation)=>Success(legal_situation)
    case None=>Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.NotFound_legal_situation))
  }
  }
  def getStores(name:Option[String])(implicit me:StoreUserAccount):Try[List[Storedto]]={
  for{
    role<-checkRole(UserRole.StoreManager,me.id)
  }
  yield{
    val all_fields = name.getOrElse("")
    val split_fields = all_fields.split(",")
    var list_stordto:List[Storedto]=List()
    for(split_field<-split_fields){
      Option(split_field)match {
        case Some(value)=>{
          if(value!="")
          Option(storeRepository.findStoreByName(value)) match {
            case Some(value)=>list_stordto=list_stordto:+Storedto(value)
            case None=>Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.NotFound_Store))
          }else{
            Option(storeRepository.findAllStores()) match {
              case Some(values)=>{
                for(value<-values){
                  list_stordto=list_stordto:+Storedto(value)
                }
              }

            }
          }
        }
        case None=>{}
      }
    }
    list_stordto
   }
  }
  @Transactional
  def updateStore(data:UpdateStoreForm)(implicit me:StoreUserAccount):Try[Unit]={
    for{
      role<-checkRole(UserRole.StoreManager,me.id)
      name<-checkNameStore(data.name)
    }yield{
        Option(storeRepository.findStoreByName(data.name))match {
          case Some(value)=>{
            data.newname match {
              case Some(newname)=>name.name_store=newname
              case None=>{}
            }
            data.address match {
              case Some(address)=>name.address=address
              case None=>{}
            }
            data.legal_situation match {
              case Some(legal_situation)=>value.legal_situation=legal_situation
              case None=>{}
            }
            data.value_of_stock match {
              case Some(value_of_stock)=>value.value_of_stock=value_of_stock
              case None=>{}
            }
            data.Price_of_loan match {
              case Some(price_of_loan)=>value.Price_of_loan=price_of_loan
              case None=>{}
            }
            storeRepository.save(value)
          }
          case None=>{}
        }
      }
  }
  @Transactional
  def deleteStore(data:deleteStoreForm)(implicit me:StoreUserAccount):Try[Unit]={
    for{
      role<-checkRole(UserRole.StoreManager,me.id)
      name<-checkNameStore(data.name)
    }yield{
        Option(storeRepository.findStoreByName(data.name))match {
          case Some(value)=>{
         storeRepository.delete(data.id)
          }
          case None=>{}
        }
      }
  }
  def getStoreDetails(name:String)(implicit me:StoreUserAccount):Try[JsValue]={
    for{
      namecheck<-checkNameStore(name)
    }
      yield{
      var response=Json.obj()
        var re=Json.arr()
        var weled_awatef:List[Stockdto]=List()
        Option(storeRepository.findStoreById(namecheck.id))match {
          case Some(value)=>{
            Option(stockRepository.findStockByStore(value.id))match {
              case Some(values)=>{
                  response+=("Store"->Json.obj(
                    "name"->value.name_store,
                    "address"->value.address,
                    "legal_situation"->value.legal_situation,
                    "Price_of_loan"->value.Price_of_loan,
                    "value_of_stock"->value.value_of_stock,
                    "Stock"->Json.toJson(stockRepository.findStockByStore(value.id).map(s=>Json.obj(
                      "name"->s.name_stock,
                      "category"->s.category,
                      "product_value"->s.product_value,
                      "Product"->Json.toJson(productRepository.findPoductByStock(s.id).map(p=>Json.obj(
                        "name"->p.name_product,
                        "price"->p.price,
                        "tax"->p.tax,
                        "outOfStock"->p.outOfStock,
                        "secret_ref"->p.secret_ref
                      )
                      ))
                    )))
                  )
                 )
              }
              case None=>{}
            }
          }
          case None=>{}
        }
        response
      }
  }
}
