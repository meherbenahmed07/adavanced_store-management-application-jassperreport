package Repository

import Entity.legal_situation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import Entity.{Stock, StoreUserAccount}
import org.springframework.data.jpa.repository.{JpaRepository, Query}
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
trait LegalSituationRepository extends JpaRepository[legal_situation, String]{
    @Query("select u FROM legal_situation u where u.eKey = :legal_situation")
    def findlegal_situation(@Param("legal_situation") legal_situation: String): legal_situation
}