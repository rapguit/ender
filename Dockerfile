FROM tomcat:8-jre8
RUN apt-get update && apt-get upgrade -y

RUN rm -rf webapps/ROOT
COPY target/ender-*.war webapps/ROOT.war

#ENV config
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
