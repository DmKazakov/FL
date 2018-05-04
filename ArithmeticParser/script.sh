#!/bin/bash

sudo apt-get update
sudo apt-get install openjdk-8-jdk -y
wget https://services.gradle.org/distributions/gradle-3.4.1-bin.zip
sudo mkdir /opt/gradle
sudo unzip -d /opt/gradle gradle-3.4.1-bin.zip
sudo export PATH=$PATH:/opt/gradle/gradle-3.4.1/bin