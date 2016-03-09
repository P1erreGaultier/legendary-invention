#!/bin/bash
# Script de création d'un nouveau plugin pour la plateforme à plugin Legendary-invention
# Auteurs : Pierre gaultier, Arnaud Grall, Nedhir Messaoud, Thomas Minier

# Variables to store various paths
PROJECT_PATH=`pwd`

APP_PATH="${PROJECT_PATH}/application"
EXTENSIONS_PATH="${PROJECT_PATH}/extensions"

APP_JAR_NAME="legendary-invention-application-1.0-SNAPSHOT.jar"
APP_JAR_PATH="${APP_PATH}/target/${APP_JAR_NAME}"
PLATFORM_JAR_PATH="${PLATFORM_PATH}/target/${PLATFORM_JAR_NAME}"

SAMPLE_PATH="${EXTENSIONS_PATH}/sample-extension"

# variables to indicate which part of the project to build
SHOW_HELP=false
EXT_NAME=""
MAIN_CLASS_NAME=""

# check if maven is installed
if ! hash mvn 2>/dev/null; then
  echo "Error : Maven isn't installed, but it's required for the installation.
    You can get it at http://maven.apache.org"
  exit 1
fi

# check if app folder exists
if [ ! -d "$APP_PATH" ]; then
  echo "Error : the application folder doesn't exist. Cannot work without it"
  exit 1
fi

# check if extensions folder exists
if [ ! -d "$EXTENSIONS_PATH" ]; then
  echo "Error : the extensions folder doesn't exist. Cannot work without it"
  exit 1
fi

# fetch all the option of the script
while getopts "hn:c:" opt; do
	case $opt in
		n)
			EXT_NAME=$OPTARG
			;;
		c)
			MAIN_CLASS_NAME=$OPTARG
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

# if the required options weren't used
if [ "$EXT_NAME" = "" ]; then
	echo "Error : missing argument -n"
	exit 1
fi

if [ "$MAIN_CLASS_NAME" = "" ]; then
	echo "Error : missing argument -c"
	exit 1
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

MAIN_PACK_PATH="${EXTENSIONS_PATH}/${EXT_NAME}/src/main/java/com/extensions/${EXT_NAME}"
MAIN_CLASS_PATH="${MAIN_PACK_PATH}/${MAIN_CLASS_NAME}.java"

# copy the files
if [ ! -d "$EXTENSIONS_PATH/$EXT_NAME" ]; then
	cp -r $SAMPLE_PATH $EXTENSIONS_PATH/$EXT_NAME
fi

# create the main class
mkdir $MAIN_PACK_PATH
mv $EXTENSIONS_PATH/$EXT_NAME/src/main/java/com/extensions/sample/Sample.java $MAIN_CLASS_PATH
rm -rf $EXTENSIONS_PATH/$EXT_NAME/src/main/java/com/extensions/sample/

# fill the templates
sed -i s/EXTNAME/${EXT_NAME}/g $MAIN_CLASS_PATH
sed -i s/CLASSNAME/${MAIN_CLASS_NAME}/g $MAIN_CLASS_PATH
sed -i s/EXTNAME/${EXT_NAME}/g $EXTENSIONS_PATH/$EXT_NAME/pom.xml

# install the dependancy
if [ ! -d "$EXTENSIONS_PATH/$EXT_NAME/lib" ]; then
	rm -rf $EXTENSIONS_PATH/$EXT_NAME/lib
fi
cp $APP_JAR_PATH $EXTENSIONS_PATH/$EXT_NAME/lib/$APP_JAR_NAME
