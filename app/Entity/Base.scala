package Entity

import javax.persistence._
import org.hibernate.annotations.GenericGenerator
import org.springframework.data.domain.Persistable

import scala.annotation.meta.field
import scala.beans.BeanProperty

@MappedSuperclass
abstract class Base extends Persistable[String] {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @BeanProperty
  var id: String = _

  @Version
  @Column(columnDefinition = "INT DEFAULT 0")
  @field
  @BeanProperty
  var version: Int = _
  override def isNew() = true
}
