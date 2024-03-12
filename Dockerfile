FROM --platform=linux/amd64 openjdk:21
EXPOSE 8080
ADD backend/target/warehouseapp.jar warehouseapp.jar
ENTRYPOINT ["java", "-jar", "warehouseapp.jar"]