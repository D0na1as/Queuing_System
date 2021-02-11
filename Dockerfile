FROM openjdk:8
# copy the packaged jar file into our docker image
COPY build/libs/queuing-0.0.1-SNAPSHOT.jar /queuing.jar

# set to execute the jar
ENTRYPOINT ["java", "-jar", "queuing.jar"]
