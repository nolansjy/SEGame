package com.mygdx.game;


import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashMap;

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
	Integer q;
	Label quillNum;
	Table gardenUi;
	Table quills;
	TextureRegion feeder;
	long startTime;
	long elapsedTime;
	Array<Integer> birdPool;
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
		stage = new Stage(new FillViewport(450,854));
		Gdx.input.setInputProcessor(stage);

		prefs = Gdx.app.getPreferences("gamePrefs");

		Sprites.load(textureAtlas);
		feeder = textureAtlas.findRegion("feeder_empty");

		birdPool = new Array<>();
		birdPool.addAll(1, 2, 3, 4, 5);
	}

	@Override
	public void show(){
		rainMusic.play();
		//TODO: method to randomly spawn birds

		gardenUi = new Table();
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

		quills = new Table();
		quills.padLeft(35.0f);
		quills.padTop(15.0f);
		quills.align(Align.topLeft);
		quills.setFillParent(true);

		Image quill = new Image(skin, "quill");
		quills.add(quill).padRight(10.0f);

		q = User.getQuills();
		quillNum = new Label(String.valueOf(q), skin, "button");
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

		book.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new BirdList(game));
			}
		});

		shop.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new Shop(game));
			}
		});
	}


	@Override
	public void render (float delta) {
		delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.9f, 0.9f, 1, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		q=User.getQuills();
		quillNum.setText(String.valueOf(q));
		startTime = User.getStartTime(); // in milliseconds
		elapsedTime = TimeUtils.timeSinceMillis(startTime)/1000; // in seconds
		if(elapsedTime==10 && birdPool.size > 0){ // 10 seconds
			Integer birdId = birdPool.random();
			stage.addActor(new Bird(birdId));
			birdPool.removeValue(birdId,true);
			User.saveStartTime();
		}
		stage.act(delta);
		stage.getBatch().begin();
		stage.getBatch().draw(background,0,0,450,854);
		stage.getBatch().draw(feeder,stage.getWidth()/3,125,feeder.getRegionWidth(),feeder.getRegionHeight());
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
		stage.clear();
	}


}
