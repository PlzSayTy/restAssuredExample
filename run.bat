@ECHO OFF
IF "%1"=="start" (
    ECHO start 
    start "web-db-0.0.1" java -jar -Dspring.profiles.active=prod web-db-0.0.1-SNAPSHOT.jar
) ELSE IF "%1"=="stop" (
    ECHO stop 
    TASKKILL /FI "WINDOWTITLE eq web-db-0.0.1"
) ELSE (
    ECHO please, use "run.bat start" or "run.bat stop"
)
pause
