@echo OFF

cd src/parser

del *.java

call javacc Parser.jj

cd ../..

call compile_project.bat