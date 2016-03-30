# legendary-invention
Platforme de gestion de plugins

Structure du projet :    
* plateform : plateforme à plugins
* application: application utilisant la plateforme
* extensions : dossier contenant toutes les extensions disponibles

## Pré-requis d'installation ##
=======

* Java JRE 1.7 ou supérieur

## Installation ##
=======

A la racine du projet, veuillez lancer :

* Pour compiler tous le projet (Plateforme + application + extensions)
```bash
./install.sh
```

* Pour compiler juste la platforme : 

```bash
./install.sh -p
```

* Pour compiler juste l'application : 

```bash
./install.sh -a
```

* Pour compiler juste les extensions : 

```bash
./install.sh -e
```

* Pour plus d'aide
```bash
./install.sh -h
```

## Lancement ##
=======

* A la racine du projet, veuillez lancer :

```bash
./run.sh
```

## Pour créer une nouvelle extension ##

```bash
./make-extension -n <extension_name> -c <classname>
```