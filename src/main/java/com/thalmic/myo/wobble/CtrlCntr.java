package com.thalmic.myo.wobble;

import com.thalmic.myo.DeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
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
            synth.startBeat();
            while (true) {
                hub.run(5);
                lastPitch = pitch;
                pitch = (int)dataCollector.getPitchW();
                if(pitch != lastPitch) { //only call change of note if we changed pitch
                    synth.stopBeat();
                    System.out.println(pitch + "we changed the pitch!");
                    synth.setPitch(10 + (5 * pitch));
                    synth.startBeat();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
