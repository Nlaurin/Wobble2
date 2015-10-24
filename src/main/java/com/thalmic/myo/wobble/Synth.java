package com.thalmic.myo.wobble;

import javax.sound.midi.*;

/**
 * Created by Shahar on 10/24/2015.
 */
public class Synth {

    private int pitch, volume;
    private boolean isPlaying;
    private Synthesizer music;

    public Synth(){
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

    public Synthesizer startBeat(){
            isPlaying = true;
        try{
            MidiChannel[] channels = music.getChannels();
            //channels[11].programChange(90);
            channels[10].programChange(37);
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

    public Synthesizer stopBeat(){
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
    public void setPitch(int pitch){
        this.pitch = pitch;
    }

    public int getPitch(){ return pitch; }

    public void addPitch(int pitch){
    this.pitch += pitch;
}

    public void closeChannel(){

    }
}
