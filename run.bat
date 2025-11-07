@echo off
setlocal enabledelayedexpansion

set "JAVA_OPTS="
set "QUIET=false"
set "BUILD=false"
set "NOCONSOLE=false"
set "CLEAR=false"
set "MINIFY=false"
set "DEBUG=false"
set "TEST=false"
set FILE="..."

set /p JEP_PATH=<%~dp0jep.cfg

:parseargs
if "%~1"=="" goto endparse

set "arg=%~1"

if /i "!arg:~0,2!"=="-f" (
    shift
    set FILE="%2"
    goto parseargs
)

if /i "!arg:~0,2!"=="-D" (
    shift
    set "JAVA_OPTS=%1=%2 !JAVA_OPTS!"
    goto parseargs
)

if "!arg:~0,1!"=="-" (

    if /i "!arg!"=="--quiet" set "QUIET=true"
    if /i "!arg!"=="--build" set "BUILD=true"
    if /i "!arg!"=="--no-console" set "NOCONSOLE=true"
    if /i "!arg!"=="--clear-logs" set "CLEAR=true"
    if /i "!arg!"=="--minify" set "MINIFY=true"
    if /i "!arg!"=="--debug-console" set "DEBUG=true"
    if /i "!arg!"=="--test" set "TEST=true"

    if "!arg:~0,2!" neq "--" (
        
        set "flag_string=!arg:~1!"
        
        echo !flag_string! | findstr /i "\<q\>" >nul && set "QUIET=true"
        echo !flag_string! | findstr /i "\<b\>" >nul && set "BUILD=true"
        echo !flag_string! | findstr /i "\<w\>" >nul && set "NOCONSOLE=true"
        echo !flag_string! | findstr /i "\<c\>" >nul && set "CLEAR=true"
        echo !flag_string! | findstr /i "\<m\>" >nul && set "MINIFY=true"
        echo !flag_string! | findstr /i "\<d\>" >nul && set "DEBUG=true"
        echo !flag_string! | findstr /i "\<t\>" >nul && set "TEST=true"
    )
    shift
    goto parseargs
)
shift
goto parseargs

:endparse
if %FILE% neq "..." (
    set JAVA_OPTS="-Dcustom_debug_file=%FILE:"=% %JAVA_OPTS%"
)

if %CLEAR%==true (
    python ./util/clear_logs.py
)

if %MINIFY%==true (
    python ./util/minify.py
)

if %BUILD%==true (
    if %QUIET%==true (
        call build.bat -q
    ) else (
        call build.bat
    )
)

if %QUIET%==true (
    goto qrun
)

echo.
echo ========================================
echo Starting Application Launcher
echo ========================================
echo.

where java >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java JDK
    pause
    exit /b 1
)

java -version
echo.

if not exist "target\" (
    echo ERROR: target directory not found!
    echo Please build the project first using ./build
    pause
    exit /b 1
)

echo Looking for JAR files in target directory...
dir /b target\*.jar
echo.

set JAR_FOUND=0

for %%F in (target\*.jar) do (
    echo Checking: %%F
    echo %%F | find /i "original-" >nul
    if errorlevel 1 (
        set JAR_FOUND=1
        echo Found application JAR: %%F
        echo.
        echo Starting application...
        echo.
        if %NOCONSOLE%==false (
            start java -Xms256M -Xmx1536M -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F" %JAVA_OPTS%
        ) else (
            start javaw -Xms256M -Xmx1536M -Ddebug=false -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F" %JAVA_OPTS%
        )
        goto done
    )
)

if %JAR_FOUND%==0 (
    echo ERROR: No JAR file found!
    echo Files in target directory:
    dir target\*.jar
    exit /b 1
)

:qrun
set JAR_FOUND=0
for %%F in (target\*.jar) do (
    echo %%F | find /i "original-" >nul
    if errorlevel 1 (
        set JAR_FOUND=1
        if %NOCONSOLE%==false (
            start java -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F" %JAVA_OPTS%
        ) else (
            start javaw -Ddebug=false -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F" %JAVA_OPTS%
        )
        goto done
    )
)

if %JAR_FOUND%==0 (
    echo ERROR: No JAR file found!
    echo Files in target directory:
    dir target\*.jar
    exit /b 1
)

:done
exit