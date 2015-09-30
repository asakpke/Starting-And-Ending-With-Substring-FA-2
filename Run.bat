@echo off

set sFile=Automata
set Flash="I:\Data\Softwares\j2sdk1.4.1_01"

SETLOCAL

set path=%path%;c:\j2sdk1.4.1_01\bin;c:\jdk\bin;c:\java\bin;%Flash%\bin
set classpath=%classpath%;c:\j2sdk1.4.1_01\lib;c:\jdk\lib;c:\java\lib;%Flash%\lib;%cd%

echo Running %sFile%
java %sFile%

echo.
pause