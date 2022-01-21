FROM openjdk:11-jre

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /usr/src/trafficjam/app.jar

ENV SPRING_PROFILES_ACTIVE=prod
WORKDIR /usr/src/trafficjam/
ENTRYPOINT ["java","-jar","/usr/src/trafficjam/app.jar"]

RUN dpkg --add-architecture i386 \
    && apt-get update \
    && apt-get install libgtk2.0-0:i386 libsm6:i386 -y \
    && apt install libnss3-dev libgdk-pixbuf2.0-dev libgtk-3-dev libxss-dev -y