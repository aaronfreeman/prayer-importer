package app.ui

import app.file.PrayerRequests
import app.file.Presentation
import javafx.application.Application
import javafx.geometry.Insets
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.layout.VBox
import javafx.stage.Stage

class App : Application() {
    override fun start(primaryStage: Stage) {
        val root = VBox()
        root.spacing = 20.0
        root.padding = Insets(10.0, 10.0, 10.0, 10.0)
        root.style = "-fx-background-color: #EEEEEE"

        val defaults = Defaults().load()

        val excelSelect = FileSelect(primaryStage, "Prayer Excel File", "xlsx", defaults.excelFile)
        root.children.add(excelSelect.element)

        val powerPointSelect = FileSelect(primaryStage, "Source PowerPoint File", "pptx", defaults.powerpointFile)
        root.children.add(powerPointSelect.element)

        val outputDir = FileSelect(primaryStage, "Output Directory", "dir", defaults.outputDir)
        root.children.add(outputDir.element)

        val out = OutputFile(defaults.fileName)
        root.children.add(out.element)

        val submit = Button("Submit")
        root.children.add(submit)

        submit.setOnAction {
            val prayerRequests = PrayerRequests(excelSelect.path)
            val outFile = "${outputDir.path}/${out.name}.pptx"
            val presentation = Presentation(powerPointSelect.path, outFile)
            presentation.populate(prayerRequests.items)

            defaults.excelFile = excelSelect.path
            defaults.powerpointFile = powerPointSelect.path
            defaults.outputDir = outputDir.path
            defaults.fileName = out.name
            defaults.save()

            primaryStage.close()
            val alert = Alert(Alert.AlertType.INFORMATION)
            alert.title = "Complete"
            alert.headerText = null
            alert.contentText = "The presentation file was created at: $outFile"
            alert.showAndWait()
        }

        val scene = Scene(root, 650.0, 320.0)

        with(primaryStage) {
            title = "Prayer Request Transfer"
            this.scene = scene
            show()
        }
    }
}

fun main() {
    Application.launch(App::class.java)
}
