package services.write

case class ProductForm (  var name:String,
var price:Float,
var tax: Option[Int],
var outOfStock: Boolean,
var isFavorite : Boolean,
var secret_ref:String,
var id_stock:String)
