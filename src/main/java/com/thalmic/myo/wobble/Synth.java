package com.thalmic.myo.wobble;

import javax.sound.midi.*;

/**
 * Created by Shahar on 10/24/2015.
 */
public class Synth {

    private int pitch, volume, octave, instrument;
    private final int notesSize = 8;
    private boolean isPlaying;
    private Synthesizer music;
    private final int[] notes;

    public Synth(){
        octave = 0;
        notes = new int[]{60, 62, 64, 65, 67, 69, 71, 72};
        pitch = 40;
        isPlaying = false;
        volume = 100;
        instrument = 90;
        try {
            music = MidiSystem.getSynthesizer();
            music.open();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public Synthesizer startContinuous(){
        isPlaying = true;
        try{
            MidiChannel[] channels = music.getChannels();
            //channels[11].programChange(90);
            channels[10].programChange(instrument);
            //channels[11].noteOn(pitch, volume);
            channels[10].noteOn(pitch, volume);
//            if(isPlaying) {
//                channels[10].noteOff(pitch, volume);
//                isPlaying = false;
//            }
            //else
            //channels[10].noteOff(pitch,volume);
            return music;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Synthesizer playNote(int length){
        isPlaying = true;
        try{
            MidiChannel[] channels = music.getChannels();
            channels[10].programChange(instrument);
            channels[10].noteOn(pitch, volume);
            delay(length);
            channels[10].noteOff(pitch,volume);
            return music;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Synthesizer stopContinuous(){
        try{
            MidiChannel[] channels = music.getChannels();
            channels[10].noteOff(pitch, volume);
            return music;
        }
        catch (Exception e){
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

    public void setPitch(int val){
        this.pitch = notes[val] + 12*octave;
    }

    public void increaseOctave() {
        octave++;
    }

    public void decreaseOctave() {
        octave--;
    }

    public void resetOctave() {
        octave = 0;
    }

    public int getPitch(){ return pitch; }

    public void addPitch(int pitch){
        this.pitch += pitch;
    }

    public void setVolume(int vol){
        volume = vol;
    }

    public int getVolume(int vold){
        return volume;
    }
    public void setInstrument(int instrument){
        this.instrument = instrument;
    }
}


