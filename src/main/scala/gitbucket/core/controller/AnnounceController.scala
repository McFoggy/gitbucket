package gitbucket.core.controller

import gitbucket.core.admin.html
import gitbucket.core.util.AdminAuthenticator
import jp.sf.amateras.scalatra.forms._
import org.slf4j.LoggerFactory

/**
 * Created by awxgx on 30/07/2015.
 */
class AnnounceController extends AnnounceControllerBase
  with AdminAuthenticator

trait AnnounceControllerBase extends ControllerBase {
  self:  AdminAuthenticator =>

  private val logger = LoggerFactory.getLogger(classOf[AnnounceController])

  case class AnnounceForm(content: String)

  private val announceForm = mapping(
    "content" -> trim(label("Announce", text(required)))
  )(AnnounceForm.apply)

  get("/admin/announce")(adminOnly {
    html.announce(flash.get("info"))
  })

  post("/admin/announce", announceForm)(adminOnly { form =>
    flash += "info" -> "Announce has been sent."

    if (logger.isDebugEnabled) {
      logger.debug("sending announce: {}", form.content)
    }

    redirect("/admin/announce")
  })
}