@echo off

set MAVEN_OPTS=-Xmx1024m
set QUIET=false
set ERRORLEVEL=0

if /i "%1"=="-q" (
    set QUIET=true
    goto run
)
if /i "%1"=="--quiet" (
    set QUIET=true
    goto run
)


echo.
echo ========================================
echo Maven Project Build Script
echo ========================================
echo.

where mvn >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven and add it to your PATH
    pause
    exit /b 1
)

echo Checking Maven version...
call mvn --version
echo.

:build
echo Starting Maven build...
echo.

call mvn clean install

:run
if %QUIET%==true (
    call mvn clean install -q
)

:check_result
if %ERRORLEVEL%==1 (
    echo.
    echo Build failed, exiting with error level %ERRORLEVEL%
    echo.
    exit /b %ERRORLEVEL%
)

:end
exit /b 0