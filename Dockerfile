FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.4

ARG WAR_FILE=target/poc*.jar

ARG PROFILE

ARG ARGS

ENV PROFILE=${PROFILE}

ENV ARGS=${ARGS}

WORKDIR /opt/app

CMD mkdir /opt/pdf

COPY ${WAR_FILE} app.jar

#ENTRYPOINT ["java", "-jar", "/opt/app.jar", "-Dspring.profiles.active=dev"]

SHELL ["/bin/sh", "-c"]

EXPOSE 5000

EXPOSE 8443

CMD java ${ARGS} -jar app.jar --spring.profiles.active=${PROFILE}