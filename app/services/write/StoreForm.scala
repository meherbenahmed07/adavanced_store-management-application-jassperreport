package services.write

case class StoreForm (
                        name:String,
                        address:String,
                        legal_situation:String,
                        Price_of_loan:Float,
                        value_of_stock:Float
                     )
case class GetStoreForm (
                        name:String
                     )
case class UpdateStoreForm(
                            name:String,
                            newname:Option[String],
                            address:Option[String],
                            legal_situation:Option[String],
                            Price_of_loan:Option[Float],
                            value_of_stock:Option[Float]
                          )
case class deleteStoreForm(
                            name:String,
                            id:String
                          )

