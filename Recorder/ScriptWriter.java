package Recorder;

import edu.flash3388.flashlib.util.FlashUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;


public class ScriptWriter implements Runnable {

    private String path;
    private Recordable _script;
    private Vector<Frame> _frames;

    public ScriptWriter(Recordable script,String path) {
        this.path = path;
        this._script = script;
        _frames = new Vector<Frame>();
    }

    @Override
    public void run() {
        long startTime = FlashUtil.millisInt();

        while(!Thread.interrupted()) {
            _frames.addElement(new Frame(_script.writeScript()));
            try {
                Thread.sleep(Recorder.PERIOD - (startTime - FlashUtil.millisInt()));
                startTime = FlashUtil.millisInt();

            } catch (InterruptedException e) {
                System.out.println("interraped");
                break;
            }
        }
        System.out.println("tofile shit bitch face");
        toFile(path);
    }

    private void toFile(String path) {
        try {
            FileWriter writer = new FileWriter(path);
            try {
                for(Frame f : _frames) {
                    writer.write(f.getFrame());
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        _frames.clear();
    }
}
