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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.formdev.flatlaf.FlatIntelliJLaf;

class VGcompareZA implements Comparator<VideoGame>
{
    public int compare(VideoGame in1, VideoGame in2)
    {
        String Title1 = in1.getTitle(), Title2 = in2.getTitle();
        
        if (Title1.charAt(0) == Title2.charAt(0))
        {
            if (Title1.charAt(1) == Title2.charAt(1))
            {
                if (Title1.charAt(2) == Title2.charAt(2))
                {
                    if (Title1.charAt(3) == Title2.charAt(3))
                    {
                        return 0;
                    }
                    else if (Title1.charAt(3) > Title2.charAt(3))
                    {
                        return -1;
                    }
                    else
                    {
                        return 1;
                    }

                }
                else if (Title1.charAt(2) > Title2.charAt(2))
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
            else if (Title1.charAt(1) > Title2.charAt(1))
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }
        else if (Title1.charAt(0) > Title2.charAt(0))
        {
            return -1;
        }
        else
        {
            return 1;
        }
    }
}

class VGcompareAZ implements Comparator<VideoGame>
{
    public int compare(VideoGame in1, VideoGame in2)
    {
        String Title1 = in1.getTitle(), Title2 = in2.getTitle();
        
        if (Title1.charAt(0) == Title2.charAt(0))
        {
            if (Title1.charAt(1) == Title2.charAt(1))
            {
                if (Title1.charAt(2) == Title2.charAt(2))
                {
                    if (Title1.charAt(3) == Title2.charAt(3))
                    {
                        return 0;
                    }
                    else if (Title1.charAt(3) > Title2.charAt(3))
                    {
                        return 1;
                    }
                    else
                    {
                        return -1;
                    }

                }
                else if (Title1.charAt(2) > Title2.charAt(2))
                {
                    return 1;
                }
                else
                {
                    return -1;
                }
            }
            else if (Title1.charAt(1) > Title2.charAt(1))
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
        else if (Title1.charAt(0) > Title2.charAt(0))
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}

class VGcompareP implements Comparator<VideoGame>
{
    public int compare(VideoGame in1, VideoGame in2)
    {
        String Plat1 = in1.getPlatform(), Plat2 = in2.getPlatform();
        
        if (Plat1.charAt(0) == Plat2.charAt(0))
        {
            return 0;
        }
        else if (Plat1.charAt(0) > Plat2.charAt(0))
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
}

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
    static boolean fileOpening = false;
    static boolean fileSorting = false;
    static int selectedRow;

    // Constructor
    public GameHub() { }

    public static void refreshList(LinkedList<VideoGame> games_list, JTable games_table)
    {
        Object row_items[] = new Object[4];
        DefaultTableModel model = (DefaultTableModel) games_table.getModel();
        if (fileOpening == true || fileSorting == true)
        {
            model.setRowCount(0);
        }

        if (model.getRowCount() == 0 && !games_list.isEmpty())
        {
            row_items[0] = games_list.get(0).getTitle();
            if (games_list.get(0).getBeat() == true)
            {
                row_items[1] = "Have";
            }
            else
            {
                row_items[1] = "Have Not";
            }

            if (games_list.get(0).getFavorite() == true)
            {
                row_items[2] = "Favorited";
            }
            else
            {
                row_items[2] = "Not Favorited";
            }

            if (games_list.get(0).getCurrentGame() == true)
            {
                row_items[3] = "Yes";
            }
            else
            {
                row_items[3] = "No";
            }
            model.addRow(row_items);
        }
        
        if (model.getRowCount() > 0)
        {
            for(int i = model.getRowCount(); i < games_list.size(); i++)
            {
                row_items[0] = games_list.get(i).getTitle();
                if (games_list.get(i).getBeat() == true)
                {
                    row_items[1] = "Have";
                }
                else
                {
                    row_items[1] = "Have Not";
                }

                if (games_list.get(i).getFavorite() == true)
                {
                    row_items[2] = "Favorited";
                }
                else
                {
                    row_items[2] = "Not Favorited";
                }

                if (games_list.get(i).getCurrentGame() == true)
                {
                    row_items[3] = "Yes";
                }
                else
                {
                    row_items[3] = "No";
                }
                model.addRow(row_items);
                row_items = new Object[4];
            }
        }

        for (int h = 0; h < games_list.size(); h++)
        {
            model.setValueAt(games_list.get(h).getTitle(), h, 0);
            if (games_list.get(h).getBeat() == true)
            {
                model.setValueAt("Have", h, 1);
            }
            else
            {
                model.setValueAt("Have Not", h, 1);
            }

            if (games_list.get(h).getFavorite() == true)
            {
                model.setValueAt("Favorited", h, 2);
            }
            else
            {
                model.setValueAt("Not Favorited", h, 2);
            }

            if (games_list.get(h).getCurrentGame() == true)
            {
                model.setValueAt("Yes", h, 3);
            }
            else
            {
                model.setValueAt("No", h, 3);
            }
        }
        gameFrame.revalidate();
        gameFrame.repaint();
    }

    // Main Method
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception
    {

    /* ------------------------------------------------------------------------------------------------------ */
                
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
        String[] JSONkey = {"Title", "Release Date", "Platform", "Developer(s)", "Publisher(s)", "Beat", "Times Beat", "Favorite", "Current", "Image Path"};

    /* ------------------------------------------------------------------------------------------------------ */
                
        // JSON DATA initialization
        JSONParser jsonDataParse = new JSONParser();
        String fileDataPath = (".\\Data\\data.json");
        try
        {
            JSONObject data = (JSONObject) jsonDataParse.parse(new FileReader(fileDataPath));
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

    /* ------------------------------------------------------------------------------------------------------ */
            
        // Build Frame
        JFrame.setDefaultLookAndFeelDecorated(true);
        Image icon = Toolkit.getDefaultToolkit().getImage(".\\Images\\icon_00.png");
        gameFrame.setIconImage(icon);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocation(500, 150);
        gameFrame.setResizable(false);

        // MenuBar System
        JMenuBar menBar = new JMenuBar();
        JMenu item1 = new JMenu("File");
        JMenu item2 = new JMenu("Sort");
        JMenuItem item3 = new JMenuItem("Search");

    /* ------------------------------------------------------------------------------------------------------ */
                
        // File Initialization
        menBar.add(item1);
        item1.setPreferredSize(new Dimension(item1.getMinimumSize().width, item1.getPreferredSize().height));
        JMenuItem item1Open = new JMenuItem("Open");
        JMenuItem item1Save = new JMenuItem("Save As...");

        JFileChooser fileOpen = new JFileChooser();
        fileOpen.setFileFilter(new FileNameExtensionFilter("Only .json files", "json", "JSON"));

        item1.add(item1Open);
        item1Open.addActionListener(e ->
        {
            fileOpen.setDialogTitle("Select a JSON archive file");
            fileOpen.setCurrentDirectory(new File(".\\"));
            int approval_opt = fileOpen.showOpenDialog(null);
            if (approval_opt == JFileChooser.APPROVE_OPTION)
            {
                game_titles.clear();
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
                        fileOpening = true;
                        game_titles.add(openGame);
                    }
                }
                catch (Exception except)
                {
                    except.printStackTrace();
                }
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
        });

    /* ------------------------------------------------------------------------------------------------------ */
       
        // Sort Initialization
        menBar.add(item2);
        item2.setPreferredSize(new Dimension(item2.getMinimumSize().width, item2.getPreferredSize().height));
        JMenuItem item2SortLetterA = new JMenuItem("Sort A - Z");
        JMenuItem item2SortLetterZ = new JMenuItem("Sort Z - A");
        JMenuItem item2SortPlatf = new JMenuItem("Sort by Platform");
        JMenuItem item2SortDateA = new JMenuItem("Sort by Recent Release");
        JMenuItem item2SortDateZ = new JMenuItem("Sort by Latest Release");

        item2.add(item2SortLetterA);
        item2SortLetterA.addActionListener(e ->
        {
            // JOptionPane.showMessageDialog(item2SortLetterA, "Not Implemented", "Warning", 0);
            Collections.sort(game_titles, new VGcompareAZ());
        });
        item2.add(item2SortLetterZ);
        item2SortLetterZ.addActionListener(e ->
        {
            // JOptionPane.showMessageDialog(item2SortLetterZ, "Not Implemented", "Warning", 0);
            Collections.sort(game_titles, new VGcompareZA());
        });

        item2.add(item2SortPlatf);
        item2SortPlatf.addActionListener(e ->
        {
            // JOptionPane.showMessageDialog(item2SortPlatf, "Not Implemented", "Warning", 0);
            Collections.sort(game_titles, new VGcompareP());
        });

        item2.add(item2SortDateA);
        item2SortDateA.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(item2SortDateA, "Not Implemented", "Warning", 0);
        });

        item2.add(item2SortDateZ);
        item2SortDateZ.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(item2SortDateZ, "Not Implemented", "Warning", 0);
        });

