package app.ui

import javafx.geometry.Insets
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.FileChooser.ExtensionFilter
import javafx.stage.Stage
import java.io.File

class FileSelect(primaryStage: Stage, label: String, extension: String, default: String) {
    private val fileChooser = FileChooser()
    private val directoryChooser = DirectoryChooser()
    private val selected = Label(default)
    val element = VBox()

    val path: String get() = selected.text

    init {
        val labelControl = Label(label)
        element.children.add(labelControl)
        labelControl.style = "-fx-font-weight: bold; -fx-font-size: 14px;"

        if (default.isNotBlank()) {
            val defaultFile = File(default)
            fileChooser.initialDirectory = if (defaultFile.isDirectory) defaultFile else defaultFile.parentFile
        }

        selected.padding = Insets(3.0, 0.0, 0.0, 10.0)

        val type = if (extension == "dir") "Directory" else "File"
        val button = Button("Choose $type")

        if (extension != "dir") {
            fileChooser.extensionFilters.add(ExtensionFilter("Files", "*.$extension"))
        }

        button.setOnAction {
            when (extension) {
                "dir" -> directoryChooser.showDialog(primaryStage)
                else -> fileChooser.showOpenDialog(primaryStage)
            }?.let {
                selected.text = it.absolutePath
            }
        }

        element.children.add(HBox(button, selected))
    }
}
