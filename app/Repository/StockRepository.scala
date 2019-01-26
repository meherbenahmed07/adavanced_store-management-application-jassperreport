package Repository

import Entity.{Stock, StoreUserAccount}
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
trait StockRepository extends JpaRepository[Stock, String]{
  @Query("select u FROM Stock u where u.id = :id")
  def findStockById(@Param("id") id: String): Stock
  @Query("select u FROM Stock u where u.id = :id")
  def findStocValuekByStore(@Param("id") id: String): java.util.List[Stock]
  @Query("select u FROM Stock u where u.id_store.id = :id_store")
  def findStockByStore(@Param("id_store") id_store: String): java.util.List[Stock]
}