    /* ------------------------------------------------------------------------------------------------------ */

        // Search Initialization
        menBar.add(item3);
        item3.setPreferredSize(new Dimension(50, item3.getPreferredSize().height));
        item3.setMaximumSize(new Dimension(50, item3.getPreferredSize().height));
        item3.setHorizontalTextPosition(0);

        item3.addActionListener(e ->
        {
            String gameTitle = JOptionPane.showInputDialog(item3, "Enter a game TITLE to find");

            for (int i = 0; i < game_titles.size(); i++)
            {
                if (gameTitle.equals(game_titles.get(i).getTitle()))
                {
                    selectedRow = i;
                    gameTable.clearSelection();
                    gameTable.changeSelection(selectedRow, 0, true, false);
                    break;
                }
                else
                {
                    gameTable.clearSelection();
                }
            }
        });

    /* ------------------------------------------------------------------------------------------------------ */

        // Build Panel
        Container gameViewPane = new Container();
        gameViewPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        gameViewPane.setLayout(new GridBagLayout());

        Container gameDisplayPane = new Container();
        gameDisplayPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        gameDisplayPane.setLayout(new GridBagLayout());
        gameDisplayPane.setMaximumSize(new Dimension(screenDimension.width/7, screenDimension.height));

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
        //coverArtCont.setBorder(BorderFactory.createEtchedBorder());
        JLabel titleLabel = new JLabel("TITLE");
        titleLabel.setPreferredSize(new Dimension(screenDimension.width/16, screenDimension.height/18));
        titleLabel.setFont(new Font("Arial", 0, 24));
        //titleLabel.setBorder(BorderFactory.createEtchedBorder());
        JLabel dateLabel = new JLabel("RELEASE DATE");
        dateLabel.setFont(new Font("Arial", 0, 16));
        //dateLabel.setBorder(BorderFactory.createEtchedBorder());
        JLabel platformLabel = new JLabel("PLATFORM");
        platformLabel.setFont(new Font("Arial", 0, 16));
        //platformLabel.setBorder(BorderFactory.createEtchedBorder());
        ImageIcon platformImg = new ImageIcon();
        JLabel platformImgCont = new JLabel(platformImg);
        //platformImgCont.setBorder(BorderFactory.createEtchedBorder());
        JLabel devLabel = new JLabel("DEVELOPERS");
        devLabel.setPreferredSize(new Dimension(screenDimension.width/18, screenDimension.height/24));
        devLabel.setFont(new Font("Arial", 0, 16));
        //devLabel.setBorder(BorderFactory.createEtchedBorder());
        JLabel pubLabel = new JLabel("PUBLISHERS");
        pubLabel.setPreferredSize(new Dimension(screenDimension.width/18, screenDimension.height/24));
        pubLabel.setFont(new Font("Arial", 0, 16));
        //pubLabel.setBorder(BorderFactory.createEtchedBorder());
        JLabel numBeatLabel = new JLabel("BEATEN");
        numBeatLabel.setFont(new Font("Arial", 0, 16));
        //numBeatLabel.setBorder(BorderFactory.createEtchedBorder());
        ImageIcon numBeatImg = new ImageIcon();
        JLabel numBeatImgCont = new JLabel(numBeatImg);
        //numBeatImgCont.setBorder(BorderFactory.createEtchedBorder());

