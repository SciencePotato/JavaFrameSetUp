# JavaFrameSetUp

Mouse wheel direction uses the sign of the rotation, including events that
report several notches at once. Reset clears both directions. Headless check:

```sh
mkdir -p build/classes
javac -d build/classes $(find src/main/java -name '*.java') tests/MouseWheelTest.java
java -Djava.awt.headless=true -cp build/classes MouseWheelTest
```

This tests real AWT wheel events and input handling, not window rendering.