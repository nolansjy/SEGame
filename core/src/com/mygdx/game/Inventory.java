package com.mygdx.game;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

public class Inventory {
    final SEMain game;
    Skin skin;
    AssetManager assetManager;
    Stage stage;
    Dialog inventory;
    Table invList;
    Array<Integer> itemsBought;
    Array<Integer> itemsPlaced;
    ImageTextButton[] boughtList;
    ImageTextButton[] placedList;
    Image pearIcon;

    public Inventory(final SEMain game,Stage mainstage){
        this.game = game;
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        stage = mainstage;
        itemsBought = User.getItemsBought();
        itemsPlaced = User.getItemsPlaced();
        boughtList = new ImageTextButton[6];
        placedList = new ImageTextButton[6];
        HashMap<String, TextureRegion> images = Sprites.getImages();
        pearIcon = new Image(images.get("pear"));
    }

    public Dialog getInventory(){
        inventory = new Dialog("Inventory",skin);
        inventory.setWidth(stage.getWidth());
        inventory.setHeight(stage.getHeight()-100);
        inventory.align(Align.top);
        inventory.getContentTable().add(getInvList());
        inventory.button("Return",skin);
        return inventory;
    }

    private Table getInvList(){
        invList = new Table();
        invList.setFillParent(true);
        for(int i = 1; i < itemsBought.size; i++){
            int itemId = itemsBought.get(i);
            Item item = new Item(itemId);

            boughtList[i] = new ImageTextButton(item.name,skin,"checked");
            Image itemImg = new Image(item.itemImg);
            if(itemId == 4) itemImg = pearIcon;
            boughtList[i].add(itemImg);
            if(User.isItemPlaced(itemId)) boughtList[i].setChecked(true);

            invList.add(boughtList[i]).growX().pad(0,20.0f,10.0f,10.0f).row();
            int btnIter = i;
            boughtList[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if(boughtList[btnIter].isChecked()){ // counts AFTER click? not sure why reversed
                        User.placeItem(itemId);
                        stage.addActor(item);
                    }else{
                        User.removeItem(itemId);
                        item.remove();
                    }
                }
            });
        }
        return invList;
    }


}
