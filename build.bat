@echo off

set MAVEN_OPTS=-Xmx1024m
set RUN=false
set QUIET=false
set ERRORLEVEL=0

:parse_args
if "%1"=="" goto build
if /i "%1"=="-r" set RUN=true
if /i "%1"=="--run" set RUN=true
if /i "%1"=="-q" (
    set QUIET=true
    goto run
)
if /i "%1"=="--quiet" (
    set QUIET=true
    goto run
)
if /i "%1"=="clean" goto clean_build
shift
goto parse_args


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

:clean_build
echo Cleaning project...

call mvn clean
goto end

:run
if %QUIET%==true (
    call mvn install -q
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