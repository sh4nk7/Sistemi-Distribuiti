@echo off

REM Creazione cartella di output solo se non esiste
if not exist out mkdir out

REM  Compilazione per compatibilità con Java 17 (versione 61.0)
javac --release 17 -d out ^
  src\distributed\Main.java ^
  src\distributed\Node.java ^
  src\distributed\Message.java ^
  src\distributed\MessageType.java ^
  src\distributed\SimulationLogger.java

REM  Avvio della simulazione se compilazione riuscita
if %ERRORLEVEL% equ 0 (
    echo  Compilazione completata. Avvio della simulazione...
    java -cp out distributed.Main
) else (
    echo  Errore durante la compilazione.
)
pause
