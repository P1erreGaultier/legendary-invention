#!/bin/bash

PROJECT_PATH=`pwd`

PLATFORM_PATH="${PROJECT_PATH}/platform"
APP_PATH="${PROJECT_PATH}/application"
EXTENSIONS_PATH="${PROJECT_PATH}/extensions"

PLATFORM_JAR_NAME="legendary-invention-platform-1.0-SNAPSHOT.jar"
APP_JAR_NAME="legendary-invention-application-1.0-SNAPSHOT.jar"
PLATFORM_JAR_PATH="${PLATFORM_PATH}/target/${PLATFORM_JAR_NAME}"
APP_JAR_PATH="${APP_PATH}/target/${APP_JAR_NAME}"

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

# build the plateform
cd $PLATFORM_PATH
mvn clean # clean before installing
mvn package

# build the application
# if lib directory doesn't exist yet
if [ ! -d "$APP_PATH/src/main/lib/" ]; then
  mkdir -p $APP_PATH/src/main/lib/
fi
# move the required jar file to lib folder
mv $PLATFORM_JAR_PATH $APP_PATH/src/main/lib/$PLATFORM_JAR_NAME
cd $APP_PATH
mvn clean # clean before installing
mvn package

# build all the extensions
for extension in $EXTENSIONS_PATH/*
do
	# if lib directory doesn't exist yet
	if [ ! -d "$extension/src/main/lib/" ]; then
	  mkdir -p $extension/src/main/lib/
	fi
	# move the required jar file to lib folder
	mv $APP_JAR_PATH $extension/src/main/lib/$APP_JAR_NAME
	cd $extension
	mvn clean # clean before installing
	mvn package
done