        // Table
        String[] col_names = {"Title", "Beaten", "Favorite", "Currently Playing"};
        table_core.addColumn(col_names[0]);
        table_core.addColumn(col_names[1]);
        table_core.addColumn(col_names[2]);
        table_core.addColumn(col_names[3]);

        gameTable = new JTable(table_core)
        {
            public boolean editCellAt(int row, int column, java.util.EventObject e) { return false; }
        };

        gameTable.getTableHeader().setReorderingAllowed(false);
        gameTable.getTableHeader().setResizingAllowed(false);

        gameTable.getColumnModel().getColumn(0).setPreferredWidth(screenDimension.width/5);
        gameTable.changeSelection(0, 0, true, false);
        scrollView = new JScrollPane(gameTable);

        gameTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                // Row Selection Per Click
                selectedRow = gameTable.getSelectedRow();

                selectedLabelString = (selectedRow + ": " + game_titles.get(selectedRow).getTitle());
                selectedLabel.setText(selectedLabelString);
                
                // Display Text Update
                titleLabel.setText("<html>" + game_titles.get(selectedRow).getTitle() + "</html>");
                coverArtCont.setIcon(new ImageIcon(new ImageIcon(game_titles.get(selectedRow).getImagePath()).
                getImage().getScaledInstance(256, 300, Image.SCALE_AREA_AVERAGING)));
                dateLabel.setText("Date: " + game_titles.get(selectedRow).getPrintDate());

