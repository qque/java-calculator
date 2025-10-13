package calculator;

import java.awt.Font;

public enum Settings {
    instance;

    // setting w/ default values
    private String displayFontName = "SansSerif";
    private int displayFontStyle = 1; // 0 = plain, 1 = bold, 2 = italics, 3 = bold & italics
    private int displayFontSize = 18;
    private Font displayFont = new Font(displayFontName, displayFontStyle, displayFontSize);

    private String sideButtonFontName = "SansSerif";
    private int sideButtonFontStyle = 1;
    private int sideButtonFontSize = 18;
    private Font sideButtonFont = new Font(sideButtonFontName, sideButtonFontStyle, sideButtonFontSize);

    private String gridButtonFontName = "SansSerif";
    private int gridButtonFontStyle = 1;
    private int gridButtonFontSize = 18;
    private Font gridButtonFont = new Font(gridButtonFontName, gridButtonFontStyle, gridButtonFontSize);
    // ...other settings (todo)


    // initialization for default values
    public void setDefault() {
        //set...()
    }


    // getters/setters for setting vars, todo when more settings are added
    //public String 
}
