package com.thalmic.myo.wobble;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.Quaternion;
import com.thalmic.myo.enums.*;

/**
 * Created by alexkarle on 10/24/15.
 */
public class OurDevList extends AbstractDeviceListener {
    private static final int SCALE = 16; //number of subdivisions of range of position output
    private double pitchW;
    private Pose currentPose;



    public OurDevList() {
        pitchW = 0;
        currentPose = new Pose();
    }

    @Override
    public void onOrientationData(Myo myo, long timestamp, Quaternion rotation) {
        Quaternion normalized = rotation.normalized();
        double pitch = Math.asin(2.0f * (normalized.getW() * normalized.getY() - normalized.getZ() * normalized.getX()));
        pitchW = ((pitch + Math.PI / 2.0) / Math.PI * SCALE);
    }

    @Override
    public void onPose(Myo myo, long timestamp, Pose pose) {
        currentPose = pose;
    }

    public double getPitchW(){//returns 0-7
        if (pitchW<=4)return 0;
        if (pitchW>=12)return 7;
        return pitchW-4;
    }
    public Pose getCurrentPose(){return currentPose;}
}