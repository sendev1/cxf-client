#!/usr/bin/env bash

ARTIFACT=client
MAINCLASS=com.cxf.client.ClientApplication
VERSION=0.0.1-SNAPSHOT

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

#rm -rf build


echo "Packaging $ARTIFACT with Maven"
./gradlew clean build > build/native-image/output.txt

mkdir -p build/native-image

JAR="$ARTIFACT-$VERSION.jar"
rm -f $ARTIFACT
echo "Unpacking $JAR"
cd build/native-image
jar -xvf ../libs/$JAR >/dev/null 2>&1
cp -R META-INF BOOT-INF/classes

LIBPATH=`find BOOT-INF/lib | tr '\n' ':'`
CP=BOOT-INF/classes:$LIBPATH

GRAALVM_VERSION=`native-image --version`
echo "Compiling $ARTIFACT with $GRAALVM_VERSION"
{ time native-image \
  --verbose \
  -H:Name=$ARTIFACT \
  -H:+RemoveSaturatedTypeFlows \
  -H:+ReportExceptionStackTraces \
  -Dspring.native.remove-yaml-support=true \
  --enable-all-security-services \
  -cp $CP $MAINCLASS >> output.txt ; } 2>> output.txt

if [[ -f $ARTIFACT ]]
then
  printf "${GREEN}SUCCESS${NC}\n"
  mv ./$ARTIFACT ..
  exit 0
else
  cat output.txt
  printf "${RED}FAILURE${NC}: an error occurred when compiling the native-image.\n"
  exit 1
fi

