package com.thalmic.myo.wobble;

/**
 * Created by wongk8 on 10/24/2015.
 */
import javax.swing.*;
import java.awt.*;
/**
 * Created by wongk8 on 10/24/2015.
 */
public class ControlCenter extends JPanel {
    private Synth synth;
    private int windowWidth, windowHeight;
    private final char[] notes = {'C', 'D', 'E', 'F', 'G', 'A', 'B', 'C'};
    public ControlCenter(){
        synth = new Synth();
    }
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        windowHeight = getHeight();
        windowWidth = getWidth();
        g2.setColor(Color.white);
        g2.fillRect(0,0,windowWidth,windowHeight);
        g2.setColor(Color.black);
        g2.drawLine(windowWidth/2,0,windowWidth/2,windowHeight);
        g2.setFont(new Font("default", Font.BOLD, 32));
        g2.drawString("Pitch", windowWidth/5,windowHeight/20);
        int centerOfPitchX = (windowWidth/5) + 30;
        g2.setFont(new Font("default", Font.BOLD, 24));
        for(int i = 0; i < 8; i++) {
            g2.drawString("" + notes[i], centerOfPitchX, (windowHeight / 9 + 100 * i));
        }
        if(synth.isPlaying())
            g2.setColor(Color.green);
        else
            g2.setColor(Color.blue);
        g2.drawString("" + notes[synth.getNotesIndex()], centerOfPitchX, (windowHeight / 9 + 100 * synth.getNotesIndex()));
    }
}

