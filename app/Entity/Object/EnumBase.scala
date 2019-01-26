package Entity.Object

import java.io.Serializable
import javax.persistence._

import org.hibernate.annotations.GenericGenerator


/**
  * EnumBase is the base entity used to represent an Enumerated values
  */
@MappedSuperclass
abstract class EnumBase extends Serializable {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private var id: String = _

  @Column(unique = true)
  var eKey: String = _

  @Column(unique = true)
  var eValue: String = _

  def getId() = id

}

