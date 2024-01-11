/*
 * Developer -   Jace L. Zavarelli
 * Company   -   Z.A. Entertainment LLC.
 * Project   -   Game Archiver :: Video Game Object
 * 
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class VideoGame 
{
    // Global Variables -- OBSOLETE
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private String Title_0;
    private String ImgPath_0;
    private LinkedList<String> Developers_0;
    private LinkedList<String> Publishers_0;
    private Date Release_0;
    private String StringRelease_0;
    private String Platform_0;
    private Boolean Beaten_0;
    private Integer Times_Beaten_0;
    private Boolean Favorite_0;
    private Boolean CurrentGame_0;

    // Constructor
    public VideoGame() { }

    // Init Build
    public void buildVideoGame(String title, LinkedList<String> devs, LinkedList<String> pubs, Date release, String platform, int times_beat, boolean beat, boolean fave, boolean curr, String img)
    {
        // Initialize Values in Title
        setTitle(title);
        setImagePath(img);
        setDevelopers(devs);
        setPublishers(pubs);
        setDate(release);
        setPlatform(platform);
        setTimesBeaten(times_beat);
        setBeat(beat);
        setFavorite(fave);
        setCurrentGame(curr);
    }

    // Print
    public void print()
    {
        System.out.println(Title_0 + " " + Platform_0 + " " + StringRelease_0 + " " + Beaten_0 + " " + Times_Beaten_0 + " " + Favorite_0);
    }

    // Title
    public void setTitle(String title) { Title_0 = title; } 

    protected String getTitle() { return Title_0; }

    // Image
    public void setImagePath(String imagePath) { ImgPath_0 = imagePath; }

    protected String getImagePath() { return ImgPath_0; }

    // Developers of Title
    public void setDevelopers(LinkedList<String> devs) { Developers_0 = devs; } 

    protected LinkedList<String> getDevelopers() { return Developers_0; }

    // Publishers of Title
    public void setPublishers(LinkedList<String> pubs) { Publishers_0 = pubs; } 

    protected LinkedList<String> getPublishers() { return Publishers_0; }

    // Release Date
    public void setDate(Date release) 
    { 
        Release_0 = release;
        StringRelease_0 = sdf.format(Release_0);
    }

    public String getPrintDate() { return StringRelease_0; }

    protected Date getDate() { return Release_0; }

    // Platform of Title
    public void setPlatform(String platform) { Platform_0 = platform; }

    protected String getPlatform() { return Platform_0; }
 
    // Times Beaten
    public void setTimesBeaten(Integer times_beaten) { Times_Beaten_0 = times_beaten; }

    protected Integer getTimesBeaten() { return Times_Beaten_0; }

    // Beaten or Not
    public void setBeat(boolean beaten) { Beaten_0 = beaten; }

    protected Boolean getBeat() { return Beaten_0; }

    // Favorite or Not
    public void setFavorite(boolean fave) { Favorite_0 = fave; }

    protected Boolean getFavorite() { return Favorite_0; }

    // Current Game or Not
    public void setCurrentGame(boolean curr) { CurrentGame_0 = curr; }

    protected Boolean getCurrentGame() { return CurrentGame_0; }
}
