# Access Control Models: Matrix, ACLs, and Capability Lists

A Java simulation of three classic operating-system protection models, enforced by a single reference monitor against concurrent processes. Built as a team project for CMPS 455 (Operating Systems) at the University of Louisiana at Lafayette with Diego and Charlie.

## What it demonstrates

Every access-control system answers the same question: *may this subject perform this operation on this object?* This project implements three different ways of storing the answer and runs the same workload against each:

| Model | Stored as | Real-world analogue |
| --- | --- | --- |
| **Access matrix** (Task 1) | One table: domains × (files + domains) | The theoretical model the other two are derived from |
| **Access control lists** (Task 2) | Per object: which domains may access it | Unix/Windows file permissions |
| **Capability lists** (Task 3) | Per domain: what it is allowed to access | Tokens, keys, and handles a process holds |

## How it works

* **Domains** run as concurrent threads. Each one randomly attempts to read a file, write a file, or switch into another protection domain.
* **The Arbitrator** acts as the reference monitor. Every request passes through it, and it grants or denies based on whichever model is loaded. Denied requests are logged and never reach the file.
* **Domain switching** is itself access-controlled. A domain can only escalate into another domain if the model explicitly allows it, which models privilege transitions.
* **Files** are guarded by semaphores, so permitted reads and writes stay mutually exclusive under concurrency.
* Permissions (R, W, R/W, or none) and switch rights are randomized each run, with 3–7 domains and 3–7 files.

## Running it

```bash
javac -d out src/com/company/*.java
java -cp out com.company.Main -S 1   # access matrix
java -cp out com.company.Main -S 2   # access control lists
java -cp out com.company.Main -S 3   # capability lists
```

Each run prints the generated permission structure, then a log of every access attempt and switch request with the Arbitrator's GRANTED or DENIED decision.

## Concepts practiced

Access control models, reference monitor design, least privilege, privilege transitions between protection domains, thread concurrency, and semaphore-based mutual exclusion in Java.
