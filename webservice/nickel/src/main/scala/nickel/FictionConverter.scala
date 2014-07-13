package nickel

import java.util.regex.Pattern

import com.google.appengine.repackaged.org.joda.time.format.DateTimeFormat
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.logging.Logger

import scala.collection.JavaConverters._

object Env {
  val logger = Logger.getLogger("Nickel")
}

case class ParsingException(message: String) extends Exception

trait FictionLoader {
  def canHandleUrl(url: String) : Boolean
  def getChapterList(html: String) : List[Chapter]
}

class FanFictionNetLoader extends FictionLoader {
  val authorXPath = "div[id*=profile_top]>a[href^=/u]"
  val storyTitleXPath = "div[id*=profile_top]>b"
  val storyDescriptionXPath = "div[id*=profile_top]>div"
  val chapterSelectorXPath = "select[id*=chap_select]"
  val baseUrl = "https://www.fanfiction.net"

  val chapterOnChangePattern = Pattern.compile("self.location = '(/s/[0-9]+/)'.+\\+ '(/.+)';")
  val dateParser = DateTimeFormat.forPattern("MM/dd/YYYY")

  override def canHandleUrl(url: String): Boolean = url.contains("fanfiction.net")

  override def getChapterList(html: String): List[Chapter] = {
    val soup = Jsoup.parse(html)
    extractChapters(soup)
  }

  def extractChapters(soup: Document) : List[Chapter] = {
    val select = soup.select(chapterSelectorXPath).first()
    val options = select.getElementsByTag("option").asScala
    val onchange = select.attr("onchange")

    Env.logger.info(onchange)

    val matcher = chapterOnChangePattern.matcher(onchange)
    if (matcher.matches()) {
      val locFirstPart = matcher.group(1)
      val locSecondPart = matcher.group(2)

      options.map(x => new Chapter(
        baseUrl + locFirstPart + x.attr("value") + locSecondPart,
        x.text())).toList
    } else {
      throw new ParsingException("Couldn't parse onChange attribute " + onchange)
    }
  }
}

object FictionConverter {
  def detectFictionType(url: String): Unit = {

  }
}
