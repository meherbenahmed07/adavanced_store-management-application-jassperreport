package Entity

import _root_.Entity.Object.EnumBase
import javax.persistence.Entity

@Entity
class UserRole extends EnumBase
object UserRole {
  var Client="Client"
  var StoreManager="StoreManager"
  var Agent="Agent"
}
