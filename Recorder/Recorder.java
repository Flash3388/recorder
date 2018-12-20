package Recorder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Recorder {

    protected final ExecutorService _executorService;
    protected final List<Future<?>> _futures;

    protected Recordable[] _scripts;

    private String _path;
    protected String _fileName;
    private File _file;

    public static final String FILE_PATH = "/home/lvuser/recorder/";
    public static final int PERIOD = 20;

    public Recorder(String fileName, Recordable... _scripts) {
        this._fileName = fileName;
        this._path = FILE_PATH + fileName;
        this._scripts = _scripts;

        _executorService = Executors.newSingleThreadExecutor();
        _futures = new ArrayList<>();
    }

    public void multiRecord() {
        for(Recordable script : _scripts) {
            String path = _path+"-"+script.getClass().getName()+".rec";
            ScriptWriter scriptWriter = new ScriptWriter(script, path);
            _futures.add(_executorService.submit(scriptWriter));
        }
    }

    public boolean isFinished() {
        for (Future<?> future : _futures) {
            if (!future.isDone()) {
                return false;
            }
        }
        return true;
    }

    public void stop() {
        for (Future<?> future : _futures) {
            if (!future.isDone() || future.isCancelled()) {
                future.cancel(true);
            }
        }
        _futures.clear();
    }

    public String path() {
        return _path;
    }
}
