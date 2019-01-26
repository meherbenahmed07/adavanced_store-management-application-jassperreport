package Repository

import Entity.StoreUserAccount
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
@Repository
trait UserRepository extends JpaRepository[StoreUserAccount, String]{
  @Query("select u FROM StoreUserAccount u where u.email = :email")
  def findByEmail(@Param("email") email: String): StoreUserAccount

  @Query("select u.user_role FROM StoreUserAccount u where u.user_role = :userRole and u.id = :id")
  def findRoleByUser(@Param("userRole") userRole: String,@Param("id") id: String): String
}