                platformLabel.setText("Platform: " + game_titles.get(selectedRow).getPlatform());
                switch(game_titles.get(selectedRow).getPlatform())
                {
                    case ("PC"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\pc_00.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("PSP"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\psp_01.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("PSP Vita"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\psvita_02.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Playstation 1"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\playstation_99.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Playstation 2"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\playstation_99.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Playstation 3"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\playstation_99.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Playstation 4"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\playstation4_06.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Playstation 5"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\playstation_99.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Xbox Original"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\xbox_08.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Xbox 360"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\xbox360_09.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Xbox One"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\xboxone_10.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Xbox Series X"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\xbox_99.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("NES"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\nes_12.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("GameBoy"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\gameboy_13.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("SNES"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\snes_14.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Nintendo 64"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\n64_15.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("GameCube"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\gamecube_16.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("DS"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\ds_17.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Wii"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\wii_18.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Wii-U"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\nintendo_99.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("3DS"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\3ds_20.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    case ("Nintendo Switch"):
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\switch_21.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;

                    default:
                        platformImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\pc_00.png").
                        getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                        break;
                }

                String devDisplayStr = "", pubDisplayStr = "";
                for (int i = 0; i < game_titles.get(selectedRow).getDevelopers().size(); i++)
                {
                    if (i == game_titles.get(selectedRow).getDevelopers().size() - 1)
                    {
                        devDisplayStr += (game_titles.get(selectedRow).getDevelopers().get(i));
                    }
                    else
                    {
                        devDisplayStr += (game_titles.get(selectedRow).getDevelopers().get(i)+ ", ");
                    }
                }

                for (int l = 0; l < game_titles.get(selectedRow).getPublishers().size(); l++)
                {
                    if (l == game_titles.get(selectedRow).getPublishers().size() - 1)
                    {
                        pubDisplayStr += (game_titles.get(selectedRow).getPublishers().get(l));
                    }
                    else
                    {
                        pubDisplayStr += (game_titles.get(selectedRow).getPublishers().get(l)+ ", ");
                    } 
                }
                devLabel.setText("<html>Developer(s): " + devDisplayStr + "</html>");
                pubLabel.setText("<html>Publisher(s): " + pubDisplayStr + "</html>");

                numBeatLabel.setText("Times Completed: " + game_titles.get(selectedRow).getTimesBeaten());
                if (game_titles.get(selectedRow).getTimesBeaten() == 0)
                {
                    numBeatImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\beat_00.png").
                    getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                }
                else
                {
                    numBeatImgCont.setIcon(new ImageIcon(new ImageIcon(".\\Images\\beat_01.png").
                    getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING)));
                }          
            }
        });

        // Buttons - Listeners and Components
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
                new EditGame(game_titles.get(row_selected), game_titles, row_selected);
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
        grid.gridwidth = 1;
        grid.weighty = 1.0;
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = defauIn;
        gameDisplayPane.add(addButton, grid);

        grid.gridx = 1;
        grid.gridy = 0;
        grid.insets = defauIn;
        gameDisplayPane.add(editButton, grid);

        grid.gridx = 2;
        grid.gridy = 0;
        grid.insets = defauIn;
        gameDisplayPane.add(deleteButton, grid);

        grid.anchor = GridBagConstraints.PAGE_END;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(10, 10, 10, 1);
        grid.gridwidth = 3;
        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 1;
        gameDisplayPane.add(titleLabel, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 2;
        gameDisplayPane.add(coverArtCont, grid);
    
        grid.weighty = 0.0;
        grid.gridwidth = 1;
        grid.gridx = 0;
        grid.gridy = 3;
        gameDisplayPane.add(dateLabel, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 4;
        gameDisplayPane.add(platformLabel, grid);

        grid.weighty = -1.0;
        grid.gridx = 2;
        grid.gridy = 4;
        gameDisplayPane.add(platformImgCont, grid);

        grid.weighty = 0.0;
        grid.gridwidth = 3;
        grid.gridx = 0;
        grid.gridy = 5;
        gameDisplayPane.add(devLabel, grid);

        grid.weighty = 0.0;
        grid.gridx = 0;
        grid.gridy = 6;
        gameDisplayPane.add(pubLabel, grid);

        grid.insets = new Insets(20, 10, 25, 0);
        grid.weighty = 0.0;
        grid.gridwidth = 1;
        grid.gridx = 0;
        grid.gridy = 7;
        gameDisplayPane.add(numBeatLabel, grid);

        grid.weighty = 0.0;
        grid.gridheight = 2;
        grid.gridx = 2;
        grid.gridy = 7;
        gameDisplayPane.add(numBeatImgCont, grid);

        // Adding Components to Frame
        gameFrame.getContentPane().add(BorderLayout.CENTER, gameViewPane);
        gameFrame.getContentPane().add(BorderLayout.WEST, gameDisplayPane);
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
