FROM openjdk:17-jdk-slim
WORKDIR /app
COPY . /app/.
RUN chmod +x gradlew
RUN ./gradlew jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/server-1.0.jar", "--port", "8080"]
