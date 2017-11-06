FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD build/libs/demo-0.1.0-*.jar app.jar
COPY yjp-12.0.6/bin/linux-x86-64/libyjpagent.so libyjpagent.so
ENV JAVA_OPTS="-Xmx1G -Xms1G"
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -agentpath:/libyjpagent.so -jar /app.jar
EXPOSE 8090
EXPOSE 10001-10010