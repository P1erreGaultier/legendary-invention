# legendary-invention
Platforme de gestion de plugins

Structure du projet :    
* plateform : plateforme à plugins
* application: application utilisant la plateforme
* extensions : dossier contenant toutes les extensions disponibles

Pré-requis d'installation
=======

* Java JRE 1.7 ou supérieur

Installation et lancement
=======
*Attention* : en l'état, l'application se lance directement, mais plus tard, elle sera démarée comme une extension par la plateforme

* Naviguez dans le dossier du projet
```
$ cd legendary-invention
```
* Compiler chaque extension du repertoire *extensions* séparément
```
# Par exemple

$ cd extensions/
$ cd extension_1
$ mvn package
# etc pour chaque extension
```
* Naviguez dans le dossier de la plateforme et compilezila
```
$ cd ../plateform/
$ mvn package
```
* Naviguez dans le dossier de l'application, compilez-la et lancez-la
```
$ cd ../application/
$ mvn package
$ java -classpath target/legendary-invention-application-1.0-SNAPSHOT.jar com.alma.application.FirstTry 
```
