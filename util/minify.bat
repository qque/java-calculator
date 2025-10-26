@echo off

pip show python-minifier >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo python-minifier package not found, attempting to install
    echo.
    pip install python-minifier
)

python ./util/minify.py

exit