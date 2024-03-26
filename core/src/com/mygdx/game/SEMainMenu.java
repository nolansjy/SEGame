package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class SEMainMenu implements Screen{ // used instead of ApplicationAdapter
    private Skin skin;
    private Stage stage;
    private AssetManager assetManager;
    final SEMain game;
    OrthographicCamera camera;
    public SEMainMenu(final SEMain game){ // used in place of create() method
        this.game = game;
        camera = new OrthographicCamera();
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        stage = new Stage(new FitViewport(450,854,camera));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() { // menu layout goes here
        Table menu = new Table();
        menu.setFillParent(true);
        stage.addActor(menu);

        TextButton playGame = new TextButton("Play Game", skin);
        TextButton settings = new TextButton("Settings",skin);
        menu.add(playGame).fillX().uniformX();
        menu.row().pad(10, 0, 10, 0);
        menu.add(settings).fillX().uniformX();

        Slider volume = new Slider(0,100,10,false,skin);
        CheckBox disableStart = new CheckBox("Disable start menu",skin);
        Button close = new Button(skin,"close");

        playGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SEGameScreen(game));
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog settingsPopup = new Dialog("Settings",skin);
                settingsPopup.button(close).setPosition(
                        settingsPopup.getMaxWidth(),settingsPopup.getMaxHeight());
                settingsPopup.getContentTable().add(volume);
                settingsPopup.getButtonTable().add(disableStart);
                settingsPopup.show(stage);
            }
        });

        disableStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (disableStart.isChecked()) System.out.println("Disabled Start");
            }
        });

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(.9f, .9f, .9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
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
        skin.dispose();
        stage.dispose();

    }

}
