package services.Exception

import play.api.libs.json.JsValue

case class StoreException(status: ErrorStatusCode.exception, errorCode: String, meta: Option[JsValue] = None) extends Exception(errorCode)
