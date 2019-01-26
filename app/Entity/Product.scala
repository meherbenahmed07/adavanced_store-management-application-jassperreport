package Entity

import javax.persistence.{Column, Entity, JoinColumn, ManyToOne}
@Entity
class Product extends Base{
  var name_product:String=_
  var price:Float=_
  var tax: Int = 0
  var outOfStock: Boolean = false
  @Column(unique = true)
  var secret_ref:String=_
  @ManyToOne
  @JoinColumn(nullable = false)
  var id_stock:Stock=_
}
