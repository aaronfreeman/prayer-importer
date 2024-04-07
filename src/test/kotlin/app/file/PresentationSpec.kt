package app.file

import io.kotest.core.spec.style.StringSpec

class PresentationSpec : StringSpec({
    "create presentation" {
        val presentation = Presentation("src/test/resources/presentation.pptx", "src/test/resources/out.pptx")

        presentation.populate(PrayerRequests("src/test/resources/requests.xlsx").items)
    }
})