package Entity

import javax.persistence.{Column, Entity, JoinColumn, ManyToOne}

@Entity
class Stock extends Base {
@Column(unique = true,nullable = true)
var name_stock:String=_
var category:String=_
var product_value:Float=_
@ManyToOne
@JoinColumn(nullable = false)
var id_store:Store=_
}
