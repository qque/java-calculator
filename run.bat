@echo off

set "JAVA_OPTS="
set "QUIET=false"
set "BUILD=false"

set "i=0"
for %%a in (%*) do (
    set "arg=%%a"
    if /i "!arg:~0,2!"=="-D" (
        set "JAVA_OPTS=!JAVA_OPTS! %%a"
        
        set "setting_pair=!arg:~2!"
        for /f "tokens=1* delims==" %%b in ("!setting_pair!") do (
            set "setting_name=%%b"
            set "setting_value=%%c"

            set "!setting_name!=!setting_value!"
        )
    )
    set "flag_string=!arg:~1!"
    if "!flag_string:q=!" neq "!flag_string!" (
        set "QUIET=true"
    )
    if /i "!arg!"=="--quiet" (
        set "QUIET=true"
    )
    if /i "!arg!"=="-b" (
        set "BUILD=true"
        call build.bat -q
    )
    if /i "!arg!"=="--build" (
        set "BUILD=true"
        call build.bat -q
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
        echo Found application JAR: %%F
        echo.
        echo Starting application...
        echo.
        start javaw %JAVA_OPTS% -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -jar "%%F"
        set JAR_FOUND=1
        goto :done
    )
)

:qrun
set JAR_FOUND=0
for %%F in (target\*.jar) do (
    echo %%F | find /i "original-" >nul
    if errorlevel 1 (
        start javaw %JAVA_OPTS% -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -jar "%%F"
        set JAR_FOUND=1
    )
)

if %JAR_FOUND%==0 (
    echo ERROR: No JAR file found!
    echo Files in target directory:
    dir target\*.jar
    exit /b 1
)

:done
timeout /t 3 >nul
exit