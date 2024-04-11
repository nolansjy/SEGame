package com.mygdx.game;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;

public class Item extends Actor {
    int id;
    String spriteName;
    Float x;
    Float y;
    String name;
    String description;
    Integer price;
    TextureRegion itemImg;
    Skin skin;
    static Array<Integer> itemRate;

    public Item(int itemId){
        FileHandle itemdata = Gdx.files.internal("items.json");
        skin = new Skin(Gdx.files.internal("earthskin-ui/earthskin.json"));
        Texture pearImg = new Texture(Gdx.files.internal("pears.png"));

        JsonReader jsonRead = new JsonReader();
        JsonValue itemjson = jsonRead.parse(itemdata);
        JsonValue item = getItemData(itemjson,itemId);
        id = itemId;
        spriteName = item.getString("spriteName");
        x = item.getFloat("x");
        y = item.getFloat("y");
        name = item.getString("name");
        description = item.getString("description");
        price = item.getInt("price");

        HashMap<String, TextureRegion> images = Sprites.getImages();
        itemImg = images.get(spriteName);
        if(id == 4){
            itemImg = new TextureRegion(pearImg);
        }

        setWidth(itemImg.getRegionWidth());
        setHeight(itemImg.getRegionHeight());
        setX(x);
        setY(y);
        setTouchable(enabled);
        setBounds(getX(),getY(),getWidth(),getHeight());
    }

    public static Array<Integer> getItemRate(){
        Array<Integer> itemsPlaced = User.getItemsPlaced();
        itemRate = new Array<>();
        if(itemsPlaced.notEmpty()){
            for(int i = 0; i < itemsPlaced.size; i++){
                itemRate.add(itemsPlaced.get(i));
            }
        }

        return itemRate;
    }

    public static FileHandle getItemjson(){return Gdx.files.internal("items.json");}




    @Override
    public void draw(Batch batch, float parentAlpha){
        batch.draw(itemImg, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    private JsonValue getItemData(JsonValue jsonValue, int id){
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
