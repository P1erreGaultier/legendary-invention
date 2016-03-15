#!/bin/bash
# Script d'installation pour la plateforme à plugin Legendary-invention
# Auteurs : Pierre gaultier, Arnaud Grall, Nedhir Messaoud, Thomas Minier

# Variables to store various paths
PROJECT_PATH=`pwd`

PLATFORM_PATH="${PROJECT_PATH}/platform"
APP_PATH="${PROJECT_PATH}/application"
EXTENSIONS_PATH="${PROJECT_PATH}/extensions"

PLATFORM_JAR_NAME="legendary-invention-platform-1.0-SNAPSHOT.jar"
APP_JAR_NAME="legendary-invention-application-1.0-SNAPSHOT.jar"

# variables to indicate which part of the project to build
ONLINE=true
BUILD_PLATFORM=false
BUILD_APP=false
BUILD_EXTENSIONS=false
SHOW_HELP=false

# fetch all the option of the script
while getopts "paeho" opt; do
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
		o)
			ONLINE=false;
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
	echo "	-o 	:	Force l'installation en mode offline (DOESN'T WORK YET)"
	echo "	-p	:	Compile la plateforme à plugin"
	echo "	-a	:	Compile l'application 'Monster Clicker'"
	echo "	-e	:	Compile toutes les extensions situées
			dans le dossier 'extensions'"
	echo "Le mélange d'option est autorisé (-ap, -pe, -pae, ...)"
	echo "Par exemple, l'option -pa compilera la plateforme et l'application"
	echo "Si aucun paramètre n'est passé, le script compilera l'ensemble du projet"
	exit 0
fi

#if not indicated, check if internet is accessible
if $ONLINE; then
	echo "Checking internet connection..."
	wget -q --tries=10 --timeout=20 --spider https://www.google.com
	if [[ ! $? -eq 0 ]]; then
		ONLINE=false;
		echo "No connection found. The installer will now run in Offline mode"
	else
		echo "Connection found. The installer will continue in Online mode"
	fi
fi

# check if maven is installed (only in Online mode)
if $ONLINE; then
	if ! hash mvn 2>/dev/null; then
	  echo "Error : Maven isn't installed, but it's required for the installation.
	    You can get it at http://maven.apache.org"
	  exit 1
	fi
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

# build the plateform if asked
if $BUILD_PLATFORM; then
	cd $PLATFORM_PATH
	if $ONLINE; then
		mvn clean # clean before installing
		mvn package
	else
		if [ ! -d "$PLATFORM_PATH/target" ]; then
			mkdir $PLATFORM_PATH/target
		else
			echo "Cleaning platform build directory in ${PLATFORM_PATH}"
			rm -rf $PLATFORM_PATH/target
			mkdir -p $PLATFORM_PATH/target/classes
		fi
		find $PLATFORM_PATH/src/main/ -name *.java | \
			xargs javac -d $PLATFORM_PATH/target/classes -source 1.7 -bootclasspath "$JAVA_HOME/jre/lib/rt.jar"
		# move resources in Maven fashion
		cp -r $PLATFORM_PATH/src/main/resources/* $PLATFORM_PATH/target/classes/
		jar cf $PLATFORM_PATH/target/$PLATFORM_JAR_NAME -C $PLATFORM_PATH/target/classes .
	fi
fi

# build the application if asked
if $BUILD_APP; then
	cd $APP_PATH
	if $ONLINE; then
		mvn clean # clean before installing
		mvn package
	else
		if [ ! -d "$APP_PATH/target" ]; then
			mkdir $APP_PATH/target
		else
			echo "Cleaning application build directory in ${APP_PATH}"
			rm -rf $APP_PATH/target
			mkdir -p $APP_PATH/target/classes
		fi
		find $APP_PATH/src/main/ -name *.java | \
			xargs javac -cp .:$PLATFORM_PATH/target/$PLATFORM_JAR_NAME \
				-d $APP_PATH/target/classes -source 1.7 -bootclasspath "$JAVA_HOME/jre/lib/rt.jar"
		# move resources in Maven fashion
		cp -r $APP_PATH/src/main/resources/* $APP_PATH/target/classes/
		jar cf $APP_PATH/target/$APP_JAR_NAME -C $APP_PATH/target/classes .
	fi
fi

# build all the extensions if asked
if $BUILD_EXTENSIONS; then
	for extension in $EXTENSIONS_PATH/*
	do
		cd $extension
		if $ONLINE; then
			mvn clean # clean before installing
			mvn package
		else
			if [ ! -d "$extension/target" ]; then
				mkdir $extension/target
			else
				echo "Cleaning extension build directory in ${extension}"
				rm -rf $extension/target
				mkdir -p $extension/target/classes
			fi
			find $extension/src/main/ -name *.java | \
				xargs javac -cp .:$PLATFORM_PATH/target/$PLATFORM_JAR_NAME:$APP_PATH/target/$APP_JAR_NAME \
					-d $extension/target/classes -source 1.7 -bootclasspath "$JAVA_HOME/jre/lib/rt.jar"
			# move resources in Maven fashion
			cp -r $extension/src/main/resources/* $extension/target/classes/
			#jar cf $extension/target/$extension -C $extension/target/classes .
		fi
	done
fi
