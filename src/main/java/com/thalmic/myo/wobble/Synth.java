package com.thalmic.myo.wobble;

import javax.sound.midi.*;

/**
 * Created by Shahar on 10/24/2015.
 */
public class Synth {

    private int pitch, volume, octave;
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
            channels[10].programChange(89);
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
            channels[10].programChange(89);
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

    //Will get a value of 0 - 18, needs to convert to 0-7
    public void setPitch(int val){
        int index;
        if(val < 2)
            index = 0;
        else if(val > 16)
            index = 7;
        else index = (val/2)-1;
        this.pitch = notes[index] + 12*octave;
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

    public void closeChannel(){

    }
}


