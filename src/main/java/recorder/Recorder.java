package recorder;

import execution.Frame;

public interface Recorder {
    Frame capture();
    String getName();
}
