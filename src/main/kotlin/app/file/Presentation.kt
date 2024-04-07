package app.file

import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xslf.usermodel.XSLFAutoShape
import org.apache.poi.xslf.usermodel.XSLFSlide
import java.io.FileInputStream
import java.io.FileOutputStream

class Presentation(private val sourcePath: String, private val outputPath: String) {
    fun populate(requests: List<PrayerRow>) {
        FileInputStream(sourcePath).use { fis ->
            XMLSlideShow(fis).use { slideShow ->
                createSlides(slideShow, requests.size)
                populateRequests(slideShow, requests)
                write(slideShow)
            }
        }
    }

    private fun write(slideShow: XMLSlideShow) {
        FileOutputStream(outputPath).use { fos ->
            slideShow.write(fos)
        }
    }

    private fun createSlides(slideShow: XMLSlideShow, rowCount: Int) {
        duplicateSlides(slideShow, rowCount - (slideShow.slides.size - 1))
    }

    private fun duplicateSlides(slideShow: XMLSlideShow, count: Int) {
        var i = 0
        while (i++ < count) {
            val slide = slideShow.createSlide()
            slide.importContent(slideShow.slides[0])
            slideShow.setSlideOrder(slide, 0)
        }
    }

    private fun populateRequests(slideShow: XMLSlideShow, requests: List<PrayerRow>) {
        requests.forEachIndexed { index, row -> populateSlide(slideShow.slides[index], row) }
    }

    private fun populateSlide(slide: XSLFSlide, row: PrayerRow) {
        findPrayerElement(slide, PrayerNumber.ONE, row.one)
        findPrayerElement(slide, PrayerNumber.TWO, row.two)
    }

    enum class PrayerNumber { ONE, TWO }

    private fun findPrayerElement(slide: XSLFSlide, num: PrayerNumber, value: String) {
        val textBlock = slide.shapes[0] as XSLFAutoShape
        val paragraphs = textBlock.textBody.paragraphs

        val index = when (num) {
            PrayerNumber.ONE -> 0
            PrayerNumber.TWO -> 2
        }

        paragraphs[index].textRuns[0].text = value
    }
}