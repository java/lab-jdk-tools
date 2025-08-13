# Define your base image
FROM container-registry.oracle.com/java/openjdk:25-oraclelinux9 as jre-build

RUN $JAVA_HOME/bin/jlink \
       --add-modules jdk.compiler,jdk.httpserver,<ADD_MODULES>\
       --no-man-pages \
       --no-header-files \
       --compress=zip-9 \
       --output javaruntime

# Define your base image
FROM container-registry.oracle.com/os/oraclelinux:9-slim

ENV JAVA_HOME /usr/java/openjdk-25
ENV PATH $JAVA_HOME/bin:$PATH
COPY --from=jre-build /javaruntime $JAVA_HOME

WORKDIR app

# Continue with your application deployment
COPY ./lib /app/lib
COPY ./D_bday_jlink/src/main /app

RUN $JAVA_HOME/bin/jfr configure jdk.CPUTimeSample#enabled=true && groupadd -r appuser && useradd -r -g appuser appuser
USER appuser

ENV JDK_JAVA_OPTIONS "--enable-preview"

ENTRYPOINT java --class-path 'lib/*' java/eu/ammbra/bday/Organizer.java "./resources/store/events.json" 8081