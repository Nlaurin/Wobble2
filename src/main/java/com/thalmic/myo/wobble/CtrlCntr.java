package com.thalmic.myo.wobble;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.enums.PoseType;
import com.thalmic.myo.example.DataCollector;

/**
 * Created by alexkarle on 10/24/15.
 */
public class CtrlCntr {

    public static void main(String[] args) {
        Synth synth = new Synth();
        try {
            Hub hub = new Hub("com.wobble.ctrlcntr");

            System.out.println("Attempting to find a Myo...");
            Myo myo = hub.waitForMyo(10000);

            if (myo == null) {
                throw new RuntimeException("Unable to find a Myo!");
            }

            System.out.println("Connected to a Myo armband!");
            OurDevList dataCollector = new OurDevList();
            hub.addListener(dataCollector);

            int pitch = 0;
            int lastPitch = 0;
            Pose pose;
            synth.startContinuous();
            Boolean exit = false;
            while (!exit) {
                hub.run(5);
                lastPitch = pitch;
                pitch = (int)dataCollector.getPitchW();
                pose = dataCollector.getCurrentPose();
                System.out.println(dataCollector.getCurrentPose());
                if(pose.getType() == PoseType.FIST) { //only call change of note if we changed pitch
                    System.out.println(dataCollector.getCurrentPose());
                    if(pitch != lastPitch) {
                        synth.stopContinuous();
                        System.out.println(pitch + "we changed the pitch!");
                        synth.setPitch(pitch);
                        synth.startContinuous();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
