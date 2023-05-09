# K.E.N



## Getting started

To run the JAR file, you need to have Java installed and run the following command:

```
java -jar k.e.n.jar
```

## Building The source [IntelliJ]:

### Weather API

- [OpenWeatherAPI](https://openweathermap.org/api)
- [How to implement](https://github.com/Prominence/openweathermap-java-api)

### UI

- [JavaFX](https://openjfx.io/)
- Download the files then in : File > Project Structure > Libraries Select Java and add the folder
- Then in the Compiler Options : Edit Configurations > Add VM
  Options > `--module-path ${JFX} --add-modules javafx.controls,javafx.fxml` Where `${JFX}` is the path to JavaFX

### JUNIT 5

Go to File > Project Structure > Libraries Select Maven and search for `org.junit.jupiter:junit-jupiter:5.9.2` > Ok >
Add.

### OpenAI

Provide your own OpenAI key in the OpenAI class.
