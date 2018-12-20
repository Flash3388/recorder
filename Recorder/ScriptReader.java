package Recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class ScriptReader implements Runnable {

    private Recordable _script;
    private String path;
    private Vector<Frame> _frames;

    public ScriptReader(Recordable script, String path) {
        this._script = script;
        this.path = path;
        _frames = new Vector<Frame>();
        readScript(path);
    }

    @Override
    public void run() {
        long startTime = FlashUtil.millisInt();
        //System.out.println("start");
        for(Frame f : _frames)
        {
            _script.readScript(f.getFrame());

            try {
                Thread.sleep(Recorder.PERIOD - (startTime - FlashUtil.millisInt()));
                startTime = FlashUtil.millisInt();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void readScript(String path) {
        _frames.clear();
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(path));
            for(String line = file.readLine(); line != null; line = file.readLine()) {
                _frames.add(new Frame(line));
            }
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
