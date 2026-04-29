.DEFAULT_GOAL := build

clean:
	./gradlew clean

build: clean
	./gradlew build -x test

lint:
	./gradlew checkstyleMain checkstyleTest

test:
	./gradlew test

VERSION := $(shell grep "^version=" gradle.properties | cut -d'=' -f2)
GPR_USER := $(shell grep gpr.user ~/.gradle/gradle.properties | cut -d= -f2)
GPR_TOKEN := $(shell grep gpr.key ~/.gradle/gradle.properties | cut -d= -f2)

docker-build:
	docker build \
		--build-arg GPR_USER=$(GPR_USER) \
		--build-arg GPR_TOKEN=$(GPR_TOKEN) \
		-t motemere/testproject-entrypoint:latest \
		-t motemere/testproject-entrypoint:$(VERSION) \
		.
