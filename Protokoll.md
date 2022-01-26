# MTCG
Swen project

# Protokoll



## Designs

Am Anfang des Projekts bin ich das Programmieren mit einer "C/C++" Denkweise angegangen. Davor habe ich mich nicht viel mit Java beschäftigt, 
jedoch habe ich während dem Prozess das "SOLID" Prinzip angewendet und mich mehr über Java informiert. Die wichtigsten Komponenten sind in eigenen
Packages, diese haben Klassen drinnen, die maximal zwei Schnittstellen mit anderen Komponenten haben. Dies ist erforderlich um das SOLID Prinzip einzuhalten.

![image](https://user-images.githubusercontent.com/71192659/151152949-24f92b6b-9920-4291-93d0-77ee00806bbf.png)


### Design Pattern
Für die Klassen DBconnection und Response habe ich das Design Pattern "Singleton" verwendet. Da es nur einmal notwendig ist sich zur Datenbank zu verbinden,
ist es nicht nötig mehrmals neue Objekte dieser Instanz zu erstellen, sondern eines genügt. Für die Response wird für den PrintWriter ein Socket benötigt. 
Um nicht jedesmal den Socket zu übergeben, erstelle ich nur eine Instanz und rufe diese ab.

### Failures and selected solutions
Die größten Probleme auf die ich zugestoßen bin, waren die Battle Logik und das Handlen von Datenbankverbindungen. Bei der Battle Logik fiel es mir schwer
die beiden Threads, die mit "start \b" gestartet werden, asynchron zu machen, um eine "Queue" für das Matchmaking zu kreiern. Die Lösung dafür ist ein
Thread Pool als "newSingleThreadedExecutor" zu instanzieren, damit jeder Thread sozusagen nacheinanderläuft/asynchron. 
Oft hat mir das Program eine Exception mit der Nachricht "FATAL sorry too many Clients" geprintet.Anfangs habe ich die Sql statements und Resultsets geschlossen ohne
dabei die Verbindung zu schließen. Dies führte dann zur Auslastung der Verbindungskapazität der Datenbank, welches weitere Verbindungen verhindert.
![image](https://user-images.githubusercontent.com/71192659/151153131-f7ce4fba-067a-4c10-a266-0d51920fcc8e.png)


## Tests
Die meisten Tests überprüfen, ob korrekte Parameter übergeben werden oder ob die geteste Funktion ihre Aufgabe richtig erfüllt. Beispielsweise wird im Battletest
getestet ob die Berechnungen mit Damage korrekt sind je nach Bedinungen. Weiteres werden Tests gemacht um zu überprüfen ob der User zB sich nicht mit einem vorhanden
Username registrieren kann oder einloggen kann wenn es den User noch nicht in der Datenbank gibt.


## Time spent
Das Projekt hab ich ganz am Anfang gestartet und Stück für Stück daran weitergearbeitet. Allerdings habe ich 80% des Projekts Anfang Jänner gemacht, da ich ganz ehrlich 
meine Zeit nicht richtig gemanaget hab. Viel Zeit hab ich ans Recherieren investiert, weil ich noch nie einen Http Server davor gemacht habe und viele Funktionen und Libraries 
in Java nicht kannte.


## Link to Git
https://github.com/lobna99/MTCG
