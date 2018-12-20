package Recorder;

import java.util.function.BinaryOperator;

public interface Recordable {

    String writeScript();
    void readScript(String val);
}
