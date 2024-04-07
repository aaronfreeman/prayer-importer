package app.ui

import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class OutputFile(defaultValue: String) {
    private val textField = TextField()

    val element = VBox()
    val name: String get() = textField.text

    init {
        textField.text = defaultValue
        val label = Label("New PowerPoint File Name")
        label.style = "-fx-font-weight: bold; -fx-font-size: 14px;"
        element.children.add(label)
        val extension = Label(".pptx")
        extension.padding = Insets(5.0, 0.0, 0.0, 0.0)
        textField.prefWidth = 200.0
        element.children.add(HBox(textField, extension))
    }
}