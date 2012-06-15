# README zum RYLC-Backend (Mobile Computing) #

*   Voraussetzungen:
    *   Java Development Kit 1.6 oder neuer.
    *   Apache Maven 3.0.4 oder neuer.
*   Bauen des Projekts inkl. Integrationstests: `mvn clean verify -Pintegration`.
    Dabei werden H2 als Stand-Alone-Prozess und Jetty gestartet und nach Durchführen der Tests wieder beendet.
*   Das Backend verwendet die [H2 Database Engine](http://www.h2database.com/html/main.html).
    Zur Entwicklung wird H2 als Stand-Alone-Prozess benutzt, "in Produktion" als In-Memory-Datenbank.
    Zum Starten der Datenbank `./start-db.sh` bzw. `./start-db.cmd` verwenden.
