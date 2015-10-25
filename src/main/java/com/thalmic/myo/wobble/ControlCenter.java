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
    //private messenger messenger;
    private MidiMessenger messenger;
    private int windowWidth, windowHeight;
    private final char[] notes = {'C', 'D', 'E', 'F', 'G', 'A', 'B', 'C'};
    public ControlCenter(MidiMessenger newMessenger){
        messenger = newMessenger;
    }
    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        windowHeight = getHeight();
        windowWidth = getWidth();
        g2.setColor(Color.white);
        g2.fillRect(0,0,windowWidth,windowHeight);
        g2.setColor(Color.black);
        g2.drawLine(windowWidth/2,0,windowWidth/2,windowHeight);
        g2.setStroke(new BasicStroke(10));
        for(int i = 0; i < 8; i++) {
            if(messenger.isPlaying()){
                if(i == messenger.getNotesIndex()) {
                    g2.setColor(new Color(243, 31, 221,255));
                    g2.fillRect(0, (7-i)*windowHeight/8,windowWidth/2, windowHeight/8);
                    g2.setColor(Color.black);
                    g2.drawRect(0, (7-i)*windowHeight/8,windowWidth/2, windowHeight/8);
                    //g2.drawString("" + notes[messenger.getNotesIndex()], centerOfPitchX, (windowHeight / 9 + 100 * (7 - messenger.getNotesIndex())));
                }
                else {
                    g2.setColor(new Color(0, 243, 196, 240));
                    g2.fillRect(0, (7-i)*windowHeight/8,windowWidth/2, windowHeight/8);
                    g2.setColor(Color.black);
                    g2.drawRect(0, (7-i)*windowHeight/8,windowWidth/2, windowHeight/8);
                    //g2.drawString("" + notes[i], centerOfPitchX, (windowHeight / 9 + 100 * (7 - i)));
                }
            }
            else {
                if(i == messenger.getNotesIndex()) {
                    g2.setColor(new Color(172, 243, 101,240));
                    g2.fillRect(0, (7-i)*windowHeight/8,windowWidth/2, windowHeight/8);
                    g2.setColor(Color.black);
                    g2.drawRect(0, (7-i)*windowHeight/8,windowWidth/2, windowHeight/8);
                    //g2.drawString("" + notes[messenger.getNotesIndex()], centerOfPitchX, (windowHeight / 8) * (7 - messenger.getNotesIndex()) + windowHeight/14);
                }
                else {
                    g2.setColor(new Color(0, 243, 196,240));
                    g2.fillRect(0, (7 - i) * windowHeight / 8, windowWidth / 2, windowHeight / 8);
                    g2.setColor(Color.black);
                    g2.drawRect(0, (7-i)*windowHeight/8,windowWidth/2, windowHeight/8);
                }
            }

            g2.setFont(new Font("default", Font.BOLD, 32));
            g2.drawString("" + notes[i], 20, (windowHeight / 8) * (7-i) + windowHeight/14);
            g2.setFont(new Font("default", Font.BOLD, 32));
            g2.drawString("Pitch", windowWidth/5 + 30,windowHeight/20);
            g2.drawString("Octave: "+messenger.getOctave(), 1200, 400);
        }
        //if(messenger.isPlaying()) {
        //    g2.setColor(Color.green);
        //}
        //else{
        //    g2.setColor(Color.blue);
        //    g2.drawString("" + notes[messenger.getNotesIndex()], centerOfPitchX, (windowHeight / 9 + 100 * messenger.getNotesIndex()));
        //}
    }
}

