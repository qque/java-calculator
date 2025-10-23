@echo off
setlocal enabledelayedexpansion

echo ================================
echo JavaScript Minifier Script
echo ================================
echo.

echo Checking for npm...
where npm >nul 2>nul
if %errorlevel% neq 0 (
    echo ERROR: npm is not installed or not in PATH.
    echo Please install Node.js from https://nodejs.org/
    echo Node.js includes npm.
    pause
    exit /b 1
)
echo [OK] npm is installed

echo Checking for terser...
where terser >nul 2>nul
if %errorlevel% neq 0 (
    echo terser is not installed globally.
    echo Installing terser globally...
    call npm install -g terser
    if %errorlevel% neq 0 (
        echo ERROR: Failed to install terser.
        pause
        exit /b 1
    )
    echo [OK] terser installed successfully
) else (
    echo [OK] terser is already installed
)

echo.
echo --------------------------------

cd %~dp0\..\src\main\resources

if not exist "functionDefinitions.js" (
    echo ERROR: functionDefinitions.js not found in directory.
    echo Please make sure functionDefinitions.js exists.
    exit /b 1
)

echo Minifying functionDefinitions.js to minified.js...
call terser functionDefinitions.js -o minified.js --compress --mangle

if %errorlevel% neq 0 (
    echo ERROR: Minification failed.
    pause
    exit /b 1
)

echo.
echo ================================
echo [SUCCESS] Minification complete!
echo Output: minified.js
echo ================================
echo.

exit /b 0;