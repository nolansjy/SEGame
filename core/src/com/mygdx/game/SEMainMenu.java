package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SEMainMenu implements Screen{ // used instead of ApplicationAdapter
    private final Skin skin;
    private final Stage stage;
    final SEMain game;
    OrthographicCamera camera;
    public static float master_vol;
    public static boolean skip_menu;
    public Preferences prefs;
    AssetManager assetManager;
    public SEMainMenu(final SEMain game){ // used in place of create() method
        this.game = game;
        camera = new OrthographicCamera();
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        stage = new Stage(new FitViewport(450,854,camera));
        Gdx.input.setInputProcessor(stage);
        prefs = Gdx.app.getPreferences("gamePrefs");
    }

    @Override
    public void show() { // menu layout goes here
        if (prefs.getBoolean("skip_menu")){
            this.game.setScreen(new SEGameScreen(game));
        }
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
        volume.setValue(prefs.getFloat("master_vol"));

        playGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("master_vol",volume.getValue());
                prefs.flush();
                game.setScreen(new SEGameScreen(game));
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog settingsPopup = new Dialog("Settings",skin);
                //settingsPopup.button(close);
                //settingsPopup.setDebug(true);
                settingsPopup.getContentTable().add(volume);
                settingsPopup.getButtonTable().add(disableStart);
                //settingsPopup.add(String.valueOf(prefs.getFloat("master_vol")));
                settingsPopup.button(close);
                settingsPopup.pad(40,20,10,20);
                settingsPopup.show(stage);
            }
        });

        disableStart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putBoolean("skip_menu",disableStart.isChecked());
                prefs.flush();
                if (disableStart.isChecked()){
                    System.out.println("Disabled Start");
                }
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
        assetManager.clear();
        stage.dispose();

    }

}
