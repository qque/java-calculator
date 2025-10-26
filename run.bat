@echo off
setlocal enabledelayedexpansion

set "JAVA_OPTS="
set "QUIET=false"
set "BUILD=false"
set "DEBUG=false"
set "NOCONSOLE=false"

set /p JEP_PATH=<%~dp0jep.cfg

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
    
    if "!arg:~0,1!"=="-" (
        if "!arg:~0,2!" neq "--" (
            if "!arg:~0,2!" neq "-D" (
                set "flag_string=!arg:~1!"
                
                echo !flag_string! | findstr /i "\<q\>" >nul && set "QUIET=true"
                echo !flag_string! | findstr /i "\<b\>" >nul && set "BUILD=true"
                echo !flag_string! | findstr /i "\<d\>" >nul && set "DEBUG=true"
                echo !flag_string! | findstr /i "\<w\>" >nul && set "NOCONSOLE=true"
            )
        )
    )
    
    if /i "!arg!"=="--quiet" set "QUIET=true"
    if /i "!arg!"=="--build" set "BUILD=true"
    if /i "!arg!"=="--debug-console" set "DEBUG=true"
    if /i "!arg!"=="--no-console" set "NOCONSOLE=true"
)

if %BUILD%==true (
    if %QUIET%==true (
        call build.bat -q
    ) else (
        call build.bat
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
        set JAR_FOUND=1
        echo Found application JAR: %%F
        echo.
        echo Starting application...
        echo.
        if %NOCONSOLE%==false (
            start java %JAVA_OPTS% -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F"
        ) else (
            start javaw %JAVA_OPTS% -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F"
        )
        goto :done
    )
)

:qrun
set JAR_FOUND=0
for %%F in (target\*.jar) do (
    echo %%F | find /i "original-" >nul
    if errorlevel 1 (
        set JAR_FOUND=1
        if %NOCONSOLE%==false (
            start java %JAVA_OPTS% -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F"
        ) else (
            start javaw %JAVA_OPTS% -Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8 -Dconsole.encoding=UTF-8 -Djava.library.path="%JEP_PATH%" -jar "%%F"
        )
    )
)

if %JAR_FOUND%==0 (
    echo ERROR: No JAR file found!
    echo Files in target directory:
    dir target\*.jar
    exit /b 1
)

:done
exit