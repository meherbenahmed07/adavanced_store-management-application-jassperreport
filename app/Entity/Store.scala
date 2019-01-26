package Entity

import javax.persistence.{Column, Entity}
import scala.beans.BeanProperty

@Entity
class Store extends Base {
@Column(nullable = false)
var name_store:String=_
@Column(nullable = false)
@BeanProperty
var address:String=_
var legal_situation:String=_
var Price_of_loan:Float=_
var value_of_stock:Float=_
}
