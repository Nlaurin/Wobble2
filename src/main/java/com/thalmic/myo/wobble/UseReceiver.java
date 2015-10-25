package com.thalmic.myo.wobble;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;

/**
 * Created by alexkarle on 10/24/15.
 */
public class UseReceiver {
    private Receiver receiver;
    public UseReceiver(Synth synth){
        try {
            synth.getSynthesizer().open();
        }
        catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
            try {
            receiver = synth.getSynthesizer().getReceiver();
        }catch (MidiUnavailableException e){
            e.printStackTrace();
        }


    }
}
