@echo off
setlocal enabledelayedexpansion

set "MAVEN_OPTS=-Xmx1024m"

set "QUIET=false"
set "CLEAN=false"
set "ONLY=false"
set "COMPILE=false"
set "PACKAGE=false"
set "SKIPTEST=true"

set "ERRORLEVEL=0"

for %%a in (%*) do (
    set "arg=%%a"
    
    if "!arg:~0,1!"=="-" (
        if "!arg:~0,2!" neq "--" (
            set "flag_string=!arg:~1!"
            
            echo !flag_string! | findstr /i "\<q\>" >nul && set "QUIET=true"
            echo !flag_string! | findstr /i "\<c\>" >nul && set "CLEAN=true"
            echo !flag_string! | findstr /i "\<o\>" >nul && set "ONLY=true"
            echo !flag_string! | findstr /i "\<m\>" >nul && set "COMPILE=true"
            echo !flag_string! | findstr /i "\<p\>" >nul && set "PACKAGE=true"
            echo !flag_string! | findstr /i "\<t\>" >nul && set "SKIPTEST=false"
        )
    )
    
    if /i "!arg!"=="--quiet" set "QUIET=true"
    if /i "!arg!"=="--clean" set "CLEAN=true"
    if /i "!arg!"=="--clean-only" set "ONLY=true"
    if /i "!arg!"=="--compile" set "COMPILE=true"
    if /i "!arg!"=="--package" set "PACKAGE=true"
    if /i "!arg!"=="--test" set "SKIPTEST=false"
)

if %QUIET%==true (
    goto qrun
)

echo.
echo ========================================
echo Maven Project Build Script
echo ========================================
echo.

if %ONLY%==true (
    echo. Cleaning build...
    echo.
    call mvn clean
    goto end
)
if %CLEAN%==true (
    echo. Cleaning build...
    echo.
    call mvn clean
)

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

echo Starting Maven build...
echo.
if %COMPILE%==true (
    mvn compile
    goto check
)
if %PACKAGE%==true (
    mvn package "-Dmaven.test.skip=%SKIPTEST%
    goto check
)
mvn install "-Dmaven.test.skip=%SKIPTEST%

:check
if %ERRORLEVEL%==1 (
    echo.
    echo Build failed, exiting with error level %ERRORLEVEL%
    echo.
    exit /b %ERRORLEVEL%
)

echo.
echo Installing python dependencies...
echo.
python -m pip show jep >nul 2>&1
if %ERRORLEVEL%==1 (
    python -m pip install jep
) else (
    python -c "from importlib.metadata import version; print('jep version ' + version('jep'))"
)
python -m pip show numpy >nul 2>&1
if %ERRORLEVEL%==1 (
    python -m pip install numpy
) else (
    python -c "import numpy; print('numpy version ' + numpy.__version__)"
)
python -m pip show sympy >nul 2>&1
if %ERRORLEVEL%==1 (
    python -m pip install sympy
) else (
    python -c "import sympy; print('sympy version ' + sympy.__version__)"
)
python -m pip show mpmath >nul 2>&1
if %ERRORLEVEL%==1 (
    python -m pip install mpmath
) else (
    python -c "import mpmath; print('mpmath version ' + mpmath.__version__)"
)
python -m pip show gmpy2 >nul 2>&1
if %ERRORLEVEL%==1 (
    python -m pip install gmpy2
) else (
    python -c "import gmpy2; print('gmpy2 version ' + gmpy2.__version__)"
)

echo.
echo Minifying python function file...
python ./util/minify.py

echo.
echo Installation complete, exiting...
goto end

:qrun
if %ONLY%==true (
    call mvn clean -q
    goto end
)
if %COMPILE%==true (
    mvn compile -q
    goto check
)
if %PACKAGE%==true (
    mvn package -q "-Dmaven.test.skip=%SKIPTEST%
    goto check
)
if %CLEAN%==true (
    call mvn clean install "-Dmaven.test.skip=%SKIPTEST%" -q
    goto check
) else (
    mvn install "-Dmaven.test.skip=%SKIPTEST%" -q
)
:check
if %ERRORLEVEL%==1 (
    echo.
    echo Build failed, exiting with error level %ERRORLEVEL%
    echo.
    exit /b %ERRORLEVEL%
)
python -m pip show jep >nul 2>&1
if %ERRORLEVEL%==1 (
    yes | python -m pip install jep -q -q --exists-action i
)
python -m pip show numpy >nul 2>&1
if %ERRORLEVEL%==1 (
    yes | python -m pip install numpy -q -q --exists-action i
)
python -m pip show sympy >nul 2>&1
if %ERRORLEVEL%==1 (
    yes | python -m pip install sympy -q -q --exists-action i
)
python -m pip show mpmath >nul 2>&1
if %ERRORLEVEL%==1 (
    yes | python -m pip install mpmath -q -q --exists-action i
)
python -m pip show gmpy2 >nul 2>&1
if %ERRORLEVEL%==1 (
    yes | python -m pip install gmpy2 -q -q --exists-action i
)
python ./util/minify.py -q

:end
for /f "usebackq tokens=*" %%i in (`python -m site --user-site`) do set SITE_PACKAGES=%%i
set JEP_PATH="%SITE_PACKAGES%\jep"

echo %JEP_PATH%>jep.cfg

exit /b 0