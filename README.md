# Spring Boot Basics: Traineeship Technical Recap

Questo repository contiene un'applicazione **Java** basata sul **Framework Spring**, realizzata come progetto di consolidamento durante il mio tirocinio formativo. Il software funge da riepilogo pratico dei concetti fondamentali di Spring Boot, applicando un'architettura robusta e scalabile.

## Panoramica del Progetto

L'applicazione sfrutta l'ecosistema **Spring Boot** per semplificare la creazione e la distribuzione di servizi Java. Grazie all'infrastruttura di Spring, il progetto gestisce in modo automatizzato aspetti complessi come la configurazione e la gestione delle dipendenze.

---

## Architettura a Tre Livelli

L'applicazione segue il principio della **separazione delle responsabilità**, suddividendo la logica in tre livelli distinti per migliorare la manutenibilità e il testing:

1.  **Controller Layer**: Gestisce le richieste HTTP in ingresso e restituisce le risposte appropriate all'utente.
    *   Annotazioni utilizzate: `@Controller`, `@RestController`, `@GetMapping`.
2.  **Service Layer**: Contiene la logica di business dell'applicazione.
    *   Annotazione utilizzata: `@Service`.
3.  **Repository Layer**: Responsabile dell'accesso ai dati e delle operazioni **CRUD** (Create, Read, Update, Delete) sul database.
    *   Annotazione utilizzata: `@Repository`.

---

## Concetti Tecnici Implementati

### Inversion of Control (IoC) & Dependency Injection (DI)
Il **Contenitore Spring** (Contenitore IoC) gestisce interamente il ciclo di vita degli oggetti dell'applicazione, definiti **Bean**.
*   Spring si occupa di creare, configurare e collegare automaticamente i componenti necessari, riducendo l'accoppiamento tra le classi.
*   **Scope dei Bean**: È stato implementato lo scope predefinito **Singleton** (istanza unica condivisa), con supporto alla configurazione **Prototype** (nuova istanza per ogni richiesta) tramite l'annotazione `@Scope`.

### Gestione Dinamica delle Richieste (Spring MVC)
Sono state implementate tecniche avanzate per l'acquisizione dei dati dall'URL:
*   **@RequestParam**: Utilizzato per estrarre parametri di ricerca dalle query string (es. `/find?name=Alex`).
*   **@PathVariable**: Utilizzato per gestire valori dinamici direttamente nel percorso dell'URL (es. `/user/{id}`).

---

## Come Eseguire l'Applicazione

1.  Assicurati di avere **Java 17+** e **Maven** installati.
2.  Clona il repository.
3.  Importa il progetto nel tuo IDE (consigliato **IntelliJ IDEA Ultimate**).
4.  Esegui la classe `FirstSpringBootAppApplication`.
5.  Accedi a `localhost:5432` (o alla porta specificata nel file `application.properties`).

---
*Progetto tecnico basato sui fondamenti del corso Udemy, completato nell'ambito del mio percorso di tirocinio.*
