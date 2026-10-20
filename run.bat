@echo off
REM ==============================================================================
REM Windows Run Script for Campus OLX
REM ==============================================================================

if not exist bin (
    echo [Campus OLX] 'bin/' directory not found. Compiling first...
    call compile.bat
)

REM In Windows, classpath uses semicolon (;) instead of colon (:)
java -cp "bin;lib/*" com.campusolx.Main
