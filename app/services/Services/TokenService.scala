package services.Services

import Entity.StoreUserAccount
import Repository.UserRepository
import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}
import javax.inject.Inject
import org.joda.time.DateTime
import play.api.Logger
import services.Exception.{ErrorCode, ErrorStatusCode, StoreException}

import scala.util.{Failure, Success, Try}

class TokenService {
  @Inject
  var configuration: play.api.Configuration = _
  @Inject
  var userRepository: UserRepository = _
  private def generateToken(private_claims: Map[String, String], secret_key: String) = {
    val header = JwtHeader("HS256")
    var public_claims: Map[String, String] = Map()
    public_claims += ("iat" -> DateTime.now().getMillis.toString)
    val claims = public_claims ++ private_claims
    val claimsSet = JwtClaimsSet(claims)
    JsonWebToken(header, claimsSet, secret_key)
  }

  def generateAuthToken(private_claims: Map[String, String] = Map())(implicit me: StoreUserAccount): String = {
    var claims: Map[String, String] = Map()
    claims += ("userID" -> me.id)
    val secret_key = Option(me.passwordHashCode).getOrElse(configuration.getString("application.secret").getOrElse("Store_default_secret_key"))
    generateToken(claims, secret_key)
  }

  def getUserFromToken(JWToken: String): Try[String] = {
    JWToken match {
      case JsonWebToken(header, claimsSet, signature) => {
        val claims = claimsSet.asSimpleMap.toOption.getOrElse(Map.empty[String, String])
        val user_id = claims.getOrElse("userID", "")
        Option(userRepository.findOne(user_id)) match {
          case Some(user) => {
            // best security mode : encrypt every token by connected user password else from application.secret else default password
            val secret_key = Option(user.passwordHashCode).getOrElse(configuration.getString("application.secret").getOrElse("StoreManager_secret_key"))

            //Check if the jwt is valid
            if (JsonWebToken.validate(JWToken, secret_key)) {
              //successful authentication and valid token
              Success(user_id)
            }
            else {
              Logger.info("invalid token: invalid signature."+ System.lineSeparator() + JWToken)
              Failure(StoreException(ErrorStatusCode.Unauthorized, ErrorCode.BadRequest_LcToken))
            }
          }
          case None => {
            Logger.info("invalid token: parsing error." + System.lineSeparator() + JWToken)
            Failure(StoreException(ErrorStatusCode.Unauthorized, ErrorCode.BadRequest_LcToken))
          }
        }
      }
      case _ => {
        Logger.info("invalid token: parsing error." + System.lineSeparator() + JWToken)
        Failure(StoreException(ErrorStatusCode.Unauthorized, ErrorCode.BadRequest_LcToken))
      }
    }
  }
}
