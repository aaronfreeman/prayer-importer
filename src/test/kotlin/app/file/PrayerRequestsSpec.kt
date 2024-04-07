package app.file

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PrayerRequestsSpec : StringSpec({
    "reading file" {
        val result = PrayerRequests("src/test/resources/requests.xlsx").items

        result.size shouldBe 3
        result.first().one shouldBe "Prayer1a"
        result.first().two shouldBe "Prayer1b"
        result.last().one shouldBe "Prayer3a"
        result.last().two shouldBe ""
    }
})