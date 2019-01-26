package controllers

import play.api.libs.iteratee.Enumerator
import play.api.mvc.Action
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import services.Services.ReportBuilder
class ReportController extends FilterController {
  def report() = Action {
    Ok.chunked( Enumerator.fromStream(ReportBuilder.toPdf("StoreManagerReport.jrxml") ) )
      .withHeaders(CONTENT_TYPE -> "application/octet-stream")
      .withHeaders(CONTENT_DISPOSITION -> "attachment; filename=report-products.pdf"
      )
      }
}
