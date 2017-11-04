/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platformer.game;

import com.badlogic.gdx.Screen;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author SI
 */
public class ScreenManager {
    Map<String,Screen> screenMap;
    
    private static ScreenManager instance;
    
    public static ScreenManager getInstance(){
        if ( instance == null ){
            instance = new ScreenManager();
        }
        return instance;
    }
    private ScreenManager(){
        screenMap = new TreeMap<String,Screen>();
    }
    public void insertScreen(String name, Screen screen){
        screenMap.put(name, screen);
        
    }
    public Screen getScreen(String name){
        return screenMap.get(name);
    }
}
