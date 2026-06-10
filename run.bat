@echo off
cd /d "%~dp0"
echo Building project...
call mvn package -DskipTests -q
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b %errorlevel%
)
echo Starting application...
copy /Y "target\demo-0.0.1.jar" "%TEMP%\app.jar" >nul
start /B "" java -jar "%TEMP%\app.jar"
timeout /t 8 /nobreak >nul
echo.
echo ============================================
echo  App started at http://localhost:8080
echo ============================================
echo  Admin login: admin / admin123
echo  Register:    http://localhost:8080/register
echo  Stop:        taskkill /F /IM java.exe
echo ============================================
echo.
