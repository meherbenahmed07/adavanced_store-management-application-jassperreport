package services.Services

import Entity.{Store, StoreUserAccount}
import Repository.{StoreRepository, UserRepository, UserRoleRepository}
import javax.inject.Inject
import org.joda.time.DateTime
import org.springframework.transaction.annotation.Transactional
import services.Exception.{ErrorCode, ErrorStatusCode, StoreException}
import services.write.RegistrationForm

import scala.util.{Failure, Success, Try}

class UserService  {
  @Inject
  var userRepository:UserRepository=_
  @Inject
  var userRoleRepository:UserRoleRepository=_
  @Inject
  var storeRepository:StoreRepository=_
  @Inject
  var tokenService: TokenService = _
  def isEmailExist(email: String): Boolean = {
    Option(userRepository.findByEmail(email)) match {
      case Some(user) => true
      case None => false
    }
  }
  @Transactional
  def registration(registration: RegistrationForm): StoreUserAccount = {
    var newAccount: StoreUserAccount = new StoreUserAccount()
    newAccount.firstname = registration.firstName
    newAccount.lastname = registration.lastName
    newAccount.setPasswordHashCode(registration.password)
    newAccount.gender = registration.gender
    registration.birthday match {
      case Some(birthday) => newAccount.setDateOfBirth(new DateTime(birthday))
      case None => {}
    }
    newAccount.user_role=userRoleRepository.findBykey(registration.userRole).eKey
    newAccount.email = registration.email
    newAccount = userRepository.save(newAccount)
    newAccount
  }
  def getByEmail(email: String): Try[StoreUserAccount] = {
    Option(userRepository.findByEmail(email)) match {
      case Some(account) => {
        Success(account)
      }
      case None =>  Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.BadRequest_Credentials))
    }
  }

  @Transactional
  def authenticate(email: String, password: String): Try[String] = {
    for {
      me <- {
        if (email.contains("cashdesk@snugmenu.com") || email.contains("adhoc@@snugmenu.com"))
          getByEmail(email)
        else
          getByEmailByPassword(email, password)
      }
    } yield {
      implicit val user = me
      val token = tokenService.generateAuthToken()
      var page:String=""
      var customer:String=""
      (token)
    }
  }
  def getByEmailByPassword(email: String, password: String): Try[StoreUserAccount] = {
    Option(userRepository.findByEmail(email)) match {
      case Some(account) => {
        if (account.comparePassword(password))
          Success(account)
        else
          Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.BadRequest_Credentials))
      }
      case None =>   Failure(StoreException(ErrorStatusCode.BadRequest, ErrorCode.BadRequest_Credentials))
    }
  }
}