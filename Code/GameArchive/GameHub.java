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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class GameHub extends VideoGame
{
    // // Global Parameters
    static JFrame gameFrame = new JFrame("Game Archiver");
    static Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
    static DefaultTableModel table_core = new DefaultTableModel();
    static JTable gameTable;
    static JScrollPane scrollView;
    
    static JLabel selectedLabel = new JLabel();
    static String selectedLabelString;
    static int selectedRow;

    // Constructor
    public GameHub() { }

    public static void refreshList(LinkedList<VideoGame> games_list, JTable games_table)
    {
        Object row_items[] = new Object[6];
        DefaultTableModel model = (DefaultTableModel) games_table.getModel();
        //model.setRowCount(0);

        if (model.getRowCount() == 0 && !games_list.isEmpty())
        {
            //System.out.println("New Item");
            row_items[0] = games_list.get(0).getTitle();
            // row_items[1] = games_list.get(0).getPlatform();
            row_items[1] = games_list.get(0).getBeat();
            // row_items[3] = games_list.get(0).getTimesBeaten();
            row_items[2] = games_list.get(0).getCurrentGame();
            row_items[3] = games_list.get(0).getFavorite();

            model.addRow(row_items);
        }
        
        if (model.getRowCount() > 0)
        {
            for(int i = model.getRowCount(); i < games_list.size(); i++)
            {
                row_items[0] = games_list.get(i).getTitle();
                // row_items[1] = games_list.get(i).getPlatform();
                row_items[1] = games_list.get(i).getBeat();
                // row_items[3] = games_list.get(i).getTimesBeaten();
                row_items[2] = games_list.get(i).getCurrentGame();
                row_items[3] = games_list.get(i).getFavorite();

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
            UIManager.setLookAndFeel( new FlatIntelliJLaf() );
        } 
        catch( Exception ex ) 
        {
            System.err.println( "Failed to initialize LaF" );
        }

        // Variable Initialized
        StringBuilder strBuild = new StringBuilder();
        LinkedList<VideoGame> game_titles = new LinkedList<VideoGame>();

        // Build Frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocation(500, 150);
        gameFrame.setResizable(false);

        // MenuBar System
        JMenuBar menBar = new JMenuBar();
        JMenu item1 = new JMenu("File");
        JMenu item2 = new JMenu("Sort");
        menBar.add(item1);
        menBar.add(item2);

        JMenuItem item1Open = new JMenuItem("Open");
        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFileFilter(new FileNameExtensionFilter("Only .json files", "json", "JSON"));
        JMenuItem item1Save = new JMenuItem("Save As...");

        // File Initializer
        String[] JSONkey = {"Title", "Release Date", "Platform", "Developer(s)", "Publisher(s)", "Beat", "Times Beat", "Favorite", "Current", "Image Path"};

        item1.add(item1Open);
        item1Open.addActionListener(e ->
        {
            fileOpen.setDialogTitle("Select a JSON archive file");
            fileOpen.setCurrentDirectory(new File(".\\"));
            int approval_opt = fileOpen.showOpenDialog(null);
            if (approval_opt == JFileChooser.APPROVE_OPTION)
            {
                JSONParser jsonParse = new JSONParser();
                String filePath = fileOpen.getSelectedFile().getAbsolutePath();

                try
                {
                    JSONObject data = (JSONObject) jsonParse.parse(new FileReader(filePath));
                    JSONArray gameList = (JSONArray) data.get("Games");
                    
                    for (Object indGame : gameList)
                    {
                        JSONObject indObjGame = (JSONObject) indGame;
                        VideoGame openGame = new VideoGame();
                        
                        LinkedList<String> openPubs = new LinkedList<String>();
                        JSONArray pubsArray = (JSONArray) indObjGame.get(JSONkey[4]);
                        for (Object pubItem : pubsArray)
                        {
                            openPubs.add((String) pubItem);
                        }

                        LinkedList<String> openDevs = new LinkedList<String>();
                        JSONArray devsArray = (JSONArray) indObjGame.get(JSONkey[3]);
                        for (Object devItem : devsArray)
                        {
                            openDevs.add((String) devItem);
                        }

                        boolean openBeat = (boolean) indObjGame.get(JSONkey[5]);
                        boolean openFave = (boolean) indObjGame.get(JSONkey[7]);
                        boolean openCurr = (boolean) indObjGame.get(JSONkey[8]);

                        String openTitle = (String) indObjGame.get(JSONkey[0]);
                        String openPlatf = (String) indObjGame.get(JSONkey[2]);
                        String openImg = (String) indObjGame.get(JSONkey[9]);
                        String openStringRelease = (String) indObjGame.get(JSONkey[1]);
                        long openTimesBeat = (long) indObjGame.get(JSONkey[6]);

                        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
                        Date openRelease = sdf.parse(openStringRelease);

                        openGame.buildVideoGame(openTitle, openDevs, openPubs, openRelease, openPlatf, (int) openTimesBeat, openBeat, openFave, openCurr, openImg);
                        game_titles.add(openGame);
                    }
                }
                catch (Exception except)
                {
                    except.printStackTrace();
                }
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
                gameObj.put(JSONkey[0], game_titles.get(i).getTitle());
                gameObj.put(JSONkey[1], game_titles.get(i).getDate().toString());
                gameObj.put(JSONkey[2], game_titles.get(i).getPlatform());
                gameObj.put(JSONkey[3], game_titles.get(i).getDevelopers());
                gameObj.put(JSONkey[4], game_titles.get(i).getPublishers());
                gameObj.put(JSONkey[5], game_titles.get(i).getBeat());
                gameObj.put(JSONkey[6], game_titles.get(i).getTimesBeaten());
                gameObj.put(JSONkey[7], game_titles.get(i).getFavorite());
                gameObj.put(JSONkey[8], game_titles.get(i).getCurrentGame());
                gameObj.put(JSONkey[9], game_titles.get(i).getImagePath());

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

        // Build Panel
        Container gameViewPane = new Container();
        gameViewPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        gameViewPane.setLayout(new GridBagLayout());

        Container gameHolder = new Container();
        gameHolder.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        gameHolder.setLayout(new GridBagLayout());

        Container gamePane = new Container();
        gamePane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        gamePane.setLayout(new GridBagLayout());

        Container gameDisplayPane = new Container();
        gameDisplayPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        gameDisplayPane.setLayout(new GridBagLayout());

        // Grid Items
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weightx = 0.5;
        grid.ipadx = 10;
        grid.ipady = 10;

        Insets defauIn = new Insets(2, 5, 2, 5);    
          
        // Components
        ImageIcon coverArt = new ImageIcon();
        JLabel coverArtCont = new JLabel(coverArt);
        JLabel titleLabel = new JLabel("TITLE");
        titleLabel.setFont(new Font("Arial", 0, 24));
        JLabel dateLabel = new JLabel("RELEASE DATE");
        dateLabel.setFont(new Font("Arial", 0, 12));
        JLabel platformLabel = new JLabel("PLATFORM");
        platformLabel.setFont(new Font("Arial", 0, 12));
        JLabel devLabel = new JLabel("DEVELOPERS");
        devLabel.setFont(new Font("Arial", 0, 12));
        JLabel pubLabel = new JLabel("PUBLISHERS");
        pubLabel.setFont(new Font("Arial", 0, 12));

        // Table
        String[] col_names = {"Title", "Beaten", "Favorite", "Currently Playing"}; // , "Platform", "Times Beaten"
        table_core.addColumn(col_names[0]);
        table_core.addColumn(col_names[1]);
        table_core.addColumn(col_names[2]);
        table_core.addColumn(col_names[3]);

        gameTable = new JTable(table_core)
        {
            public boolean editCellAt(int row, int column, java.util.EventObject e)
            {
                return false;
            }
        };
        gameTable.getTableHeader().setReorderingAllowed(false);
        gameTable.getTableHeader().setResizingAllowed(false);

        gameTable.getColumnModel().getColumn(0).setPreferredWidth(screenDimension.width/5);
        scrollView = new JScrollPane(gameTable);

        gameTable.addMouseListener(new MouseAdapter() 
        {
            public void mouseClicked(MouseEvent e)
            {
                // Row Selection Per Click
                selectedRow = gameTable.getSelectedRow();
                System.out.println("Selected Row: " + selectedRow);

                selectedLabelString = (selectedRow + ": " + game_titles.get(selectedRow).getTitle());
                selectedLabel.setText(selectedLabelString);
                
                // Display Text Update
                titleLabel.setText(game_titles.get(selectedRow).getTitle());
                coverArtCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\img_000.jpg").
                getImage().getScaledInstance((screenDimension.width/8), (screenDimension.height/4), Image.SCALE_AREA_AVERAGING)));

                dateLabel.setText(game_titles.get(selectedRow).getPrintDate());
                platformLabel.setText(game_titles.get(selectedRow).getPlatform());
                devLabel.setText("DEVS");
                pubLabel.setText("PUBS");
            }
        });

        // Button
        JButton addButton = new JButton("Add Game");
        addButton.addActionListener(e ->
        {
            VideoGame newGame = new VideoGame();
            new AddGame(newGame, game_titles);
        });
        JButton editButton = new JButton("Edit Game");
        editButton.addActionListener(e ->
        {
            int row_selected = selectedRow;

            if (gameTable.getRowCount() == 0)
            {
                JOptionPane.showMessageDialog(editButton, "Please SELECT an item to EDIT, in a NON_EMPTY TABLE.", "Warning", 0);
            }
            else
            {
                DefaultTableModel model = (DefaultTableModel) gameTable.getModel();
                new EditGame(game_titles.get(row_selected), game_titles, row_selected, model);
            }
        });
        JButton deleteButton = new JButton("Delete Game");
        deleteButton.addActionListener(e ->
        {
            int row_selected = selectedRow;

            if (gameTable.getRowCount() == 0 )
            {
                JOptionPane.showMessageDialog(deleteButton, "Please SELECT an item to DELETE, in a NON_EMPTY TABLE.", "Warning", 0);
            }
            else
            {
                if (game_titles.get(row_selected).getFavorite() == true || game_titles.get(row_selected).getCurrentGame() == true)
                {
                    JOptionPane.showMessageDialog(deleteButton, "You can not DELETE a FAVORITED/CURRENT PLAYING game.", "Warning", 0);
                }
                else
                {
                    game_titles.remove(row_selected);

                    DefaultTableModel model = (DefaultTableModel) gameTable.getModel();
                    model.removeRow(row_selected);
                }
            }
        });

        // Grid Layout Setup
        grid.anchor = GridBagConstraints.PAGE_START;
        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 1.0;
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = defauIn;
        gameViewPane.add(scrollView, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = defauIn;
        gameViewPane.add(selectedLabel, grid);

        grid.anchor = GridBagConstraints.PAGE_START;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weighty = 1.0;
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = defauIn;
        gamePane.add(addButton, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        grid.insets = defauIn;
        gamePane.add(editButton, grid);

        grid.gridx = 2;
        grid.gridy = 0;
        grid.insets = defauIn;
        gamePane.add(deleteButton, grid);

        grid.anchor = GridBagConstraints.PAGE_END;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridwidth = 1;
        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 1;
        gameDisplayPane.add(titleLabel, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 2;
        gameDisplayPane.add(coverArtCont, grid);
    
        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 3;
        gameDisplayPane.add(dateLabel, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 4;
        gameDisplayPane.add(platformLabel, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 5;
        gameDisplayPane.add(devLabel, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 6;
        gameDisplayPane.add(pubLabel, grid);

        grid.anchor = GridBagConstraints.PAGE_START;
        grid.weighty = 1.0;
        grid.gridx = 0;
        grid.gridy = 0;
        gameHolder.add(gamePane, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 1;
        gameHolder.add(gameDisplayPane, grid);


        // Adding Components to Frame
        gameFrame.getContentPane().add(BorderLayout.CENTER, gameViewPane);
        gameFrame.getContentPane().add(BorderLayout.WEST, gameHolder);
        gameFrame.getContentPane().add(BorderLayout.NORTH, menBar);
        gameFrame.pack();
        gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameFrame.setVisible(true);

        // Refresh Page Every Second
        ActionListener refresher = new ActionListener() 
        {
            public void actionPerformed(ActionEvent evt)
            {
                refreshList(game_titles, gameTable);
            }
        };

        Timer timer = new Timer(500, refresher);
        timer.setRepeats(true);
        timer.start();
    }
}
