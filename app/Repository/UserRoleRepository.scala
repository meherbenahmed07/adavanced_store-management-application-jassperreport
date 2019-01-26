package Repository

import org.springframework.stereotype.Repository
import Entity.{ UserRole}
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
@Repository
trait UserRoleRepository extends JpaRepository[UserRole, String]{
  @Query("select u FROM UserRole u where u.eKey = :userR")
  def findBykey(@Param("userR") userR: String): UserRole
}
