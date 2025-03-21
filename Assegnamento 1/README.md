# 🚀 Sistema di Comunicazione Multicast in Java

## 📌 Descrizione
Questo progetto implementa un **sistema distribuito client-server** basato su **socket e comunicazione multicast**. 
Ogni nodo può inviare e ricevere messaggi contenenti l'ID del nodo mittente e l'ID del messaggio. Inoltre, i nodi rilevano e segnalano la perdita di messaggi, richiedendone il reinvio.

## 📂 Struttura del Progetto
```
MulticastSystem/
│── src/
│   ├── server/
│   │   ├── Server.java
│   ├── node/
│   │   ├── Node.java
│   ├── utils/
│   │   ├── Message.java
│   │   ├── MulticastManager.java
│── README.md
│── run.sh
│── run.bat
│── pom.xml (se usiamo Maven)
│── build.gradle (se usiamo Gradle)
```

## 🛠️ Requisiti
🔹 **Java 8 o superiore**  
🔹 **Ambiente di sviluppo consigliato**: [VS Code](https://code.visualstudio.com/) con estensione "Extension Pack for Java"

## ▶️ Compilazione ed Esecuzione
### 🔧 Compilazione
```sh
javac -d out src/**/*.java
```

### 🚀 Avvio del Server
```sh
java -cp out server.Server
```

### 📡 Avvio dei Nodi
Esempio di avvio di due nodi:
```sh
java -cp out node.Node 1
java -cp out node.Node 2
```

## 🔄 Funzionamento
1. 🔹 Il **server** avvia il sistema e coordina i nodi.
2. 🔹 I **nodi** inviano messaggi multicast incrementando l'ID del messaggio.
3. 🔹 Se un nodo rileva la perdita di un messaggio, **ne richiede il reinvio**.
4. 🔹 Il server monitora i nodi e chiude il sistema quando tutti hanno completato (ID messaggi = 100).

## 🖥️ Demo e Test
Puoi eseguire il sistema localmente avviando più terminali per simulare i nodi.
Esempio:
```sh
java -cp out node.Node 1 &
java -cp out node.Node 2 &
java -cp out node.Node 3 &
```

## 👨‍💻 Autore
📌 **Nome:** Giuseppe Dimonte  
📌 **Email:** giuseppe.dimonte@studenti.unipr.it  
📌 **Progetto sviluppato come parte di un esercizio di programmazione distribuita in Java.**

---
💡 *Se questo progetto ti è stato utile, lascia un ⭐ su GitHub!*
