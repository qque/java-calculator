@echo off

set MAVEN_OPTS=-Xmx1024m
set QUIET=false
set ERRORLEVEL=0

if /i "%1"=="-q" (
    set QUIET=true
    goto qrun
)
if /i "%1"=="--quiet" (
    set QUIET=true
    goto qrun
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

echo Starting Maven build...
echo.
call mvn clean install
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
call mvn clean install -q
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