# legendary-invention
Platforme de gestion de plugins

Structure du projet   
* plateform : plateforme à plugins
* application: application utilisant la plateforme
* extensions : dossier contenant toutes les extensions disponibles

## Pré-requis d'installation

Les dépendances du projet sont : 
* **Java JRE** version 1.7 ou supérieure
* **javac** version 1.7 ou supérieure 
* **jar** version 1.7 ou supérieure

## Installation

A la racine du projet, veuillez lancer :
* Pour compiler tous le projet (Plateforme + application + extensions)
```bash
./install.sh
```
* Pour compiler seulement la platforme
```bash
./install.sh -p
```

* Pour compiler seulement l'application
```bash
./install.sh -a
```

* Pour compiler seulement les extensions
```bash
./install.sh -e
```

* Pour plus d'aide
```bash
./install.sh -h
```

## Lancement du projet
A la racine du projet, veuillez lancer la commande suivante :
```bash
./run.sh
```

## Pour créer une nouvelle extension
```bash
./make-extension -n <extension_name> -c <classname>
```
Pour plus d'informations à ce sujet, merci de vous référez à la documentation développeur fournie en annexe.
