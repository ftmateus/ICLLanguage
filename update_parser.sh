#!/bin/bash

cd src/parser

javacc Parser.jj

cd ../..

sh ./compile_project.sh