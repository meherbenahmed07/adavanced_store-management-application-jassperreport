package Entity

import javax.persistence.{Column, Entity}
import java.math.BigInteger
import java.security.{MessageDigest, NoSuchAlgorithmException}
import org.hibernate.annotations.{GenericGenerator, Type}
import org.joda.time.DateTime
import play.api.Logger

import scala.beans.BeanProperty

@Entity
class StoreUserAccount  extends Base{
  var passwordHashCode: String = _
   var gender: String = _
  var firstname: String = _
  var lastname: String = _
    @Type(`type` = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private var dateOfBirth: DateTime = _
    @Column(length = 255)
    var address: String = _
    @Column(length = 255)
    var email : String = _
    var user_role:String=_

    def setDateOfBirth(date: DateTime) = dateOfBirth = date

    def crypt(password: String): String = {
      var dd:String=password
      var m: MessageDigest = null
      try {
        m = MessageDigest.getInstance("MD5");
        m.update(dd.getBytes());
        val bytes = m.digest
        val sb = new StringBuilder
        for (i <- bytes) {
          sb.append(String.format("%02x", Byte.box(i)))
        }
       sb.toString()
        dd=sb.toString()
        return dd
      } catch {
        case e: NoSuchAlgorithmException => {
          Logger.error(e.toString)
          return null
        }
      }
    }
    def comparePassword(password: String): Boolean = {
      Option(passwordHashCode) match {
        case Some(password_hash) => password_hash.equals(crypt(password))
        case None => false
      }
    }
    def setPasswordHashCode(password: String) = {
      this.passwordHashCode = crypt(password)
    }
    def getdateOfBirth(): Option[scala.Long] = Option(dateOfBirth) match {
      case Some(value) => Some(value.getMillis)
      case None => None
    }
}
