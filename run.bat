@echo off
rem Compile and run the Student Management program without Maven
if not exist out mkdir out
javac -d out src\main\java\com\studentmanagement\model\Student.java src\main\java\com\studentmanagement\service\StudentManager.java src\main\java\com\studentmanagement\Main.java
if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)
java -cp out com.studentmanagement.Main
