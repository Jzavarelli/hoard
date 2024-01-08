/*
 * Developer -   Jace L. Zavarelli
 * Company   -   Z.A. Entertainment LLC.
 * Project   -   Game Archiver :: Add Class
 * 
 */

// GUI Library
import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.util.Date;
import java.util.LinkedList;


public class AddGame extends VideoGame
{
    // Global Variables
    VideoGame New_Game = new VideoGame();
    boolean beatBool = false;
    boolean faveBool = false;
    boolean currBool = false;

    // Constructor
    public AddGame() {}

    // Parameter Constructor
    public AddGame(VideoGame newGame, LinkedList<VideoGame> games_list) 
    { 
        // Initialize Object
        New_Game = newGame;

        // Build Frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame addFrame = new JFrame("Add Game");
        addFrame.setLocation(750, 175);
        addFrame.setResizable(false);
        addFrame.setSize(250, 500);

        // Build Pane
        Container addPane = new Container();
        addPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        addPane.setLayout(new GridBagLayout());

        // Gridbag Initialization
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 0.5;
        grid.ipadx = 10;
        grid.ipady = 10;

        Insets defauIn = new Insets(2, 5, 2, 5);

        // Components
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

        JLabel currentLabel = new JLabel("Current:");
        JCheckBox currBox = new JCheckBox();

        JButton addButton = new JButton("Add");
        JButton backButton = new JButton("Back");
        JButton clearButton = new JButton("Clear Fields");

        // Grid Add
        grid.gridx = 0;                 // Column 0
        grid.gridy = 0;                 // Row 0
        grid.insets = new Insets(20, 5, 10,5);
        addPane.add(titleLabel, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        grid.insets = new Insets(20, 5, 10, 5);
        addPane.add(titleField, grid);

        grid.gridx = 0;
        grid.gridy = 1;                 // Row 1
        grid.insets = defauIn;
        addPane.add(devLabel, grid);

        grid.gridx = 1;
        grid.gridy = 1;
        grid.insets = defauIn;
        addPane.add(devField, grid);
        
        grid.gridx = 0;
        grid.gridy = 2;                 // Row 2
        grid.insets = defauIn;
        addPane.add(pubLabel, grid);

        grid.gridx = 1;
        grid.gridy = 2;
        grid.insets = defauIn;
        addPane.add(pubField, grid);

        grid.weightx = 0.0;
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 3;                 // Row 3
        grid.insets = defauIn;
        addPane.add(platformLabel, grid);

        grid.weightx = 0.0;
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 4;                 // Row 4
        grid.insets = new Insets(2, 5, 10, 5);
        addPane.add(platBox, grid);

        grid.weighty = 1.0;
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 5;                 // Row 5
        grid.insets = defauIn;
        addPane.add(dateCalendar, grid);

        grid.gridwidth = 1;             // Reset Component Defaults
        grid.weightx = 0.5;

        grid.gridx = 0;
        grid.gridy = 6;                 // Row 6
        grid.insets = defauIn;
        addPane.add(beatLabel, grid);

        grid.gridx = 1;
        grid.gridy = 6;
        grid.insets = defauIn;
        addPane.add(beatBox, grid);
        
        grid.gridx = 0;
        grid.gridy = 7;                 // Row 7
        grid.insets = defauIn;
        addPane.add(timesBeatLabel, grid);

        grid.gridx = 1;
        grid.gridy = 7;
        grid.insets = defauIn;
        addPane.add(timesbeField, grid);

        grid.gridx = 0;
        grid.gridy = 8;                 // Row 8
        grid.insets = defauIn;
        addPane.add(favoriteLabel, grid);

        grid.gridx = 1;
        grid.gridy = 8;
        grid.insets = defauIn;
        addPane.add(faveBox, grid);

        grid.gridx = 0;
        grid.gridy = 9;                 // Row 9
        grid.insets = defauIn;
        addPane.add(currentLabel, grid);

        grid.gridx = 1;
        grid.gridy = 9;
        grid.insets = defauIn;
        addPane.add(currBox, grid);

        grid.gridx = 0;
        grid.gridy = 10;                 // Row 10
        grid.insets = defauIn;
        addPane.add(backButton, grid);

        grid.gridx = 1;
        grid.gridy = 10;
        grid.insets = defauIn;
        addPane.add(clearButton, grid);

        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 11;                 // Row 11
        grid.insets = new Insets(2, 5, 20, 5);
        addPane.add(addButton, grid);

        // Event - Item Listeners
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
        currBox.addItemListener(e ->
        {
            if (currBox.isSelected())
            {
                currBool = true;
            }
            else if (!currBox.isSelected())
            {
                currBool = false;
            }
        });

        backButton.addActionListener(e ->
        {
            addFrame.dispose();
        });
        clearButton.addActionListener(e ->
        {
            titleField.setText("");
            devField.setText("");
            pubField.setText("");
            dateCalendar.setDate(new Date());
            timesbeField.setText("");

            platBox.setSelectedItem(platform_list[0]);
            faveBox.setSelected(false);
            beatBox.setSelected(false);
            currBox.setSelected(false);
        });
        addButton.addActionListener(e ->
        {
            if (titleField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(addButton, "Please ENTER a TITLE to the added game.", "Warning", 0);
            } 
            else
            {                
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
                Date selectedDate = dateCalendar.getDate();
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

                New_Game.buildVideoGame(titleHold, devHold, pubHold, selectedDate, platformHold, timesbeatHold, beatBool, faveBool, currBool);
                games_list.add(New_Game);
                addFrame.dispose();
            }
        });

        // Adding Components to Frame
        addFrame.getContentPane().add(BorderLayout.CENTER, addPane);
        addFrame.pack();
        addFrame.setVisible(true);
    }
}
