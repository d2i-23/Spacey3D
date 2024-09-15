package com.src;
import javafx.stage.Stage;

public class Settings {
    
    private static final int windowWidth = 1200;
    private static final int windowHeight = 800;
    private static final String bFontColor = "#c9c7c7";
    private static final String fontColor = "#dbd9d9";
    private static final String canvasColor = "#111112";//"#202224";
    private static final String scrollBoxColor = "#141414"; 
    private static final String UIPaneColor =  "#1d1e1f"; 

    public static int getWindowWidth(){
        return windowWidth; 
    }

    public static int getWindowHeight(){
        return windowHeight; 
    }

    public static String getbFontColor(){
        return bFontColor; 
    }
    
    public static String getfontColor(){
        return fontColor; 
    }

    public static String getCanvasColor(){
        return canvasColor; 
    }

    public static String getScrolLBoxColor(){
        return scrollBoxColor; 
    }
    

    public static String getUIPaneColor(){
        return UIPaneColor; 
    }
    

}
