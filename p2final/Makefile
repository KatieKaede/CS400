
runBDTests:
	javac -cp .:../junit5.jar *.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

runFDTest: FrontendDeveloperTests.class
	java -jar ../junit5.jar --class-path=. --select-class=FrontendDeveloperTests
FrontendDeveloperTests.class: FrontendDeveloperTests.java
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java

clean:
	@rm *.class
