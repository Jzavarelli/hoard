/*
 * Developer -   Jace L. Zavarelli
 * Company   -   Z.A. Entertainment LLC.
 * Project   -   Game Archiver :: Edit Class
 * 
 */

// GUI Library
import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
 
public class EditGame extends VideoGame
{
    // Global Variables
    VideoGame Edit_Game = new VideoGame();

    // Constructor
    public EditGame() { }

    // Parameter Constructor
    public EditGame(VideoGame editGame) 
    {
        // Init Object
        Edit_Game = editGame;

        // Build Frame
        JFrame editFrame = new JFrame("Edit Game");
        editFrame.setLocation(750, 175);
        editFrame.setResizable(false);
        editFrame.setSize(250, 500);

        // Build Panel
        JPanel editPanel = new JPanel();



        // Adding Components to Frame
        editFrame.getContentPane().add(BorderLayout.CENTER, editPanel);
        editFrame.setVisible(true);
    }
}
