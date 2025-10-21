
@echo off

set JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF-8

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

echo Java found: 
java -version
echo.

if not exist "target\" (
    echo ERROR: target directory not found!
    echo Please build the project first using: mvn clean package
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
        echo Starting application...
        echo.
        start javaw -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -jar "%%F"
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
echo Application started successfully!
echo You can close this window.
timeout /t 3 >nul
exit