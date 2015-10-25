package com.thalmic.myo.wobble;

/**
 * Created by wongk8 on 10/24/2015.
 */
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.enums.LockingPolicy;
import com.thalmic.myo.enums.PoseType;

import javax.swing.*;
import java.awt.*;
/**
 * Created by wongk8 on 10/24/2015.
 */
public class Start {
    public static void main(String[] args){
        Synth synth = new Synth();
        ControlCenter control = new ControlCenter(synth);
        JFrame fr = new JFrame();
        fr.setExtendedState(Frame.MAXIMIZED_BOTH);
        fr.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        fr.add(control);
        fr.pack();
        fr.setVisible(true);
        synth.setInstrument(75); //Set instrument
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
            synth.startContinuous();
            Boolean exit = false;
            while (!exit) {
                hub.run(100);
                pose = dataCollector.getCurrentPose();
                updatePitch = (int)dataCollector.getPitchW();
                synth.setNotesIndex(updatePitch);
                if(pose.getType() == PoseType.FIST) { //only call change of note if we changed pitch
                    //System.out.println(dataCollector.getCurrentPose());
                    lastPitch = pitch;
                    pitch = (int)dataCollector.getPitchW();
                    if(pitch != lastPitch) {
                        synth.stopContinuous();
                        System.out.println(pitch + " We changed the pitch!");
                        synth.setPitch(pitch);
                        synth.startContinuous();
                    }
                }
                else {
                    synth.stopContinuous();
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

