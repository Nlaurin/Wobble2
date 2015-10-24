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
            while (true) {
                hub.run(200);
                lastPitch = pitch;
                pitch = (int)dataCollector.getPitchW();
                if(pitch != lastPitch) //only call change of note if we changed pitch
                    System.out.println(pitch + "we changed the pitch!");
            }
        } catch (Exception e) {
            System.err.println("Error: ");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
