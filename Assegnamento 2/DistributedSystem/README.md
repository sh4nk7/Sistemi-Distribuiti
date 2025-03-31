# 🚀 Sistema di Elezione e Mutua Esclusione in Java

## 📌 Descrizione
Questo progetto implementa un **sistema distribuito** in Java che simula un insieme di nodi cooperanti per la gestione di una risorsa condivisa.  
Il sistema è basato su:
- **Mutua esclusione centralizzata**
- **Algoritmo di elezione** (Bully Algorithm)
- **Rilevamento di guasto del coordinatore**

Ogni nodo può acquisire fino a **10 risorse**, previa autorizzazione del **coordinatore**. Se il coordinatore fallisce, viene avviata un'elezione per eleggere un nuovo coordinatore.

## 📂 Struttura del Progetto
```
DistributedSystem/
│── src/
│   ├── distributed/
│   │   ├── Main.java
│   │   ├── Node.java
│   │   ├── Message.java
│   │   ├── MessageType.java
│   │   ├── SimulationLogger.java
│── README.md
```

## 🛠️ Requisiti
🔹 **Java 8 o superiore**  
🔹 **Ambiente di sviluppo consigliato**: [VS Code](https://code.visualstudio.com/) con estensione "Extension Pack for Java"

## ▶️ Compilazione ed Esecuzione (Windows)
### 🔧 Compilazione ed esecuzione automatica
Utilizza il file `run.bat` per compilare ed eseguire il programma:
```bat
@echo off

REM  Creazione cartella di output solo se non esiste
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
```

## 🔄 Funzionamento
1. 🔹 Il nodo con ID più alto è inizialmente il **coordinatore**.
2. 🔹 I nodi richiedono la risorsa una alla volta tramite il coordinatore (**mutua esclusione**).
3. 🔹 Se il coordinatore non risponde, viene avviato il **bully algorithm** per eleggere un nuovo coordinatore.
4. 🔹 Ogni nodo termina quando ha acquisito 10 risorse.
5. 🔹 Il sistema termina quando tutti i nodi hanno completato.

## 💻 Demo e Test
Puoi eseguire la simulazione da terminale. I log mostreranno in tempo reale:
- Richieste di risorsa
- Concessioni
- Elezioni
- Cambio del coordinatore
- Completamento attività dei nodi

## 👨‍💻 Autore
📌 **Nome:** Giuseppe Dimonte  
📌 **Email:** giuseppe.dimonte@studenti.unipr.it  
📌 **Progetto sviluppato come parte di un esercizio di programmazione distribuita in Java.**

---
💡 *Se questo progetto ti è stato utile, lascia un ⭐ su GitHub!*
