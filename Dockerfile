FROM openjdk:8-jre-alpine
MAINTAINER Alejandro Mantilla

RUN mkdir /code
COPY build/libs /code

EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "exec java -jar -Duser.timezone=$TIMEZONE -Dnetworkaddress.cache.ttl=60 -Dnetworkaddress.cache.negative.ttl=30 /code/*.jar" ]