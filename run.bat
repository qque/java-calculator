
@echo off

echo.
echo ========================================
echo Starting Application Launcher
echo ========================================
echo.

where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
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

for %%a in (%*) do (
    set "arg=%%a"
    if /i "!arg:~0,2!"=="-D" (
        set "JAVA_OPTS=!JAVA_OPTS! %%a"
        
        set "setting_pair=!arg:~2!"
        for /f "tokens=1* delims==" %%b in ("!setting_pair!") do (
            set "setting_name=%%b"
            set "setting_value=%%c"

            set "!setting_name!=!setting_value!"
            echo   Captured: !setting_name!=!setting_value!
        )
    )
)

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

if %JAR_FOUND%==0 (
    echo ERROR: No JAR file found!
    echo Files in target directory:
    dir target\*.jar
    pause
    exit /b 1
)

:done
timeout /t 3 >nul
exit