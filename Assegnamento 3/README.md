🚀 Sistema di Snapshot Globale Distribuito – Algoritmo di Chandy-Lamport in Java

📌 Descrizione
Questo progetto implementa un sistema distribuito simulato in Java per la realizzazione di uno **stato globale consistente** utilizzando l’**algoritmo di Chandy-Lamport**.  
Ogni nodo comunica con gli altri tramite messaggi asincroni (`TASK` e `MARKER`) e partecipa a uno snapshot distribuito.  
Il sistema è pensato per scopi didattici e di simulazione di ambienti reali asincroni.

📂 Struttura del Progetto
ChandyLamportSnapshot/ │── src/ │ ├── Main.java │ ├── Message.java │ ├── Node.java │── README.md


🛠️ Requisiti
🔹 Java 17 o superiore  
🔹 Ambiente consigliato: VS Code, IntelliJ, o terminale con `javac`

---

▶️ Compilazione ed Esecuzione

🔧 Compilazione
```bash
javac --release 17 -d . src/*.java

🚀 Avvio del Sistema

java src.Main

🔄 Funzionamento

🔹 Ogni nodo (Node.java) è rappresentato da un thread indipendente.
🔹 I nodi comunicano tra loro tramite code (BlockingQueue) che simulano i canali di rete.
🔹 Il nodo 0 avvia lo snapshot Chandy-Lamport, inviando un MARKER a tutti.
🔹 I nodi salvano il proprio stato locale al primo MARKER ricevuto.
🔹 I messaggi TASK ricevuti prima della ricezione completa dei MARKER sono salvati come messaggi in transito.
🔹 Alla fine, ogni nodo stampa il proprio stato e lo stato dei canali in ingresso.

📌 Output di esempio

=== Inizio snapshot da nodo 0 ===

Node 0 starts snapshot.
Node 1 received first MARKER from Node 0
Node 1 starts snapshot.
Node 2 received first MARKER from Node 0
Node 2 starts snapshot.

=== Stato snapshot ===

Node 0 local state: Snapshot taken at Node 0
Node 0 channel state (messages in transit):
  [TASK] from Node 3: POST-SNAPSHOT TASK from 3

Node 1 local state: Snapshot taken at Node 1
Node 1 channel state (messages in transit):

...


📦 Classi principali

Classe	Descrizione
Main.java	Avvia il sistema, crea i nodi e gestisce lo snapshot.
Node.java	Ogni nodo partecipa allo snapshot e gestisce i messaggi.
Message.java	Definisce i messaggi di tipo TASK o MARKER.


👨‍💻 Autore

📌 Nome: Giuseppe Dimonte
📌 Matricola: 367431
📌 Email: giuseppe.dimonte@studenti.unipr.it
📌 Progetto sviluppato come parte dell’assegnamento 3 del corso di Sistemi Distribuiti – 9 CFU, Università di Parma

💡 Se questo progetto ti è stato utile o vuoi espanderlo, sentiti libero di clonarlo, migliorarlo o ⭐ lasciargli una stella su GitHub!
