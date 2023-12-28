/*
 * Developer -   Jace L. Zavarelli
 * Company   -   Z.A. Entertainment LLC.
 * Project   -   Game Archiver
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

import org.json.simple.JSONObject;
import com.formdev.flatlaf.FlatLightLaf;

public class GameHub extends VideoGame
{
    // // Label Final Variables
    private final static String TITLE_FIELD = "TITLE:";
    private final static String DEV_FIELD   = "DEVELOPER'S:";
    private final static String DATE_FIELD  = "DATE:";
    private final static String PLATF_FIELD = "PLATFORM:";
    private final static String BEAT_FIELD  = "TIMES_BEATEN:";

    // Constructor
    public GameHub() { }

    // // Initializer -- Used to load the Linked List from the JSON file, also used to reinitialize after each added item to JSON.
    // public static LinkedList<VideoGame> initializer(LinkedList<VideoGame> game_titles)
    // {
    //     return game_titles;
    // }

    // Loader -- Used to load new Linked List Title Entry into Loader, used every time a game is added. 
    public static void loader()
    {

    }

    public static void refreshList(LinkedList<VideoGame> games_list, DefaultTableModel table_model)
    {
        Object[] row;
        for (int r = 0; r <= games_list.size(); r++)
        {
            if (table_model.getRowCount() <= 0)
            {
                                row = new Object[4];
                row[0] = games_list.get(r).getTitle();
                row[1] = games_list.get(r).getPlatform();
                row[2] = games_list.get(r).getBeat();
                row[3] = games_list.get(r).getTimesBeaten();

                table_model.addRow(row);
            }
            else if (games_list.get(r).getTitle().equals(table_model.getValueAt(r, 0)))
            {
                row = new Object[4];
                row[0] = games_list.get(r).getTitle();
                row[1] = games_list.get(r).getPlatform();
                row[2] = games_list.get(r).getBeat();
                row[3] = games_list.get(r).getTimesBeaten();

                table_model.addRow(row);
            }
        }

    }

    // Main Method
    public static void main(String[] args)
    {
        // LaF Initialization
        try 
        {
            UIManager.setLookAndFeel( new FlatLightLaf() );

        } catch( Exception ex ) 
        {
            System.err.println( "Failed to initialize LaF" );
        }

        // List Initialized
        LinkedList<VideoGame> game_titles = new LinkedList<VideoGame>();
        //game_titles = initializer(game_titles);

        // Build Frame
        JFrame gameFrame = new JFrame("Game Archiver");
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
        JMenuItem item1Save = new JMenuItem("Save As...");
        item1.add(item1Open);
        item1.add(item1Save);

        JMenuItem item2Light = new JMenuItem("Light Theme");
        JMenuItem item2Dark = new JMenuItem("Dark Theme");
        item2.add(item2Light);
        item2.add(item2Dark);

        // Build Panel
        JPanel gameViewPanel = new JPanel();

        String[] col_names = {"Title", "Platform", "Beaten", "Times Beaten", "Currently Playing", "Favorite"};
        DefaultTableModel table_core = new DefaultTableModel();
        table_core.addColumn(col_names[0]);
        table_core.addColumn(col_names[1]);
        table_core.addColumn(col_names[2]);
        table_core.addColumn(col_names[3]);
        table_core.addColumn(col_names[4]);
        table_core.addColumn(col_names[5]);

        JTable gameTable = new JTable(table_core);
        gameTable.setColumnSelectionAllowed(false);
        
        JScrollPane scrollView = new JScrollPane(gameTable);

        JPanel gamePanel = new JPanel();
        JButton addButton = new JButton("Add Game");
        addButton.addActionListener(e ->
        {
            
            VideoGame newGame = new VideoGame();
            new AddGame(newGame);

            game_titles.add(newGame);
        });

        JButton editButton = new JButton("Edit Game");
        // editButton.addActionListener(e ->
        // {
        // });

        JButton deleteButton = new JButton("Delete Game");
        // deleteButton.addActionListener(e ->
        // {
        // });

        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e ->
        {
            DefaultTableModel model = (DefaultTableModel) gameTable.getModel();
            refreshList(game_titles, model);
            gameTable.setModel(model);

            
            gameFrame.revalidate();
            gameFrame.repaint();
        });

        gameViewPanel.add(scrollView);
        gamePanel.add(refreshButton);
        gamePanel.add(addButton);
        gamePanel.add(editButton);
        gamePanel.add(deleteButton);

        // Adding Components to Frame
        gameFrame.getContentPane().add(BorderLayout.CENTER, gameViewPanel);
        gameFrame.getContentPane().add(BorderLayout.WEST, gamePanel);
        gameFrame.getContentPane().add(BorderLayout.NORTH, menBar);
        gameFrame.setVisible(true);

    }
}
