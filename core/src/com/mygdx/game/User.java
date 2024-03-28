package com.mygdx.game;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.HashMap;

public class User {
    public Array<Integer> birdsFound;
    public Array<Integer> itemsBought;
    public Array<Integer> itemsPlaced;
    public HashMap<Integer,Integer> feed;
    public Integer quills;

    public static FileHandle getUserfile(){
        return Gdx.files.local("user.json");
    }

    public static void save(User user){ // save file
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        FileHandle userfile = getUserfile();

        String txt = json.toJson(user);
        userfile.writeString(json.prettyPrint(txt),false);
    }

    public static void check(){ // print user file to logcat
        FileHandle userfile = getUserfile();
        String checkJson = userfile.readString();
        System.out.println(json.prettyPrint(checkJson));
    }

    public static void init(){ // on first install or to reset to default
        FileHandle userfile = getUserfile();
        User user = new User();
        user.birdsFound = new Array<>();
        user.itemsBought = new Array<>();
        user.itemsPlaced = new Array<>();
        user.feed = new HashMap<>();

        user.birdsFound.add(0);
        user.itemsBought.add(0);
        user.itemsPlaced.add(0);
        user.feed.put(0,0);
        user.quills = 0;

        String txt = json.toJson(user);
        userfile.writeString(json.prettyPrint(txt),false);
    }

}
