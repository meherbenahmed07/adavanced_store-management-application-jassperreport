package services.write

case class RegistrationForm(firstName: String, lastName: String, email: String, password: String, confirm_password: String,
                            birthday: Option[Long], gender: String,userRole:String)
