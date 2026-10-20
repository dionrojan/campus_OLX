@echo off
REM ==============================================================================
REM Windows Compile Script for Campus OLX
REM ==============================================================================

echo [Campus OLX] Compiling Java project on Windows...

if not exist bin mkdir bin

javac -cp "lib/*" -d bin src\com\campusolx\*.java

if %ERRORLEVEL% EQU 0 (
    echo [Campus OLX] Compilation successful! Compiled classes placed in 'bin/'.
) else (
    echo [Campus OLX] ❌ Compilation failed with errors.
)
