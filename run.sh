#!/bin/bash
# Script d'exécution pour la plateforme à plugin Legendary-invention
# Auteurs : Pierre Gaultier, Arnaud Grall, Nedhir Messaoud, Thomas Minier

# Variables to store various paths
PROJECT_PATH=`pwd`

PLATFORM_PATH="${PROJECT_PATH}/platform"
PLATFORM_JAR_NAME="legendary-invention-platform-1.0-SNAPSHOT.jar"

cd $PLATFORM_PATH
java -cp target/${PLATFORM_JAR_NAME} com.alma.platform.Platform
