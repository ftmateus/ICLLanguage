@echo OFF

cd src/parser

call javacc Parser.jj

cd ../..

call compile_project.bat