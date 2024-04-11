package com.mygdx.game;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;
import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashMap;

public class Shop implements Screen {
    final SEMain game;
    Stage stage;
    Skin skin;
    AssetManager assetManager;
    ImageTextButton[] itemList;
    ImageTextButton[] itemPrice;
    TextButton[] feedList;
    ImageTextButton[] feedPrice;
    Image pearIcon;
    Image quill;
    Label quillNum;
    Integer q;
    Table headTable;
    ImageTextButton priceBtn;

    public Shop(final SEMain game){ //create object instances
        this.game = game;
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        stage = new Stage(new FitViewport(450,854));
        Gdx.input.setInputProcessor(stage);
        itemList = new ImageTextButton[6];
        itemPrice = new ImageTextButton[6];
        feedList = new TextButton[5];
        feedPrice = new ImageTextButton[5];
        HashMap<String, TextureRegion> images = Sprites.getImages();
        pearIcon = new Image(images.get("pear"));
        quill = new Image(skin,"quill");
        quillNum = SEGameScreen.getQuillNum();
        q = User.getQuills();

    }
    public void show() {
        stage.addActor(SEGameScreen.getQuillCounter());

        // main menu
        headTable = new Table();
        headTable.setFillParent(true);
        headTable.add(getItemTable()).growX();
        headTable.row();
        headTable.add(getFeedTable()).growX();
        ScrollPane scrollPane = new ScrollPane(headTable,skin);
        stage.addActor(headTable);
        stage.addActor(scrollPane);

        //returnButton
        TextButton returnBtn = new TextButton("Return", skin);
        returnBtn.setX(50);
        returnBtn.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen(new SEGameScreen(game));
            }
        });
        stage.addActor(returnBtn);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.88f,0.82f,0.75f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        q = User.getQuills();
        quillNum.setText(String.valueOf(q));
        for(int i = 1; i < 6; i++){
            updatePriceBtn(itemPrice[i], i,0);
        }
        for(int i = 1; i < 5; i++) {
            updatePriceBtn(feedPrice[i],i,1);
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private Table getItemTable(){
        Table decoTable = new Table();
        Label decoTitle = new Label("Decorations",skin,"button");
        decoTable.add(decoTitle).colspan(2).center().padBottom(10.0f).row();
        for(int i = 1; i < 6; i++){
            Item item = new Item(i);
            itemList[i] = new ImageTextButton(item.name,skin);
            Image itemImg = new Image(item.itemImg);
            if(i == 4) itemImg = pearIcon;
            itemList[i].add(itemImg);
            decoTable.add(itemList[i]).growX().pad(0,20.0f,10.0f,10.0f);
            itemList[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    getDescription(item.name,item.description).show(stage);
                }
            });

            priceBtn = new ImageTextButton(String.valueOf(item.price),skin,"price");
            decoTable.add(priceBtn).pad(0,0,0,10.0f).uniform();
            decoTable.row();

            int itemId = i;
            priceBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    User.subQuills(item.price);
                    User.buyItem(itemId);
                }
            });
            itemPrice[i] = priceBtn;
        }

        return decoTable;
    }

    private Table getFeedTable(){
        Table feedTable = new Table();
        Label feedTitle = new Label("Feeders",skin,"button");
        feedTable.add(feedTitle).colspan(2).center().padBottom(10.0f).row();
        for(int i = 1; i < 5; i++){
            Feeder feeder = new Feeder(i);
            feedList[i] = new TextButton(feeder.name,skin,"pixel");
            feedTable.add(feedList[i]).growX().pad(0,20.0f,10.0f,10.0f);
            ImageTextButton priceBtn = new ImageTextButton(String.valueOf(feeder.price),skin,"price");
            feedTable.add(priceBtn).pad(0,0,0,10.0f).uniform();
            feedTable.row();

            feedList[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    getDescription(feeder.name,feeder.description).show(stage);
                }
            });

            int feederId = i;
            priceBtn.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    User.subQuills(feeder.price);
                    User.addFeedOne(feederId);
                }
            });

            feedPrice[i] = priceBtn;
        }
        return feedTable;
    }

    private void updatePriceBtn(ImageTextButton priceBtn, int id, int type){
        int price = Integer.parseInt(priceBtn.getText().toString());
        switch(type) {
            case 0: // item type
                if (price > q || User.isItemBought(id)) {
                    priceBtn.setDisabled(true);
                    priceBtn.setTouchable(disabled);
                } else {
                    priceBtn.setDisabled(false);
                    priceBtn.setTouchable(enabled);
                }
                break;
            case 1: // feed type
                if (price > q) {
                    priceBtn.setDisabled(true);
                    priceBtn.setTouchable(disabled);
                } else {
                    priceBtn.setDisabled(false);
                    priceBtn.setTouchable(enabled);
                }
                break;
        }


    }


    private Dialog getDescription(String name, String description){
        Label desc = new Label(description, skin,"button");
        desc.setWrap(true);
        Button close = new Button(skin,"close");

        Dialog descInfo = new Dialog(name,skin);
        descInfo.getContentTable().add(desc).width(400);
        descInfo.getButtonTable().add(close).expandX().align(Align.right);

        close.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                descInfo.remove();
            }
        });

        return descInfo;
    }

    @Override
    public void resize(int width, int height) {stage.getViewport().update(width,height,true);}

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose () {
        stage.clear();
    }

}