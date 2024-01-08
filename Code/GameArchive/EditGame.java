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
        // Init Object / Variables
        String[] platform_list = {
            "PC", "PSP", "PSP Vita", "Playstation 1", "Playstation 2", "Playstation 3", 
            "Playstation 4", "Playstation 5", "Xbox Original", "Xbox 360", "Xbox One",
            "Xbox Series X", "NES", "GameBoy", "SNES", "Nintendo 64", "GameCube",
            "DS", "Wii", "Wii-U", "3DS", "Nintendo Switch" };
        Edit_Game = editGame;

        // Build Frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame editFrame = new JFrame("Edit Game");
        editFrame.setLocation(750, 175);
        editFrame.setResizable(false);
        editFrame.setSize(250, 500);

        // Build Panel
        Container editPane = new Container();
        editPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        editPane.setLayout(new GridBagLayout());

        // Gridbag Initialization
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 0.5;
        grid.ipadx = 10;
        grid.ipady = 10;

        Insets defauIn = new Insets(2, 5, 2, 5);

        // Components
        JLabel titleLabel = new JLabel("Title:");
        JLabel devLabel = new JLabel("Developer(s):");
        JLabel pubLabel = new JLabel("Publisher(s):");
        JLabel beatLabel = new JLabel("Beaten:");
        JLabel timeBeatLabel = new JLabel("Times Beaten:");
        JLabel platformLabel = new JLabel("Platform:");
        JLabel faveLabel = new JLabel("Favorite:");
        JLabel currLabel = new JLabel("Current:");

        JTextField titleField = new JTextField(15);
        titleField.setText(editGame.getTitle());
        JTextField devField = new JTextField(15);
        JTextField pubField = new JTextField(15);
        JTextField timesBeatField = new JTextField(15);
        timesBeatField.setText(Integer.toString(editGame.getTimesBeaten()));

        JCheckBox faveBox = new JCheckBox();
        faveBox.setSelected(editGame.getFavorite());
        JCheckBox currBox = new JCheckBox();
        currBox.setSelected(editGame.getCurrentGame());
        JCheckBox beatBox = new JCheckBox();
        beatBox.setSelected(editGame.getBeat());

        JComboBox<String> platComboBox = new JComboBox<String>(platform_list);
        platComboBox.setSelectedItem(editGame.getPlatform());
        JCalendar dateCalendarBox = new JCalendar();
        dateCalendarBox.setDate(editGame.getDate());

        // Button Listeners
        JButton backButton = new JButton("Back");
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save");

        // Grid Add
        grid.gridx = 0;                 // Column 0
        grid.gridy = 0;                 // Row 0
        grid.insets = new Insets(20, 5, 10,5);
        editPane.add(titleLabel, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        grid.insets = new Insets(20, 5, 10,5);
        editPane.add(titleField, grid);

        grid.gridx = 0;
        grid.gridy = 1;                 // Row 1
        grid.insets = defauIn;
        editPane.add(devLabel, grid);

        grid.gridx = 1;
        grid.gridy = 1;
        grid.insets = defauIn;
        editPane.add(devField, grid);
        
        grid.gridx = 0;
        grid.gridy = 2;                 // Row 2
        grid.insets = defauIn;
        editPane.add(pubLabel, grid);

        grid.gridx = 1;
        grid.gridy = 2;
        grid.insets = defauIn;
        editPane.add(pubField, grid);

        grid.weightx = 0.0;
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 3;                 // Row 3
        grid.insets = defauIn;
        editPane.add(platformLabel, grid);

        grid.weightx = 0.0;
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 4;                 // Row 4
        grid.insets = new Insets(2, 5, 10, 5);;
        editPane.add(platComboBox, grid);

        grid.weighty = 1.0;
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 5;                 // Row 5
        grid.insets = defauIn;
        editPane.add(dateCalendarBox, grid);

        grid.gridwidth = 1;             // Reset Component Defaults
        grid.weightx = 0.5;

        grid.gridx = 0;
        grid.gridy = 6;                 // Row 6
        grid.insets = defauIn;
        editPane.add(beatLabel, grid);

        grid.gridx = 1;
        grid.gridy = 6;
        grid.insets = defauIn;
        editPane.add(beatBox, grid);
        
        grid.gridx = 0;
        grid.gridy = 7;                 // Row 7
        grid.insets = defauIn;
        editPane.add(timeBeatLabel, grid);

        grid.gridx = 1;
        grid.gridy = 7;
        grid.insets = defauIn;
        editPane.add(timesBeatField, grid);

        grid.gridx = 0;
        grid.gridy = 8;                 // Row 8
        grid.insets = defauIn;
        editPane.add(faveLabel, grid);

        grid.gridx = 1;
        grid.gridy = 8;
        grid.insets = defauIn;
        editPane.add(faveBox, grid);

        grid.gridx = 0;
        grid.gridy = 9;                 // Row 9
        grid.insets = defauIn;
        editPane.add(currLabel, grid);

        grid.gridx = 1;
        grid.gridy = 9;
        grid.insets = defauIn;
        editPane.add(currBox, grid);

        grid.gridx = 0;
        grid.gridy = 10;                 // Row 10
        grid.insets = defauIn;
        editPane.add(backButton, grid);

        grid.gridx = 1;
        grid.gridy = 10;
        grid.insets = defauIn;
        editPane.add(clearButton, grid);
        
        grid.gridwidth = 2;
        grid.gridx = 0;
        grid.gridy = 11;                // Row 11
        grid.insets = new Insets(2, 5, 20, 5);;
        editPane.add(saveButton, grid);

        // Button Listeners
        backButton.addActionListener(e ->
        {
            editFrame.dispose();
        });
        saveButton.addActionListener(e ->
        {

        });
        clearButton.addActionListener(e ->
        {

        });


        // Adding Components to Frame
        editFrame.getContentPane().add(BorderLayout.CENTER, editPane);
        editFrame.pack();
        editFrame.setVisible(true);
    }
}
