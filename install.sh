#!/bin/bash
# Script d'installation pour la plateforme à plugin Legendary-invention
# Auteurs : Pierre gaultier, Arnaud Grall, Nedhir Messaoud, Thomas Minier

# Variables to store various paths
PROJECT_PATH=`pwd`

PLATFORM_PATH="${PROJECT_PATH}/platform"
APP_PATH="${PROJECT_PATH}/application"
EXTENSIONS_PATH="${PROJECT_PATH}/extensions"

# variables to indicate which part of the project to build
BUILD_PLATFORM=false
BUILD_APP=false
BUILD_EXTENSIONS=false
SHOW_HELP=false

# check if maven is installed
if ! hash mvn 2>/dev/null; then
  echo "Error : Maven isn't installed, but it's required for the installation.
    You can get it at http://maven.apache.org"
  exit 1
fi

# check if app folder exists
if [ ! -d "$APP_PATH" ]; then
  echo "Error : the application folder doesn't exist. Cannot build without it"
  exit 1
fi

# check if platform folder exists
if [ ! -d "$PLATFORM_PATH" ]; then
  echo "Error : the platform folder doesn't exist. Cannot build without it"
  exit 1
fi

# check if extensions folder exists
if [ ! -d "$EXTENSIONS_PATH" ]; then
  echo "Error : the extensions folder doesn't exist. Cannot build without it"
  exit 1
fi

# fetch all the option of the script
while getopts "paeh" opt; do
	case $opt in
		p)
			BUILD_PLATFORM=true
			;;
		a)
			BUILD_APP=true
			;;
		e)
			BUILD_EXTENSIONS=true
			;;
		h)
			SHOW_HELP=true
			;;
		\?)
			echo "Pour plus de détails, afficher l'aide avec ./install.sh -h"
			exit 1
			;;
	esac
done

# if no options were used, we build everything
if [ $# -eq 0 ]; then
	BUILD_PLATFORM=true
	BUILD_APP=true
	BUILD_EXTENSIONS=true
fi

# Show help if asked, then exit
if $SHOW_HELP; then
	echo "Script d'installation pour la plateforme à plugin Legendary-invention"
	echo "Usage : ./install.sh [option(s)]"
	echo "Options :"
	echo "	-h	:	Affiche l'aide"
	echo "	-p	:	Compile la plateforme à plugin"
	echo "	-a	:	Compile l'application 'Monster Clicker'"
	echo "	-e	:	Compile toutes les extensions situées
			dans le dossier 'extensions'"
	echo "Le mélange d'option est autorisé (-ap, -pe, -pae, ...)"
	echo "Par exemple, l'option -pa compilera la plateforme et l'application"
	echo "Si aucun paramètre n'est passé, le script compilera l'ensemble du projet"
	exit 0
fi

# build the plateform if asked
if $BUILD_PLATFORM; then
	cd $PLATFORM_PATH
	mvn clean # clean before installing
	mvn package
fi

# build the application if asked
if $BUILD_APP; then
	cd $APP_PATH
	mvn clean # clean before installing
	mvn package
fi

# build all the extensions if asked
if $BUILD_EXTENSIONS; then
	for extension in $EXTENSIONS_PATH/*
	do
		cd $extension
		mvn clean # clean before installing
		mvn package
	done
fi
