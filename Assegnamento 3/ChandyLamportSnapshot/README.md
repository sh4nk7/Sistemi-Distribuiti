# 🧠 Chandy-Lamport Snapshot Simulation – Java

> **Studente:** Giuseppe Dimonte  
> **Matricola:** 367431  
> **Corso:** Laurea Magistrale in Ingegneria Informatica  
> **Insegnamento:** Sistemi Distribuiti (9 CFU)  
> **Assegnamento 3**

---

## 📌 Obiettivo

Sviluppare un sistema distribuito simulato in Java per la **realizzazione di uno stato globale** utilizzando l’**algoritmo di Chandy-Lamport**. Il sistema coinvolge **almeno cinque nodi** interconnessi, ognuno con un proprio stato locale e capacità di comunicazione tramite messaggi `TASK` e `MARKER`.

---

## 🧱 Architettura del Sistema

- **5 nodi** rappresentati da thread Java (`Node`)
- Comunicazione **asimmetrica e non bloccante** tramite `BlockingQueue`
- Messaggi gestiti tramite oggetti `Message` (tipo `TASK` o `MARKER`)
- Snapshot distribuito avviato da un nodo e propagato tramite `MARKER`

### 🔄 Diagramma testuale

+--------+ +--------+ +--------+ | Node 0 |<-----> | Node 1 |<-----> | Node 2 | +--------+ +--------+ +--------+ ^ \ ^ \ ^ \ \ \ \
\ \ \ \
v v v v v +--------+ +--------+ +--------+ | Node 3 |<-----> | Node 4 |<-------+ ... +--------+ +--------+


---

## 🔍 Funzionamento dell'Algoritmo Chandy-Lamport

1. Un nodo avvia lo snapshot inviando `MARKER` a tutti i suoi vicini.
2. Alla **prima ricezione** di un `MARKER`, un nodo:
   - Registra il proprio **stato locale**
   - Invia `MARKER` a tutti i vicini
3. I messaggi `TASK` ricevuti **prima del `MARKER`** vengono gestiti normalmente.
4. I messaggi `TASK` ricevuti **dopo il `MARKER` ma prima degli altri `MARKER`** vengono salvati come **messaggi in transito**.

---

## 📂 File e Classi

| File | Descrizione |
|------|-------------|
| `Main.java` | Crea i 5 nodi, li collega e simula la comunicazione. Avvia lo snapshot da nodo 0. |
| `Node.java` | Rappresenta un nodo. Gestisce lo snapshot e la ricezione dei messaggi. |
| `Message.java` | Definisce la struttura dei messaggi `TASK` e `MARKER`. |

---

## ▶️ Come compilare ed eseguire

### ✅ Compilazione

```bash
javac --release 17 -d . src/*.java
