package com.thalmic.myo.wobble;

import javax.sound.midi.*;

/**
 * Created by nicklaurin + alexkarle + kenzowong on 10/24/15.
 */
public class MidiMessenger {

    private int pitch, volume, octave, instrument, notesIndex;
    private ShortMessage message;
    private final int notesSize = 8;
    private boolean isPlaying;
    private final int[] notes;

    public MidiMessenger(){
        octave = 0;
        notes = new int[]{60, 62, 64, 65, 67, 69, 71, 72};
        pitch = 40;
        isPlaying = false;
        volume = 100;
        notesIndex = 0;
        message = new ShortMessage();

    }
    public ShortMessage sendNoteOn(){
        isPlaying = true;
        try{
            message.setMessage(ShortMessage.NOTE_ON, 0, pitch, volume);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return null;
        }
        return message;
    }

    //public Synthesizer playNote(int length){
    //    isPlaying = true;
    //    try{
    //        MidiChannel[] channels = music.getChannels();
    //        channels[10].programChange(instrument);
    //        channels[10].noteOn(pitch, volume);
    //        delay(length);
    //        channels[10].noteOff(pitch, volume);
    //        return music;
    //    }
    //    catch (Exception e) {
    //        e.printStackTrace();
    //        return null;
    //    }
    //}

    public ShortMessage sendNoteOff(){
        isPlaying = false;
        try{
            message.setMessage(ShortMessage.NOTE_OFF, 0, pitch, volume);
        }
        catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return null;
        }
        return message;
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
        notesIndex = val;
        this.pitch = notes[val] + 12*octave;
    }

    public int getNotesIndex(){
        return notesIndex;
    }

    public boolean isPlaying(){
        return isPlaying;
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
    public void setNotesIndex(int newIndex){
        notesIndex = newIndex;
    }
}

