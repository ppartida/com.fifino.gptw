package com.fifino.gptw;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import com.fifino.framework.implementation.AndroidEntity;
import com.fifino.framework.simple.SimpleScreen;
import com.fifino.gptw.screens.GPTWScreen;
import com.fifino.gptw.screens.LoadingScreen;
import com.fifino.gptw.screens.MainMenu;
import com.kilobolt.framework.Game;
import com.kilobolt.framework.Screen;
import com.kilobolt.framework.implementation.AndroidAudio;
import com.kilobolt.framework.implementation.AndroidFastRenderView;
import com.kilobolt.framework.implementation.AndroidFileIO;
import com.kilobolt.framework.implementation.AndroidGame;
import com.kilobolt.framework.implementation.AndroidGraphics;
import com.kilobolt.framework.implementation.AndroidInput;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class GPTWGame extends AndroidGame {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        onCreateAndroidGame();
        AndroidEntity.MODE = AndroidEntity.Mode.PROD;
        // AndroidEntity.MODE = AndroidEntity.Mode.DEBUG;
        rnd = new Random();
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public Screen getInitScreen() {
    	return new LoadingScreen(this);
    }
    public boolean getIsPortrait(){
        return this.isPortrait;
    }
    boolean isPortrait = true;
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        	isPortrait = false;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
        	isPortrait = true;
        }
        resetFrameBuffer(isPortrait);
        System.out.println(isPortrait ? "PORTRAIT":"LANDSCAPE");
    }
    private Class[] gamesPool = new Class[]{
            com.fifino.gptw.screens.games.TurnOffAirs.class,
            com.fifino.gptw.screens.games.TurnOffAlarms.class
    };
    Random rnd;
    int lastGame = -1;
    int level = 1;
    public Screen getNextScreen(){
        try {
            //Ensures not to get the same game again.
            int nextGame = -1;
            do{
                nextGame = rnd.nextInt(gamesPool.length);
            }while(lastGame == nextGame);
            lastGame = nextGame;

            //Creates a new instance of the game
//            String className = gamesPool[nextGame];
//            Class cl = null;
//                cl = Class.forName(className);
            Class cl = gamesPool[nextGame];
            Constructor con = cl.getConstructor(Game.class, Integer.class);
            Object xyz = con.newInstance(this, ++level);
            return (GPTWScreen) xyz;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }
}