package com.mygdx.game;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Json;
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

    public Bird(int birdId){
        FileHandle birddata = Gdx.files.internal("birds.json");
        FileHandle userdata = User.getUserfile();

        JsonReader jsonRead = new JsonReader();
        JsonValue birdjson = jsonRead.parse(birddata);
        JsonValue bird = getBirdData(birdjson,birdId);
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
                // TODO: add quills
                addAction(Actions.removeActor());
                User.addQuills(10);
                User.check();
                return true;
            }
        });
    }

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
}
