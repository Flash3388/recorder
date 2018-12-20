package Recorder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Player{

    Recorder _rec;

    public Player(Recorder rec) {
        this._rec = rec;
    }

    public void multiPlay() {
        for (Recordable script : _rec._scripts) {
            String path = _rec.path() + "-" + script.getClass().getName() + ".rec";
            ScriptReader scriptReader = new ScriptReader(script,path);
            _rec._futures.add(_rec._executorService.submit(scriptReader));
        }
    }
}
