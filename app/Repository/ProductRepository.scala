package Repository

import Entity.Product
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
trait ProductRepository extends JpaRepository[Product, String]{
  @Query("select u FROM Product u where u.id_stock.id = :stockid")
  def findPoductByStock(@Param("stockid") stockid: String): java.util.List[Product]
}
