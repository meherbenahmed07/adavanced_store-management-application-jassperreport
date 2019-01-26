package Entity

import _root_.Entity.Object.EnumBase
import javax.persistence.Entity

@Entity
class legal_situation extends EnumBase
object legal_situation {
  var owned="owned"
  var rented="rented"
}

