package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
        stage = new Stage(new FitViewport(450,854,camera));
        Gdx.input.setInputProcessor(stage);
    }

    public void show() { //edit screen here
        Table menu = new Table();
        menu.setFillParent(true);
        stage.addActor(menu);

        Label decoLabel = new Label("Decorations", skin);
        decoLabel.setFontScale(2f);
        menu.add(decoLabel).row();


        Table decoTable = new Table();
        //decoTable.space(15);
        //Texture something = new Texture(Gdx.files.internal("fountain-test.png"));
        //ImageButton dis1 = new ImageButton(something);

        TextButton fir = new TextButton("Deco Item 1", skin);
        TextButton sec = new TextButton("Deco Item 2", skin);
        decoTable.add(fir).width(150).padRight(15);
        decoTable.add(sec).width(150).padRight(15);
        menu.add(decoTable).row();
        decoTable.pad(0,15,105,15);

        Label foodLabel = new Label("Bird feeds", skin);
        foodLabel.setFontScale(2f);
        menu.add(foodLabel).row();

        Table foodTable = new Table();
        //foodTable.space(15);
        TextButton thi = new TextButton("Food Item 1", skin);
        TextButton fou = new TextButton("Food Item 2", skin);
        TextButton fif = new TextButton("Food Item 3", skin);
        TextButton six = new TextButton("Food Item 4", skin);
        foodTable.add(thi).width(150).padRight(15);
        foodTable.add(fou).width(150).padRight(15);
        foodTable.add(fif).width(150).padRight(15);
        foodTable.add(six).width(150).padRight(15);
        menu.add(foodTable).row();
        foodTable.pad(0,15,45,15);

        ScrollPane scrolls = new ScrollPane(foodTable);
        scrolls.setScrollingDisabled(false, true);
        menu.add(scrolls);
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