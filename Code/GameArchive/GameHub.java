/*
 * Developer -   Jace L. Zavarelli
 * Company   -   Z.A. Entertainment LLC.
 * Project   -   Game Archiver :: Core Class
 * 
 * Description:
 * A project for logging and recording the most recent games
 * added to an independent collection. The project uses an
 * independent GUI that shows all the details of an attached
 * game and its main attributes. 
 * 
 */

// Main Java Libraries
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

public class GameHub extends VideoGame
{
    // // Global Parameters
    static JFrame gameFrame = new JFrame("Game Archiver");

    // Constructor
    public GameHub() { }

    public static void refreshList(LinkedList<VideoGame> games_list, JTable games_table)
    {
        //String[] col_names = {"Title", "Platform", "Beaten", "Times Beaten", "Currently Playing", "Favorite"};
        Object row_items[] = new Object[6];
        DefaultTableModel model = (DefaultTableModel) games_table.getModel();

        if (model.getRowCount() == 0 && !games_list.isEmpty())
        {
            //System.out.println("New Item");
            row_items[0] = games_list.get(0).getTitle();
            row_items[1] = games_list.get(0).getPlatform();
            row_items[2] = games_list.get(0).getBeat();
            row_items[3] = games_list.get(0).getTimesBeaten();
            row_items[4] = games_list.get(0).getCurrentGame();
            row_items[5] = games_list.get(0).getFavorite();

            model.addRow(row_items);
        }
        

        if (model.getRowCount() > 0)
        {
            for(int i = model.getRowCount(); i < games_list.size(); i++)
            {
                row_items[0] = games_list.get(i).getTitle();
                row_items[1] = games_list.get(i).getPlatform();
                row_items[2] = games_list.get(i).getBeat();
                row_items[3] = games_list.get(i).getTimesBeaten();
                row_items[4] = games_list.get(i).getCurrentGame();
                row_items[5] = games_list.get(i).getFavorite();

                model.addRow(row_items);
                row_items = new Object[6];
            }
        }
        
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    // Main Method
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {
        // LaF Pre-Initialization
        try 
        {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } 
        catch( Exception ex ) 
        {
            System.err.println( "Failed to initialize LaF" );
        }

        // Variable Initialized
        StringBuilder strBuild = new StringBuilder();
        LinkedList<VideoGame> game_titles = new LinkedList<VideoGame>();

        // Build Frame
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocation(500, 150);
        gameFrame.setResizable(false);
        gameFrame.setSize(1000, 750);

        // MenuBar System
        JMenuBar menBar = new JMenuBar();
        JMenu item1 = new JMenu("File");
        JMenu item2 = new JMenu("Theme");
        menBar.add(item1);
        menBar.add(item2);

        JMenuItem item1Open = new JMenuItem("Open");
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFileFilter(new FileNameExtensionFilter("Only .json files", "json", "JSON"));
        JMenuItem item1Save = new JMenuItem("Save As...");

        // File Initializer
        item1.add(item1Open);
        item1Open.addActionListener(e ->
        {
            fileOpen.setDialogTitle("Select a JSON archive file");
            int approval_opt = fileOpen.showOpenDialog(null);
            if (approval_opt == JFileChooser.APPROVE_OPTION)
            {
                String filePath = fileOpen.getSelectedFile().getAbsolutePath();

            }
            else
            {
                System.out.println("User Canceled File Opening");
            }
        });

        item1.add(item1Save);
        item1Save.addActionListener(e ->
        {
            JSONObject obj = new JSONObject();
            JSONArray games = new JSONArray();
            for (int i = 0; i < game_titles.size(); i++)
            {
                JSONObject gameObj = new JSONObject();
                gameObj.put("Title", game_titles.get(i).getTitle());
                gameObj.put("Release Date", game_titles.get(i).getDate().toString());
                gameObj.put("Platform", game_titles.get(i).getPlatform());
                gameObj.put("Developer(s)", game_titles.get(i).getDevelopers());
                gameObj.put("Publisher(s)", game_titles.get(i).getPublishers());
                gameObj.put("Beat", game_titles.get(i).getBeat());
                gameObj.put("Times Beat", game_titles.get(i).getTimesBeaten());
                gameObj.put("Favorite", game_titles.get(i).getFavorite());
                gameObj.put("Current", game_titles.get(i).getCurrentGame());

                games.add(gameObj);
            }

            obj.put("Games", games);

            fileOpen.setDialogTitle("Save as a JSON archive file");
            fileOpen.setCurrentDirectory(new File(".\\"));
            int approval_opt = fileOpen.showSaveDialog(null);
            if (approval_opt == JFileChooser.APPROVE_OPTION)
            {
                strBuild.append(fileOpen.getSelectedFile().getAbsolutePath());
                strBuild.append(".json");    
                try
                {
                    FileWriter fileToWrite = new FileWriter(strBuild.toString());
                    fileToWrite.write(obj.toJSONString());
                    fileToWrite.flush();
                    fileToWrite.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                System.out.println("User Canceled Save Option");
            }
        });

        JMenuItem item2Light = new JMenuItem("Light Theme");
        JMenuItem item2Dark = new JMenuItem("Dark Theme");

        // LaF Initializer 
        item2.add(item2Light);
        item2Light.addActionListener(e -> 
        {
            try 
            {
                System.out.println("Light Theme");
                UIManager.setLookAndFeel( new FlatLightLaf() );
            } 
            catch( Exception ex ) 
            {
                System.err.println( "Failed to initialize LaF" );
            }
        });

        item2.add(item2Dark);
        item2Dark.addActionListener(e -> 
        {
            try 
            {
                System.out.println("Dark Theme");
                UIManager.setLookAndFeel( new FlatDarkLaf() );
            } 
            catch( Exception ex ) 
            {
                System.err.println( "Failed to initialize LaF" );
            }
        });

        // Build Panel
        JPanel gameViewPanel = new JPanel();
        JPanel gamePanel = new JPanel();

        String[] col_names = {"Title", "Platform", "Beaten", "Times Beaten", "Currently Playing", "Favorite"};
        DefaultTableModel table_core = new DefaultTableModel();
        table_core.addColumn(col_names[0]);
        table_core.addColumn(col_names[1]);
        table_core.addColumn(col_names[2]);
        table_core.addColumn(col_names[3]);
        table_core.addColumn(col_names[4]);
        table_core.addColumn(col_names[5]);

        JTable gameTable = new JTable(table_core);
        JScrollPane scrollView = new JScrollPane(gameTable);

        // Add Button
        JButton addButton = new JButton("Add Game");
        addButton.addActionListener(e ->
        {
            VideoGame newGame = new VideoGame();
            new AddGame(newGame, game_titles);
        });

        // Edit Button
        JButton editButton = new JButton("Edit Game");
        editButton.addActionListener(e ->
        {
            if (gameTable.getRowCount() == 0 || gameTable.getSelectionModel().isSelectionEmpty() == true)
            {
                JOptionPane.showMessageDialog(editButton, "Please SELECT an item to EDIT, in a NON_EMPTY TABLE.", "Warning", 0);
            }
            else
            {
                new EditGame(game_titles.get(gameTable.getSelectedRow()));
            }
        });

        // Delete Button
        JButton deleteButton = new JButton("Delete Game");
        deleteButton.addActionListener(e ->
        {
            int row_selected = gameTable.getSelectedRow();

            if (gameTable.getRowCount() == 0 || gameTable.getSelectionModel().isSelectionEmpty() == true)
            {
                JOptionPane.showMessageDialog(deleteButton, "Please SELECT an item to DELETE, in a NON_EMPTY TABLE.", "Warning", 0);
            }
            else
            {
                game_titles.remove(row_selected);

                DefaultTableModel model = (DefaultTableModel) gameTable.getModel();
                model.removeRow(row_selected);
            }
        });

        gameViewPanel.add(scrollView);
        gamePanel.add(addButton);
        gamePanel.add(editButton);
        gamePanel.add(deleteButton);

        // Adding Components to Frame
        gameFrame.getContentPane().add(BorderLayout.CENTER, gameViewPanel);
        gameFrame.getContentPane().add(BorderLayout.WEST, gamePanel);
        gameFrame.getContentPane().add(BorderLayout.NORTH, menBar);
        gameFrame.setVisible(true);

        // Refresh Page Every Second
        ActionListener refresher = new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt)
            {
                refreshList(game_titles, gameTable);
            }
        };

        Timer timer = new Timer(1000, refresher);
        timer.setRepeats(true);
        timer.start();
    }
}
