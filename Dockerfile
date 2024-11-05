FROM openjdk:23-jdk

WORKDIR /app

COPY build/libs/SocialMedia-0.0.1-SNAPSHOT.jar app.jar
COPY nginx.conf /etc/nginx/conf.d/default.conf

ENTRYPOINT ["java", "-jar", "app.jar"]