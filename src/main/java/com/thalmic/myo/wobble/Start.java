package com.thalmic.myo.wobble;

/**
 * Created by wongk8 on 10/24/2015.
 */
import javax.swing.*;
import java.awt.*;
/**
 * Created by wongk8 on 10/24/2015.
 */
public class Start {
    public static void main(String[] args){
        JPanel jp = new ControlCenter();
        JFrame fr = new JFrame();
        fr.setExtendedState(Frame.MAXIMIZED_BOTH);
        fr.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        fr.add(jp);
        fr.pack();

        fr.setVisible(true);
    }
}

