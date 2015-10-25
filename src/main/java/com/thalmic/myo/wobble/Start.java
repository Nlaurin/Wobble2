package com.thalmic.myo.wobble;

/**
 * Created by wongk8 on 10/24/2015.
 */
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.enums.LockingPolicy;
import com.thalmic.myo.enums.PoseType;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

/**
 * Created by wongk8 on 10/24/2015.
 */
public class Start {
    private static MidiDevice dev;
    private static Receiver receiver;
    public static void main(String[] args){
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for(int i = 0; i < infos.length; i++){
            if(infos[i].getName().equals("HackUMass")){
                try {
                    dev = MidiSystem.getMidiDevice(infos[i]);
                }
                catch(MidiUnavailableException e){
                    e.printStackTrace();
                }
            }

        }
        if(!dev.equals(null)){
            try {
                dev.open();
                receiver = dev.getReceiver();
            }
            catch (MidiUnavailableException e){
                e.printStackTrace();
            }
        }


        MidiMessenger messenger = new MidiMessenger();
        ControlCenter control = new ControlCenter(messenger);
        JFrame fr = new JFrame();
        fr.setExtendedState(Frame.MAXIMIZED_BOTH);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.add(control);
        fr.pack();
        fr.setVisible(true);
        try {
            Hub hub = new Hub("com.wobble.ctrlcntr");
            hub.setLockingPolicy(LockingPolicy.LOCKING_POLICY_NONE);

            System.out.println("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                throw new RuntimeException("Unable to find a Myo!");
            }

            System.out.println("Connected to a Myo armband!");
            OurDevList dataCollector = new OurDevList();
            hub.addListener(dataCollector);

            int pitch = -1;
            int lastPitch;
            int updatePitch = 0;
            Pose pose;
            receiver.send(messenger.sendNoteOn(), -1);
            Boolean exit = false;
            while (!exit) {
                hub.run(50);
                pose = dataCollector.getCurrentPose();
                updatePitch = (int)dataCollector.getPitchW();
                messenger.setNotesIndex(updatePitch);
                if(pose.getType() == PoseType.WAVE_OUT) {
                    messenger.increaseOctave();
                }
                if(pose.getType() == PoseType.WAVE_IN) {
                    messenger.decreaseOctave();
                }
                if(pose.getType() == PoseType.FIST) { //only call change of note if we changed pitch
                    lastPitch = pitch;
                    pitch = (int)dataCollector.getPitchW();
                    if(pitch != lastPitch) {
                        receiver.send(messenger.sendNoteOff(), -1);
                        messenger.setPitch(pitch);
                        receiver.send(messenger.sendNoteOn(), -1);
                    }
                }
                else {
                    receiver.send(messenger.sendNoteOff(), -1);
                    pitch = -1;
                }
                control.repaint();
            }
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}

