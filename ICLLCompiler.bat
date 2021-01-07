@echo off

set filename=%1

java -ea -cp bin main/Compiler %filename%