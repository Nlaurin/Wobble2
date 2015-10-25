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
import java.util.ArrayList;

/**
 * Created by wongk8 on 10/24/2015.
 */
public class Start {
    private static MidiDevice dev;
    private static Receiver receiver;
    public static void main(String[] args){
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        //ArrayList<MidiDevice> device = new ArrayList<>();
        //ArrayList<String> deviceDetails = new ArrayList<>();
        for(int i = 0; i < infos.length; i++){
            if(infos[i].getName().equals("HackUMass")){
                try {
                    dev = MidiSystem.getMidiDevice(infos[i]);
                }
                catch(MidiUnavailableException e){
                    e.printStackTrace();
                }
            }
                //MidiDevice dev = MidiSystem.getMidiDevice(infos[i]);

            //deviceDetails.add("Device ID: " + i);
            //deviceDetails.set(i, deviceDetails.get(i) + "\nName: " + infos[i].getName());
            //deviceDetails.set(i, deviceDetails.get(i) + "\nDescription: " + infos[i].getDescription());
//
            ////device.add(dev);
            //deviceDetails.set(i, deviceDetails.get(i) + "\nDevice: " + device);
            //System.out.println(deviceDetails.get(i));
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

//        ShortMessage myMsg = new ShortMessage();
//        for(int i = 0; i < 100; i++) {
//            if (receiver != null) {
//                try {
//                    myMsg.setMessage(ShortMessage.NOTE_ON, 0, i, 93);
//                    receiver.send(myMsg, -1);
//                } catch (InvalidMidiDataException e) {
//                    e.printStackTrace();
//                }
//            }
//            try{
//                Thread.sleep(200);
//                try {
//                    myMsg.setMessage(ShortMessage.NOTE_OFF, 0, i, 93);
//                    receiver.send(myMsg, -1);
//                }catch (InvalidMidiDataException e){
//                    e.printStackTrace();
//                }
//            }
//            catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }
            /*
            ShortMessage myMsg = new ShortMessage();
  // Start playing the note Middle C (60),
  // moderately loud (velocity = 93).
  myMsg.setMessage(ShortMessage.NOTE_ON, 0, 60, 93);
  long timeStamp = -1;
  Receiver       rcvr = MidiSystem.getReceiver();
  rcvr.send(myMsg, timeStamp);
             */

        //Synth synth = new Synth();
        MidiMessenger messenger = new MidiMessenger();
        ControlCenter control = new ControlCenter(messenger);
        JFrame fr = new JFrame();
        fr.setExtendedState(Frame.MAXIMIZED_BOTH);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.add(control);
        fr.pack();
        fr.setVisible(true);
        //synth.setInstrument(75); //Set instrument
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
            //synth.startContinuous();
            receiver.send(messenger.sendNoteOn(), -1);
            Boolean exit = false;
            while (!exit) {
                hub.run(50);
                pose = dataCollector.getCurrentPose();
                updatePitch = (int)dataCollector.getPitchW();
                //synth.setNotesIndex(updatePitch);
                messenger.setNotesIndex(updatePitch);
                if(pose.getType() == PoseType.WAVE_OUT) {
                    messenger.increaseOctave();
                }
                if(pose.getType() == PoseType.WAVE_IN) {
                    messenger.decreaseOctave();
                }
                if(pose.getType() == PoseType.FIST) { //only call change of note if we changed pitch
                    //System.out.println(dataCollector.getCurrentPose());
                    lastPitch = pitch;
                    pitch = (int)dataCollector.getPitchW();
                    if(pitch != lastPitch) {
                        //synth.stopContinuous();
                        receiver.send(messenger.sendNoteOff(), -1);
                        System.out.println(pitch + " We changed the pitch!");
                        //synth.setPitch(pitch);
                        messenger.setPitch(pitch);
                        //synth.startContinuous();
                        receiver.send(messenger.sendNoteOn(), -1);
                    }
                }
                else {
                    //synth.stopContinuous();
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

