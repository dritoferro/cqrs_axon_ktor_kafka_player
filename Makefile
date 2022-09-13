format:
	./gradlew ktlintFormat
	./gradlew ktlintCheck

build-jar:
	./gradlew clean
	./gradlew buildFatJar
