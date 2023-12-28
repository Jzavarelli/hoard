/*
 * Developer -   Jace L. Zavarelli
 * Company   -   Z.A. Entertainment LLC.
 * Project   -   Game Archiver
 * 
 */

// GUI Library
import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class AddGame extends VideoGame
{
    // Global Variables
    VideoGame New_Game = new VideoGame();
    boolean beatBool = false;
    boolean faveBool = false;
    boolean currBool = false;


    public AddGame() {}

    // Constructor
    public AddGame(VideoGame newGame) 
    { 
        // Initialize Object
        New_Game = newGame;

        // Build Frame
        JFrame addFrame = new JFrame("Add Game");
        addFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addFrame.setLocation(750, 175);
        addFrame.setResizable(false);
        addFrame.setSize(250, 500);

        // Build Panel
        JPanel addPanel = new JPanel();
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField(15);

        JLabel devLabel = new JLabel("Developer(s):");
        JTextField devField = new JTextField(15);
        devField.setToolTipText("Format Developer(s) as: developer_1, developer_2, etc");

        JLabel pubLabel = new JLabel("Publisher(s):");
        JTextField pubField = new JTextField(15);
        pubField.setToolTipText("Format Publisher(s) as: publisher_1, publisher_2, etc");

        JCalendar dateCalendar = new JCalendar();

        JLabel beatLabel = new JLabel("Beaten:");
        JCheckBox beatBox = new JCheckBox();

        JLabel timesBeatLabel = new JLabel("Times Beaten:");
        JTextField timesbeField = new JTextField(15);
        timesbeField.setToolTipText("Format Times Beaten (NUMBER) as: XXX");
        timesbeField.setEnabled(false);

        JLabel platformLabel = new JLabel("Platform:");
        String[] platform_list = {
        "PC", "PSP", "PSP Vita", "Playstation 1", "Playstation 2", "Playstation 3", 
        "Playstation 4", "Playstation 5", "Xbox Original", "Xbox 360", "Xbox One",
        "Xbox Series X", "NES", "GameBoy", "SNES", "Nintendo 64", "GameCube",
        "DS", "Wii", "Wii-U", "3DS", "Nintendo Switch" };

        JComboBox<String> platBox = new JComboBox<String>(platform_list);

        JLabel favoriteLabel = new JLabel("Favorite:");
        JCheckBox faveBox = new JCheckBox();

        // JLabel currentLabel = new JLabel("Current:");
        // JCheckBox currBox = new JCheckBox();

        JButton addButton = new JButton("Add");
        JButton clearButton = new JButton("Clear Fields");

        addPanel.add(titleLabel);
        addPanel.add(titleField);
        addPanel.add(devLabel);
        addPanel.add(devField);
        addPanel.add(pubLabel);
        addPanel.add(pubField);
        addPanel.add(dateCalendar);

        addPanel.add(beatLabel);
        addPanel.add(beatBox);

        addPanel.add(timesBeatLabel);
        addPanel.add(timesbeField);
        addPanel.add(platformLabel);
        addPanel.add(platBox);

        addPanel.add(favoriteLabel);
        addPanel.add(faveBox);

        addPanel.add(addButton);
        addPanel.add(clearButton);

        // Event - Item Listeners
        clearButton.addActionListener(e ->
        {
            titleField.setText("");
            devField.setText("");
            pubField.setText("");
            // dateField.setText("");
            timesbeField.setText("");

            platBox.setSelectedItem(platform_list[0]);
            faveBox.setSelected(false);
            beatBox.setSelected(false);
        });

        beatBox.addItemListener(e ->
        {
            if (timesbeField.isEnabled())
            {
                beatBool = false;
                timesbeField.setEnabled(false);
            }
            else if (!timesbeField.isEnabled())
            {
                beatBool = true;
                timesbeField.setEnabled(true);
            }
        });

        faveBox.addItemListener(e ->
        {
            if (faveBox.isSelected())
            {
                faveBool = true;
            }
            else if (!faveBox.isSelected())
            {
                faveBool = false;
            }
        });

        addButton.addActionListener(e ->
        {
            // Variables - Date
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-YYYY");

            // Variables - Publisher / Developer
            String indDevHold = devField.getText();
            String indPubHold = pubField.getText();
            LinkedList<String> devHold = new LinkedList<String>();
            LinkedList<String> pubHold = new LinkedList<String>();

            if (indDevHold.equals(null))
            {
                devHold.add("No Developers");
            }
            else
            {
                String[] devTempArr = indDevHold.split(",");
                for (String str : devTempArr)
                {
                    devHold.add(str);
                }
            }

            if (indPubHold.equals(null))
            {
                devHold.add("No Publishers");
            }
            else
            {
                String[] pubTempArray = indPubHold.split(",");
                for (String str : pubTempArray)
                {
                    pubHold.add(str);
                }
            }

            // Variables - Other
            String titleHold = "NULL";
            int timesbeatHold = 0;

            // dateHold = dateField.getText();
            titleHold = titleField.getText();
            String platformHold = (String) platBox.getSelectedItem();

            if (beatBool)
            {
                if (timesbeField.getText().equals(""))
                {
                    timesbeatHold = 1;
                }
                else
                {
                    timesbeatHold = Integer.parseInt(timesbeField.getText());
                }
            }
            else if (!beatBool)
            {
                timesbeatHold = 0;
            }

            Date selectedDate = dateCalendar.getDate();
            New_Game.buildVideoGame(titleHold, devHold, pubHold, selectedDate, platformHold, timesbeatHold, beatBool, faveBool, currBool);
            addFrame.dispose();
        });

        // Adding Components to Frame
        addFrame.getContentPane().add(BorderLayout.CENTER, addPanel);
        addFrame.setVisible(true);
    }
}
