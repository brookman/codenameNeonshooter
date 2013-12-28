#!/bin/bash
cd ~/auto-builds/codenameNeonshooter
git pull | grep "Already up-to-date."
#if [ $? -eq 0 ]
#        then exit
#        else echo "Let's build!"
#fi
mvn clean
rm -R desktop/target
mvn package -Pdesktop
rm -R html/target/webapp
mvn package -Phtml
rm -R android/target
mvn package -Pandroid
rm -R /usr/share/nginx/html/neonshooter
cp -R html/target/webapp/ /usr/share/nginx/html/neonshooter/
cp android/target/neonshooter-android.apk /usr/share/nginx/html/neonshooter/
cp desktop/target/neonshooter-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar /usr/share/nginx/html/neonshooter

