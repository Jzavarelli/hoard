/*
 * Developer -   Jace L. Zavarelli
 * Company   -   Z.A. Entertainment LLC.
 * Project   -   Game Archiver :: Edit Class
 * 
 */

// GUI Library
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.util.Date;
import java.util.LinkedList;
 
public class EditGame extends VideoGame
{
    // Global Variables
    VideoGame Edit_Game = new VideoGame();
    Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    boolean beatBool = false;
    boolean faveBool = false;
    boolean currBool = false;

    String devString = "";
    String pubString = "";
    String[] platform_list = {
        "PC", "PSP", "PSP Vita", "Playstation 1", "Playstation 2", "Playstation 3", 
        "Playstation 4", "Playstation 5", "Xbox Original", "Xbox 360", "Xbox One",
        "Xbox Series X", "NES", "GameBoy", "SNES", "Nintendo 64", "GameCube",
        "DS", "Wii", "Wii-U", "3DS", "Nintendo Switch" };

    // Constructor
    public EditGame() { }

    // Parameter Constructor
    public EditGame(VideoGame editGame, LinkedList<VideoGame> games_list, int index, DefaultTableModel tableModel) 
    {
        // Init Object / Variables
        Edit_Game = editGame;

        // Build Frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame editFrame = new JFrame("Edit Game");
        editFrame.setResizable(false);


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
        for (int j = 0; j < editGame.getDevelopers().size(); j++)
        {
            if (j == editGame.getDevelopers().size() - 1)
            {
                devString += (editGame.getDevelopers().get(j));
            }
            else
            {
                devString += (editGame.getDevelopers().get(j)+ ", ");
            }
        }
        devField.setText(devString);
        JTextField pubField = new JTextField(15);
        for (int k = 0; k < editGame.getPublishers().size(); k++)
        {
            if (k == editGame.getPublishers().size() - 1)
            {
                pubString += (editGame.getPublishers().get(k));
            }
            else
            {
                pubString += (editGame.getPublishers().get(k) + ", ");
            }
        }
        pubField.setText(pubString);
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

        // Current Condition
        for (VideoGame game : games_list)
        {
            if (game.getCurrentGame() == true && Edit_Game.getCurrentGame() != true)
            {
                currBox.setEnabled(false);
            }
            else
            {
                currBox.setEnabled(true);
            }
        }

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

        // Box Listeners
        beatBox.addItemListener(e ->
        {
            if (timesBeatField.isEnabled())
            {
                beatBool = false;
                timesBeatField.setEnabled(false);
            }
            else if (!timesBeatField.isEnabled())
            {
                beatBool = true;
                timesBeatField.setEnabled(true);
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


        // Button Listeners
        backButton.addActionListener(e ->
        {
            editFrame.dispose();
        });
        clearButton.addActionListener(e ->
        {
            titleField.setText(editGame.getTitle());
            devField.setText(devString); 
            pubField.setText(pubString);
            timesBeatField.setText(Integer.toString(editGame.getTimesBeaten()));    
            
            faveBox.setSelected(editGame.getFavorite());
            currBox.setSelected(editGame.getCurrentGame());
            beatBox.setSelected(editGame.getBeat());

            platComboBox.setSelectedItem(editGame.getPlatform());
            dateCalendarBox.setDate(editGame.getDate());
        });
        saveButton.addActionListener(e ->
        {
            if (titleField.getText().equals(""))
            {
                JOptionPane.showMessageDialog(saveButton, "Please HAVE a TITLE to the added game.", "Warning", 0);
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
                Date selectedDate = dateCalendarBox.getDate();
                String titleHold = "NULL";
                int timesbeatHold = 0;

                // dateHold = dateField.getText();
                titleHold = titleField.getText();
                String platformHold = (String) platComboBox.getSelectedItem();

                if (beatBool)
                {
                    if (timesBeatField.getText().equals(""))
                    {
                        timesbeatHold = 1;
                    }
                    else
                    {
                        timesbeatHold = Integer.parseInt(timesBeatField.getText());
                    }
                }
                else if (!beatBool)
                {
                    timesbeatHold = 0;
                }
                String imgPath = ".\\Images\\img_000.jpg";
                Edit_Game.buildVideoGame(titleHold, devHold, pubHold, selectedDate, platformHold, timesbeatHold, beatBool, faveBool, currBool, imgPath);
                games_list.remove(index);
                games_list.add(index, Edit_Game);

                for (VideoGame game : games_list)
                {
                    game.print();
                }
                //tableModel.fireTableDataChanged();
                editFrame.dispose();
            }
        });


        // Adding Components to Frame
        editFrame.getContentPane().add(BorderLayout.CENTER, editPane);
        editFrame.pack();
        editFrame.setLocation((screenDimension.width/8), (screenDimension.height/8));
        editFrame.setVisible(true);
    }
}
