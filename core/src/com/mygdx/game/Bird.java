package com.mygdx.game;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

public class Bird extends Actor {
    int id;
    String spriteName;
    float x;
    float y;
    int type;
    String name;
    String species;
    String desc;
    TextureRegion birdImg;
    Skin skin;
    static Array<Integer> birdRate;
    static Array<Integer> defaultRate;
    AssetManager assetManager;
    Sound birdcall;
    Preferences prefs;
    public Bird(Integer birdId){
        FileHandle birddata = Gdx.files.internal("birds.json");
        assetManager = SEGameScreen.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        prefs = Gdx.app.getPreferences("gamePrefs");

        JsonReader jsonRead = new JsonReader();
        JsonValue birdjson = jsonRead.parse(birddata);
        JsonValue bird = getBirdData(birdjson,birdId);
        id = birdId;
        type = bird.getInt("type");
        spriteName = bird.getString("spriteName");
        name = bird.getString("name");
        species = bird.getString("species");
        desc = bird.getString("description");
        x = bird.getFloat("x");
        y = bird.getFloat("y");

        HashMap<String, TextureRegion> images = Sprites.getImages();
        birdImg = images.get(spriteName);
        setWidth(birdImg.getRegionWidth());
        setHeight(birdImg.getRegionHeight());
        setX(x);
        setY(y);
        setTouchable(enabled);
        setBounds(getX(),getY(),getWidth(),getHeight());

        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                User.addQuills(10);
                getBirdCall(birdId).play(prefs.getFloat("sound"));
                if (!User.isBirdFound(birdId)){
                    Stage stage = getStage();
                    stage.addActor(getBirdFound(birdId));
                    User.addBird(birdId);
                }
                addAction(Actions.removeActor());
                return true;
            }
        });
    }

    public static Array<Integer> getBirdBaseRate(int birdId){
        birdRate = new Array<>();
        switch(birdId){
            case 1:
                birdRate.addAll(1,1,1,1,1,1,1,1);
                break;
            case 2:
                birdRate.addAll(2,2,2,2,2,2,2,2);
                break;
            case 3:
                birdRate.addAll(3,3,3);
                break;
            case 4:
                birdRate.addAll(4,4,4,4);
                break;
            case 5:
                birdRate.add(5);
        }
        return birdRate;
    }

    public static Array<Integer> getDefaultPool(){
        defaultRate = new Array<>(64);
        for(int i = 1; i < 6; i++){
            defaultRate.addAll(getBirdBaseRate(i));
        }
        return defaultRate;
    }
    public static FileHandle getBirdjson(){return Gdx.files.internal("birds.json");}

    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(birdImg, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


    private JsonValue getBirdData(JsonValue jsonValue, int id){
        JsonValue data = null;
        for(JsonValue value : jsonValue){
            int i = value.getInt("id");
            if(i==id){
                data = value;
                break;
            }
        }
        return data;
    }

    private Sound getBirdCall(int id){
        switch(id){
            case 1:
                birdcall = assetManager.get("crow.ogg",Sound.class);
                break;
            case 2:
                birdcall = assetManager.get("mynah.ogg",Sound.class);
                break;
            case 3:
                birdcall = assetManager.get("sunbird.ogg",Sound.class);
                break;
            case 4:
                birdcall = assetManager.get("oriole.ogg",Sound.class);
                break;
            case 5:
                birdcall = assetManager.get("kingfisher.ogg",Sound.class);
                break;
        }
        return birdcall;
    }

    private Dialog getBirdFound(int birdId){
        Bird bird = new Bird(birdId);
        Table birdInfo = new Table();
        birdInfo.setFillParent(true);
        birdInfo.row();
        Label birdName = new Label(bird.name, skin,"button");
        birdName.setColor(skin.getColor("black"));
        birdInfo.add(birdName).expandX().padBottom(10.0f);
        birdInfo.row();
        Image birdImg = new Image(bird.birdImg);
        birdInfo.add(birdImg);

        birdInfo.row();
        Label speciesName = new Label(bird.species, skin,"button");
        speciesName.setColor(skin.getColor("black"));
        birdInfo.add(speciesName).expandX().padTop(10.0f);

        Dialog birdFound = new Dialog("Bird Found!",skin){
            @Override
            protected void result(Object object){
                if((Boolean)object){
                    remove();
                }
            }
        };

        birdFound.setKeepWithinStage(true);
        birdFound.getContentTable().add(birdInfo);
        birdFound.button("Return",true);
        birdFound.setPosition(getStage().getWidth()/2,getStage().getHeight()/2);
        birdFound.show(getStage());
        return birdFound;
    }

}
