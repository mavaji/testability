# Testability
[Automatic test pattern generation](https://en.wikipedia.org/wiki/Automatic_test_pattern_generation)

#### How to Run
```text
mvn clean package
java -cp target/testability-1.0.0.jar gui.GUI
```

Load a sample file from ```resources``` folder or write yourself a script in main window.
Then run `Test->Do Test Generation!`.

Specify the output file (the program output format is `xml`) and The `Fault Coverage` (a value between `0` and `1` : `0 < fc < 1`).