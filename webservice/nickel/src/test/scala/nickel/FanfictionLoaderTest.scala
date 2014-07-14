package nickel

import collection.mutable.Stack
import org.scalatest._

class FanfictionLoaderTest extends FlatSpec with Matchers {

  val doc1 = io.Source.fromInputStream(getClass.getResourceAsStream("/test.html")).mkString

  val expectedChapterNames = List(
    "1. A House of Cards",
    "2. Flawed Assumptions",
    "3. The Summer of Change",
    "4. Of Innuendo and Ice Cream",
    "5. Closets and Secrets",
    "6. Plans are Subject to Change",
    "7. I Belong to Nowhere",
    "8. Censure and Sensibility",
    "9. Bartering the Truth",
    "10. Every Rose has a Thorn",
    "11. Appearances Can Be Revealing",
    "12. What's the Price of Your Fame?",
    "13. Caged Fear",
    "14. Sometimes, It's Good to be Me",
    "15. Code of the Marauders",
    "16. Dancing in the Whirlwind",
    "17. The Dykstra Shuffle",
    "18. A Prayer for Forgiveness",
    "19. Out of Africa",
    "20. The Geist and the Grotto",
    "21. Mind Your Manners",
    "22. Head Games",
    "23. Humiliation and Other Diversionary",
    "24. Cry Havoc")


  "There" should "be 24 chapters" in {
    val loader = new FanFictionNetLoader()
    val chapters = loader.getChapterList(doc1, "")
    chapters.size should be(24)
  }

  "There" should "be following chapters names" in {
    val loader = new FanFictionNetLoader()
    val chapters = loader.getChapterList(doc1, "")

    for (pair <- chapters.map(x => x.name).zip(expectedChapterNames)) {
      pair._1 should be (pair._2)
    }
  }
}
