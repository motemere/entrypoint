.DEFAULT_GOAL := build

clean:
	./gradlew clean

build: clean
	./gradlew build -x test

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test

docker-build:
	docker build --build-arg BUILD_VERSION=0.0.2-SNAPSHOT -t motemere/testproject-entrypoint:latest -t motemere/testproject-entrypoint:0.0.2-SNAPSHOT .
