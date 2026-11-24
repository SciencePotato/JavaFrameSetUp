# JavaFrameSetUp

The frame is created on Swing's event-dispatch thread. Closing it stops the
render loop before disposal. `stop()` may also be called from the loop without
joining its own thread; interrupted callers retain their interrupt status.

Compile and run with a JDK that supports `java.io.Serial` (JDK 14 or newer):

```sh
mkdir -p build/classes
javac -d build/classes $(find src/main/java -name '*.java')
java -cp build/classes SetUp.Main
```

For the focused non-GUI lifecycle regression:

```sh
javac -d build/classes $(find src/main/java -name '*.java') tests/LifecycleTest.java
java -Djava.awt.headless=true -cp build/classes LifecycleTest
```

The test bypasses only JFrame construction with `Unsafe` (compiler warnings are
expected) and drawing with a no-op override; the real start/run/stop methods
are tested for termination and restart. It does not validate GUI rendering.