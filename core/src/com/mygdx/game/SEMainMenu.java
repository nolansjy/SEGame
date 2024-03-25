package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
    final SEMain game;
    OrthographicCamera camera;
    public SEMainMenu(final SEMain game){ // used in place of create() method
        this.game = game;
        camera = new OrthographicCamera();
        //camera.setToOrtho(false,480,800);

        skin = new Skin(Gdx.files.internal("earthskin-ui/earthskin.json"));
        stage = new Stage(new FitViewport(450,854,camera));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() { // menu layout goes here
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        TextButton playGame = new TextButton("Play Game", skin);
        TextButton settings = new TextButton("Settings",skin);
        TextButton info = new TextButton("Information",skin);
        root.add(playGame).fillX().uniformX();
        root.row().pad(10, 0, 10, 0);
        root.add(settings).fillX().uniformX();
        root.row();
        root.add(info).fillX().uniformX();

        playGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new SEGameScreen(game));
            }
        });


    }

    @Override
    public void render(float delta) {
        camera.update();
        //game.batch.setProjectionMatrix(camera.combined);

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
