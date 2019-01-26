package services.write

import Entity.StoreUserAccount
import play.api.libs.json.{Json, Writes}

class RegistrationWrite {
  val writes: Writes[StoreUserAccount] = Writes {
    (color: StoreUserAccount) => {
      var response = Json.obj(
        "id" -> color.id
        //"firstname" -> color.firstname,
        //"lastname" -> color.lastname
        //"email"->color.email,
       // "gender"->color.gender
        //"birthday"->Json.toJson(color.getdateOfBirth())
      )
      response
    }
  }
}
