package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SEMainMenu implements Screen{ // used instead of ApplicationAdapter
    Skin skin;
    Stage stage;
    final SEMain game;
    Preferences prefs;
    AssetManager assetManager;
    private Label loading;
    private Table mainMenu;
    Music bgm;
    public SEMainMenu(final SEMain game){
        this.game = game;
        assetManager = game.getAssetManager();
        skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);
        bgm = assetManager.get("dova20405.mp3",Music.class);
        stage = new Stage(new FitViewport(450,854));
        Gdx.input.setInputProcessor(stage);
        prefs = Gdx.app.getPreferences("gamePrefs");
        bgm.setLooping(true);
        bgm.setVolume(prefs.getFloat("bgm",1));
        game.loadPostStart();
    }

    @Override
    public void show() {
        bgm.play();
        mainMenu = getMainMenu();
        loading = new Label("Loading...",skin,"button");
        mainMenu.add(loading).fillX();
        stage.addActor(mainMenu);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.95f,0.97f,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(assetManager.update()){
            loading.setText("Loading...done!");
        }
        stage.act();
        stage.draw();
    }

    private Table getMainMenu(){
        mainMenu = new Table();
        mainMenu.setFillParent(true);
        Label title = new Label("Let's Bird!",skin,"title");
        TextButton playGame = new TextButton("Play Game", skin);
        TextButton settings = new TextButton("Settings",skin);
        TextButton reset = new TextButton("Reset",skin);
        mainMenu.add(title).fillX().pad(0,0,10,0).row();
        mainMenu.add(playGame).fillX();
        mainMenu.row().pad(10, 0, 10, 0);
        mainMenu.add(settings).fillX();
        mainMenu.row().pad(10, 0, 10, 0);
        mainMenu.add(reset).fillX();
        mainMenu.row().pad(40, 0, 10, 0);

        Label musicLabel = new Label("Music",skin,"button");
        Slider music = new Slider(0,1,0.1f,false,skin);
        music.setValue(prefs.getFloat("bgm",1));

        Label soundLabel = new Label("Sound",skin,"button");
        Slider sound = new Slider(0,1,0.1f,false,skin);
        sound.setValue(prefs.getFloat("sound",1));
        Button close = new Button(skin,"close");

        music.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("bgm", music.getValue());
                prefs.flush();
                bgm.setVolume(prefs.getFloat("bgm"));
            }
        });

        playGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prefs.putFloat("sound",sound.getValue());
                prefs.flush();
                if(assetManager.isFinished()){
                    game.setScreen(new SEGameScreen(game));
                }
            }
        });

        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Dialog settingsPopup = new Dialog("Settings",skin);
                settingsPopup.button(close);
                settingsPopup.getContentTable().add(musicLabel);
                settingsPopup.getContentTable().add(music).row();
                settingsPopup.getContentTable().add(soundLabel);
                settingsPopup.getContentTable().add(sound);
                settingsPopup.pad(40,20,10,20);
                settingsPopup.show(stage);
            }
        });

        reset.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                User.init();
            }
        });

        return mainMenu;
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
        stage.dispose();

    }

}
