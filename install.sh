#!/bin/bash
# Script d'installation pour la plateforme à plugin Legendary-invention
# Auteurs : Pierre Gaultier, Arnaud Grall, Nedhir Messaoud, Thomas Minier

# TODO pour exécuter : java -cp target/legendary-invention-platform-1.0-SNAPSHOT.jar com.alma.platform.Platform

# variables to store color
WHITE="\e[39m"
RED="\e[31m"
GREEN="\e[32m"
YELLOW="\e[33m"
BLUE="\e[34m"

# Variables to store various paths
PROJECT_PATH=`pwd`

PLATFORM_PATH="${PROJECT_PATH}/platform"
APP_PATH="${PROJECT_PATH}/application"
EXTENSIONS_PATH="${PROJECT_PATH}/extensions"

PLATFORM_JAR_NAME="legendary-invention-platform-1.0-SNAPSHOT.jar"
APP_JAR_NAME="legendary-invention-application-1.0-SNAPSHOT.jar"

# variables to indicate which part of the project to build
BUILD_PLATFORM=false
BUILD_APP=false
BUILD_EXTENSIONS=false
SHOW_HELP=false

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
	echo -e "${YELLOW}[WARNING] : no option used. The script will install everything${WHITE}"
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

# check if app folder exists
if [ ! -d "$APP_PATH" ]; then
  echo -e "${RED}Error : the application folder doesn't exist. Cannot build without it${WHITE}"
  exit 1
fi

# check if platform folder exists
if [ ! -d "$PLATFORM_PATH" ]; then
  echo -e "${RED}Error : the platform folder doesn't exist. Cannot build without it${WHITE}"
  exit 1
fi

# check if extensions folder exists
if [ ! -d "$EXTENSIONS_PATH" ]; then
  echo -e "${RED}Error : the extensions folder doesn't exist. Cannot build without it${WHITE}"
  exit 1
fi

# build the plateform if asked
if $BUILD_PLATFORM; then
    echo -e "${BLUE}Entering the platform path${WHITE}"
	cd $PLATFORM_PATH
	if [ ! -d "$PLATFORM_PATH/target" ]; then
		mkdir -p $PLATFORM_PATH/target/classes
	else
		echo -e "Cleaning platform build directory in ${PLATFORM_PATH}"
		rm -rf $PLATFORM_PATH/target
		mkdir -p $PLATFORM_PATH/target/classes
	fi
	echo -e "Building the platform"
	find $PLATFORM_PATH/src/main/ -name *.java | \
		xargs javac -d $PLATFORM_PATH/target/classes -source 1.7 -bootclasspath "$JAVA_HOME/jre/lib/rt.jar"
	# move resources in Maven fashion
	cp -r $PLATFORM_PATH/src/main/resources/* $PLATFORM_PATH/target/classes/
	echo -e "Creating the platform jar"
	jar cf $PLATFORM_PATH/target/$PLATFORM_JAR_NAME -C $PLATFORM_PATH/target/classes .
	echo -e "${GREEN}Plaftorm built with success !${WHITE}"
fi

# build the application if asked
if $BUILD_APP; then
    echo -e "${BLUE}Entering the application path${WHITE}"
	cd $APP_PATH
	if [ ! -d "$APP_PATH/target" ]; then
		mkdir -p $APP_PATH/target/classes
	else
		echo -e "Cleaning application build directory in ${APP_PATH}"
		rm -rf $APP_PATH/target
		mkdir -p $APP_PATH/target/classes
	fi
	echo -e "Building the application"
	find $APP_PATH/src/main/ -name *.java | \
		xargs javac -cp .:$PLATFORM_PATH/target/$PLATFORM_JAR_NAME \
			-d $APP_PATH/target/classes -source 1.7 -bootclasspath "$JAVA_HOME/jre/lib/rt.jar"
	# move resources in Maven fashion
	cp -r $APP_PATH/src/main/resources/* $APP_PATH/target/classes/
	echo -e "Creating the application jar"
	jar cf $APP_PATH/target/$APP_JAR_NAME -C $APP_PATH/target/classes .
	echo -e "${GREEN}Application built with success !${WHITE}"
fi

# build all the extensions if asked
if $BUILD_EXTENSIONS; then
	for extension in $EXTENSIONS_PATH/*
	do
	    EXTENSION_NAME=`basename ${extension}`
	    # we ignore the sample-extension
	    if [ $EXTENSION_NAME != "sample-extension" ]; then
            echo -e "${BLUE}Entering ${EXTENSION_NAME} path${WHITE}"
            cd $extension
            if [ ! -d "$extension/target" ]; then
                mkdir -p $extension/target/classes
            else
                echo "Cleaning extension build directory in ${extension}"
                rm -rf $extension/target
                mkdir -p $extension/target/classes
            fi
            echo -e "Building the ${EXTENSION_NAME} extension"
            find $extension/src/main/ -name *.java | \
                xargs javac -cp .:$PLATFORM_PATH/target/$PLATFORM_JAR_NAME:$APP_PATH/target/$APP_JAR_NAME \
                    -d $extension/target/classes -source 1.7 -bootclasspath "$JAVA_HOME/jre/lib/rt.jar"
            # move resources in Maven fashion
            if [ -d "$extension/src/main/resources/" ]; then
                cp -r $extension/src/main/resources/* $extension/target/classes/
            fi
            #jar cf $extension/target/$extension -C $extension/target/classes .
            echo -e "${GREEN}Extension ${EXTENSION_NAME} built with success !${WHITE}"
        fi
	done
fi
