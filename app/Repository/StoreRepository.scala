package Repository

import Entity.Store
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
@Repository
trait StoreRepository extends JpaRepository[Store, String]{
    @Query("select u FROM Store u where u.name_store = :name")
    def findStoreByName(@Param("name") name: String): Store
  @Query("select u FROM Store u where u.id = :id")
    def findStoreById(@Param("id") id: String): Store
  @Query("select u FROM Store u ")
    def findAllStores(): java.util.List[Store]
}