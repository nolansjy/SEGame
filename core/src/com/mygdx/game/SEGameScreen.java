package com.mygdx.game;


import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.utils.viewport.FillViewport;


public class SEGameScreen implements Screen {
	private static Table quills;
	private static Label quillNum;
	final SEMain game;
	Music bgm;
	Stage stage;
	TextureAtlas textureAtlas;
	Texture background;
	static Skin skin;
	static AssetManager assetManager;
	Preferences prefs;
	Integer q;
	Table gardenUi;
	Array<Item> itemsPlaced;
	long startTime;
	long elapsedTime;
	Array<Integer> birdPool;
	Array<Integer> itemRate;
	private Integer randBirdId;

	public SEGameScreen(final SEMain game) {
		this.game = game;
		assetManager = game.getAssetManager();
		bgm = assetManager.get("dova20405.mp3",Music.class);
		textureAtlas = assetManager.get("sprites.atlas");
		background = new Texture(Gdx.files.internal("background.png"));
		skin = assetManager.get("earthskin-ui/earthskin.json",Skin.class);

		Preferences pref = Gdx.app.getPreferences("gamePrefs");
		bgm.setLooping(true);
		bgm.setVolume(pref.getFloat("bgm",1));

		stage = new Stage(new FillViewport(450,854));
		Gdx.input.setInputProcessor(stage);

		prefs = Gdx.app.getPreferences("gamePrefs");

		Sprites.load(textureAtlas);

		itemsPlaced = new Array<>();
		itemRate = Item.getItemRate();
	}

	private Array<Integer> getBirdPool(){
		birdPool = new Array<>();
		birdPool.add(0);
		Feeder.addFeedRate(User.getLastFeed(),birdPool);
		return birdPool;
	}

	public static AssetManager getAssetManager(){
		return assetManager;
	}

	@Override
	public void show(){
		bgm.play();
		startTime = User.getStartTime(); // in milliseconds
		elapsedTime = TimeUtils.timeSinceMillis(startTime)/1000; // in seconds

		for(int i = 0; i < User.getItemsPlaced().size; i++){
			Integer itemId = User.getItemsPlaced().get(i);
			itemsPlaced.add(new Item(itemId));
			stage.addActor(itemsPlaced.get(i));
		}

		if(elapsedTime >= 10 && !getBirdPool().contains(0,false)){ // 10 seconds
			randBirdId = getBirdPool().random();
			if(!itemRate.contains(randBirdId,false)) stage.addActor(new Bird(randBirdId));
			User.saveStartTime();
		}

		if(elapsedTime >= 10 && itemRate.notEmpty()){
			for(int i = 0; i < itemRate.size; i++){
				Integer birdId = itemRate.get(i);
				if(!birdId.equals(randBirdId)){
					stage.addActor(new Bird(birdId));
				}
			}
		}

		if(elapsedTime >= 60 && !User.getLastFeed().equals(0)){
			User.setLastFeed(0);
		}

		stage.addActor(new Feeder(User.getLastFeed()));


		stage.addActor(getUI());
		stage.addActor(getQuills());
	}


	@Override
	public void render (float delta) {
		delta = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.9f, 0.9f, 1, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		q=User.getQuills();
		quillNum.setText(String.valueOf(q));
		for(int i = 0; i < itemsPlaced.size; i++){
			if(!User.isItemPlaced(itemsPlaced.get(i).id)) itemsPlaced.get(i).remove();
		}
		stage.act(delta);
		stage.getBatch().begin();
		stage.getBatch().draw(background,0,0,450,854);
		stage.getBatch().end();
		stage.draw();
	}

	public static Table getQuillCounter(){
		return quills;
	}

	public static Label getQuillNum(){
		return quillNum;
	}

	private Table getQuills(){
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
		return quills;
	}

	private Table getUI(){
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

		home.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
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
		bag.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Inventory bag = new Inventory(game,stage);
				bag.getInventory().show(stage);
			}
		});
		return gardenUi;
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
		stage.clear();
	}


}
