import javax.sound.midi.*;

/**
 * Created by Shahar on 10/24/2015.
 */
public class Synth {

    private int pitch, volume;
    private boolean isPlaying;

    public Synth(){
        pitch = 40;
        isPlaying = false;
        volume = 100;
    }

    public Synthesizer playBeat(int length){

        try{
            Synthesizer music = MidiSystem.getSynthesizer();
            music.open();
            MidiChannel[] channels = music.getChannels();
            channels[11].programChange(90);
            channels[10].programChange(68);
            //channels[11].noteOn(pitch, volume);
            channels[10].noteOn(pitch, volume);
            this.delay(length);
            channels[10].noteOff(pitch,volume);
            return music;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delay(long time){
        try{
            Thread.sleep(time);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public void setPitch(int pitch){
        this.pitch = pitch;
    }

    public int getPitch(){ return pitch; }
public void addPitch(int pitch){
    this.pitch += pitch;
}
}
