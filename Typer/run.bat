@echo off
java --enable-native-access=javafx.graphics --module-path "%~dp0\javafx-sdk-26\lib" --add-modules javafx.controls,javafx.fxml -jar "%~dp0\Typing-MACHINE.jar"
pause