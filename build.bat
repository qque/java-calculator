@echo off

echo.
echo ========================================
echo Maven Project Build Script
echo ========================================
echo.

where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven and add it to your PATH
    pause
    exit /b 1
)

echo Checking Maven version...
call mvn --version
echo.

set MAVEN_OPTS=-Xmx1024m
set SKIP_TESTS=true
set RUN=false

:parse_args
if "%1"=="" goto build
if /i "%1"=="-r" set RUN=true
if /i "%1"=="--run" set RUN=true
if /i "%1"=="-t" set SKIP_TESTS=false
if /i "%1"=="--use-tests" set SKIP_TESTS=false
if /i "%1"=="clean" goto clean_build
shift
goto parse_args

:build
echo Starting Maven build...
echo.

if "%SKIP_TESTS%"=="true" (
    echo Building with tests skipped...
    call mvn clean install -DskipTests
) else (
    echo Building with tests...
    call mvn clean install
)

goto check_result

:clean_build
echo Cleaning project...
call mvn clean
goto end



:check_result
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Build successful, running 
    echo.
    echo.
) else (
    echo.
    echo Build failed, exiting with error level %ERRORLEVEL%
    echo.
    exit /b %ERRORLEVEL%
)

echo.
echo 

:end
echo.
exit /b 0