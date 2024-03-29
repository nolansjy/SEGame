package com.mygdx.game;


import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SEGameScreen implements Screen {
	final SEMain game;
	Music rainMusic;
	OrthographicCamera camera;
	Stage stage;
	SpriteBatch batch;
	TextureAtlas textureAtlas;
	Texture background;
	Skin skin;
	AssetManager assetManager;
	Preferences prefs;


	public SEGameScreen(final SEMain game) {
		this.game = game;
		assetManager = game.getAssetManager();
		rainMusic = assetManager.get("rain.mp3");
		textureAtlas = assetManager.get("sprites.atlas");
		background = new Texture(Gdx.files.internal("background.png"));
		skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);

		Preferences pref = Gdx.app.getPreferences("gamePrefs");
		rainMusic.setLooping(true);
		//rainMusic.setVolume(SEMainMenu.master_vol/100);
		rainMusic.setVolume(pref.getFloat("master_vol")/100);

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		stage = new Stage(new FillViewport(450,854,camera));
		Gdx.input.setInputProcessor(stage);

		prefs = Gdx.app.getPreferences("gamePrefs");

		Sprites.load(textureAtlas);
	}

	@Override
	public void show(){
		rainMusic.play();
		//TODO: method to randomly spawn birds
		stage.addActor(new Bird(2));

		Table gardenUi = new Table();
		gardenUi.padRight(35.0f);
		gardenUi.padBottom(15.0f);
		gardenUi.align(Align.bottomRight);
		gardenUi.setFillParent(true);

		Button bag = new Button(skin, "bag");
		gardenUi.add(bag).padRight(5.0f);

		Button book = new Button(skin, "book");
		gardenUi.add(book).padRight(5.0f);

		Button shop = new Button(skin, "shop");
		gardenUi.add(shop).padRight(5.0f);

		Button home = new Button(skin, "home");
		gardenUi.add(home);

		stage.addActor(gardenUi);

		Table quills = new Table();
		quills.padLeft(35.0f);
		quills.padTop(15.0f);
		quills.align(Align.topLeft);
		quills.setFillParent(true);

		Image quill = new Image(skin, "quill");
		quills.add(quill).padRight(10.0f);

		Integer q = 1000;

		Label quillNum = new Label(String.valueOf(q), skin, "button");
		quillNum.setColor(skin.getColor("window"));
		quills.add(quillNum);

		stage.addActor(quills);


		home.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				prefs.putBoolean("skip_menu",false);
				prefs.flush();
				game.setScreen(new SEMainMenu(game));
			}
		});
	}


	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act();
		stage.getBatch().begin();
		stage.getBatch().draw(background,0,0,450,854);
		stage.getBatch().end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width,height,false);
	}


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
		assetManager.clear();
		batch.dispose();
		Sprites.dispose();
		stage.dispose();
	}


}
