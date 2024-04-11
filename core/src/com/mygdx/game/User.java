package com.mygdx.game;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.TimeUtils;

public class User {
    public Array<Integer> birdsFound;
    public Array<Integer> itemsBought;
    public Array<Integer> itemsPlaced;
    public Array<Integer> feed;
    public Integer quills;
    public long startTime;
    public Integer lastFeed;

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
        user.feed = new Array<>();

        user.birdsFound.add(0);
        user.itemsBought.add(0);
        user.feed.addAll(0,5,0,0,0);
        user.quills = 100;
        user.lastFeed = 0;
        user.startTime = TimeUtils.millis();

        String txt = json.toJson(user);
        userfile.writeString(json.prettyPrint(txt),false);
    }

    public static Integer getQuills(){
        User user = json.fromJson(User.class, getUserfile());
        return user.quills;
    }

    public static void addQuills(int value){
        User user = json.fromJson(User.class, getUserfile());
        user.quills += value;
        save(user);
    }

    public static void subQuills(int value){
        User user = json.fromJson(User.class, getUserfile());
        if(user.quills > 0){
            user.quills -= value;
            save(user);
        }
    }

    public static void buyItem(int itemId){
        User user = json.fromJson(User.class, getUserfile());
        user.itemsBought.add(itemId);
        save(user);
    }

    public static void placeItem(int itemId){
        User user = json.fromJson(User.class, getUserfile());
        user.itemsPlaced.add(itemId);
        save(user);
    }

    public static void removeItem(int itemId){
        User user = json.fromJson(User.class, getUserfile());
        user.itemsPlaced.removeValue(itemId,true);
        save(user);
    }

    public static boolean isItemBought(int itemId){
        return getItemsBought().contains(itemId,true);
    }

    public static boolean isItemPlaced(int itemId){
        return getItemsPlaced().contains(itemId,true);
    }
    public static Array<Integer> getItemsBought(){
        User user = json.fromJson(User.class,getUserfile());
        return user.itemsBought;
    }

    public static Array<Integer> getItemsPlaced(){
        User user = json.fromJson(User.class,getUserfile());
        return user.itemsPlaced;
    }

    public static void addBird(int birdId){
        User user = json.fromJson(User.class, getUserfile());
        user.birdsFound.add(birdId);
        save(user);
    }

    public static boolean isBirdFound(int birdId){
        User user = json.fromJson(User.class, getUserfile());
        return user.birdsFound.contains(birdId,true);
    }

    public static void saveStartTime(){
        User user = json.fromJson(User.class, getUserfile());
        user.startTime = TimeUtils.millis();
        save(user);
    }

    public static long getStartTime(){
        User user = json.fromJson(User.class, getUserfile());
        return user.startTime;
    }

    public static Integer getFeedCount(int key){
        User user = json.fromJson(User.class, getUserfile());
        return user.feed.get(key);
    }

    public static Integer getLastFeed(){
        User user = json.fromJson(User.class, getUserfile());
        return user.lastFeed;
    }

    public static void setLastFeed(int feedId){
        User user = json.fromJson(User.class, getUserfile());
        user.lastFeed = feedId;
        saveStartTime();
        save(user);
    }

    public static void addFeedOne(Integer key){
        User user = json.fromJson(User.class, getUserfile());
        Integer value = getFeedCount(key) + 1;
        user.feed.set(key,value);
        save(user);
    }

    public static void subFeedOne(Integer key){
        User user = json.fromJson(User.class, getUserfile());
        Integer value = getFeedCount(key) - 1;
        user.feed.set(key,value);
        save(user);
    }

}
