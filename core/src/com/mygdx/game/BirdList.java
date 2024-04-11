package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;

import org.w3c.dom.Text;

public class BirdList implements Screen {
    final SEMain game;
    FileHandle birdjson;
    FileHandle userfile;
    AssetManager assetManager;
    Skin skin;
    Stage stage;
    Table birdListTable;
    ImageTextButton[] birdList;
    public BirdList(final SEMain game){
        this.game = game;
        birdjson = Bird.getBirdjson();
        userfile = User.getUserfile();
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        stage = new Stage(new FitViewport(450,854));
        Gdx.input.setInputProcessor(stage);
        birdList = new ImageTextButton[6];
    }

    private Dialog getBirdProfile(Bird bird){
        Table profile = new Table();
        profile.setFillParent(true);

        Image birdImg = new Image(bird.birdImg);
        profile.add(birdImg).colspan(2);

        profile.row();
        Label birdName = new Label(bird.name, skin, "inset");
        birdName.setWrap(true);
        profile.add(birdName).pad(10.0f,0,10.0f,5.0f).grow().uniform();
        Label species = new Label(bird.species, skin, "inset");
        profile.add(species).pad(10.0f,0,10.0f,0).grow().uniform();

        profile.row();
        Label description = new Label(bird.desc, skin, "inset");
        description.setWrap(true);
        profile.add(description).fillX().colspan(2);
        Button close = new Button(skin,"close");

        Dialog birdProfile = new Dialog("Profile",skin);

        birdProfile.getContentTable().add(profile);
        birdProfile.row();
        birdProfile.button(close);
        birdProfile.setPosition(stage.getWidth()/2,stage.getHeight()/2);
        return birdProfile;
    }

    private Table getBirdListTable(){
        birdListTable = new Table();
        birdListTable.setFillParent(true);
        for(int i = 1; i < 6; i++){
            if(User.isBirdFound(i)){
                Bird bird = new Bird(i);
                birdList[i] = new ImageTextButton(bird.name,skin);
                Image birdImg = new Image(bird.birdImg);
                birdList[i].add(birdImg);
                birdListTable.add(birdList[i]).growX().pad(0,20.0f,10.0f,20.0f);
                birdList[i].addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        getBirdProfile(bird).show(stage);
                    }
                });
            }else{
                birdList[i] = new ImageTextButton("???",skin);
                birdList[i].setDisabled(true);
                birdListTable.add(birdList[i]).growX().pad(0,20.0f,10.0f,20.0f);
            }
            birdListTable.row();
        }
        return birdListTable;
    }
    @Override
    public void show() {
        Table listMenu = getBirdListTable();
        listMenu.setFillParent(true);
        ScrollPane birdListMenu = new ScrollPane(listMenu, skin);
        birdListMenu.setScrollingDisabled(true, false);
        birdListMenu.setFillParent(true);
        stage.addActor(birdListMenu);

        TextButton returnBtn = new TextButton("Return",skin);
        returnBtn.align(Align.bottomLeft);
        returnBtn.setX(50);
        returnBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SEGameScreen(game));
            }
        });
        stage.addActor(returnBtn);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.88f,0.82f,0.75f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.clear();
    }
}
