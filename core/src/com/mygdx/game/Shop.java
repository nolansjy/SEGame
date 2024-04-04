package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.assets.AssetManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Shop implements Screen {
    final SEMain game;
    OrthographicCamera camera;
    Stage stage;
    private Skin skin;
    SpriteBatch batch;
    Vector3 touchPos;
    TextureAtlas textureAtlas;
    private AssetManager assetManager;

    public Shop(final SEMain game){ //create object instances
        this.game = game;
        camera = new OrthographicCamera();
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        stage = new Stage(new FitViewport(450,894,camera));
        Gdx.input.setInputProcessor(stage);
    }

    public void show() { //edit screen here
        Color grayscale = new Color(0.2f,0.2f,0.2f,1f);
        Color colorDefault = new Color(1f, 1f, 1f, 1f);

        //TODO: Fix the alignment of the tables to match those in SEGameScreen.
        //headUI initiate
        Table headUI = new Table();
        headUI.align(Align.topLeft).padLeft(15);
        headUI.setFillParent(true);
        stage.addActor(headUI);

        //headUI elements
        Image img = new Image(skin, "quill");
        headUI.add(img).padRight(15);
        Label wallet = new Label(String.valueOf(User.getQuills()), skin, "button");
        headUI.add(wallet);


        //menu initiate
        Table menu = new Table();
        //menu.align(Align.center);
        menu.setFillParent(true);
        stage.addActor(menu);

        //decoration Table
        Label decoLabel = new Label("Decorations", skin, "button");
        menu.add(decoLabel).padBottom(15).row();
        Table decoTable = new Table();

        Image firImg = new Image(skin, "quill");
        Image secImg = new Image(skin, "quill");
        if (User.getQuills() < 50){firImg.setColor(grayscale);}
        if (User.getQuills() < 80){secImg.setColor(grayscale);}
        decoTable.add(firImg).size(150,150).padRight(15);
        decoTable.add(secImg).size(150,150);
        decoTable.row();

        TextButton fir = new TextButton("50 quills", skin);
        TextButton sec = new TextButton("80 quills", skin);
        decoTable.add(fir).width(150).padRight(15);
        decoTable.add(sec).width(150);
        menu.add(decoTable).row();
        decoTable.padBottom(105);

        //birdfeeder Table
        Label foodLabel = new Label("Bird feeds", skin, "button");
        menu.add(foodLabel).row();

        Table foodTable = new Table();
        TextButton thi = new TextButton("Fruit", skin);
        TextButton fou = new TextButton("Seeds", skin);
        TextButton fif = new TextButton("Sugar", skin);
        TextButton six = new TextButton("Worms", skin);
        foodTable.add(thi).width(150).padRight(15);
        foodTable.add(fou).width(150);
        foodTable.row().pad(15,0,15,0);
        foodTable.add(fif).width(150).padRight(15);
        foodTable.add(six).width(150);
        menu.add(foodTable).row();
        foodTable.padBottom(45);


        //This table won't appear in the final game. The purpose of this is to test
        //the display of items based on the number of quills the user has collected
        Table temp = new Table();
        TextButton addQ = new TextButton("+10", skin);
        TextButton subQ = new TextButton("-10", skin);
        temp.add(addQ).padRight(15);
        temp.add(subQ);
        menu.add(temp).row();

        addQ.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                User.addQuills(10);
                wallet.setText(String.valueOf(User.getQuills()));

                if (User.getQuills() >= 50){firImg.setColor(colorDefault);}
                if (User.getQuills() >= 80){secImg.setColor(colorDefault);}
                stage.act();
                stage.draw();
                return true;
            }
        });

        subQ.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                User.addQuills(-10);
                wallet.setText(String.valueOf(User.getQuills()));

                if (User.getQuills() < 50){firImg.setColor(grayscale);}
                if (User.getQuills() < 80){secImg.setColor(grayscale);}
                stage.act();
                stage.draw();
                return true;
            }
        });

        //returnButton
        TextButton returnBtn = new TextButton("Return", skin);
        stage.addActor(returnBtn);
        returnBtn.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                game.setScreen(new SEGameScreen(game));
            }
        });
    }

    @Override
    public void render (float delta) { //render screen here
        camera.update();
        Gdx.gl.glClearColor(156/255f, 175/255f, 170/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    //maintain below
    @Override
    public void resize(int width, int height) {stage.getViewport().update(width,height,false);}

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
        skin.dispose();
        stage.dispose();
    }

}