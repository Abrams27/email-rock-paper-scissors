FROM adoptopenjdk/maven-openjdk11

WORKDIR /jnp/gateway

COPY src/ src
COPY pom.xml pom.xml
COPY entrypoint.sh entrypoint.sh

RUN mvn clean install

ENTRYPOINT ["./entrypoint.sh"]
