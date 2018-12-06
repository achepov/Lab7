FROM java:8
LABEL maintainer="Anton Chepov"
COPY . /
WORKDIR /
RUN javac prog.java
CMD ["java", "-classpath", "mysql-connector-java-8.0.13.jar:."]