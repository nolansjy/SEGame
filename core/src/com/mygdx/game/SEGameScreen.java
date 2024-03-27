package com.mygdx.game;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FillViewport;

public class SEGameScreen implements Screen {
	final SEMain game;
	Music rainMusic;
	OrthographicCamera camera;
	Stage stage;
	SpriteBatch batch;
	Vector3 touchPos;
	TextureAtlas textureAtlas;
	Texture background;
	private AssetManager assetManager;
	private Preferences pref;

	public SEGameScreen(final SEMain game) {
		this.game = game;
		assetManager = game.getAssetManager();
		rainMusic = assetManager.get("rain.mp3");
		textureAtlas = assetManager.get("sprites.atlas");
		background = new Texture(Gdx.files.internal("background.png"));

		Sprites.load(textureAtlas);
		pref = Gdx.app.getPreferences("gamePrefs");

		rainMusic.setLooping(true);
		//rainMusic.setVolume(SEMainMenu.master_vol/100);
		rainMusic.play();
		rainMusic.setVolume(pref.getFloat("master_vol")/100);

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		//camera.setToOrtho(false, 450, 854);
		stage = new Stage(new FillViewport(450,854,camera));
		Gdx.input.setInputProcessor(stage);
		stage.addActor(new Bird("crow",20,20));

	}



//	private void drawSprite(String name, float x, float y) {
//		Sprite sprite = textureAtlas.createSprite(name);
//		sprite.setScale(2);
//		sprite.setPosition(x, y);
//		sprite.draw(batch);
//	}


	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act();
		stage.getBatch().begin();
		stage.getBatch().draw(background,0,0,450,854);
		stage.getBatch().end();
		stage.draw();
//		batch.begin();
//		drawSprite("crow",650,400);
//		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width,height,false);
	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
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
		textureAtlas.dispose();
		rainMusic.dispose();
		batch.dispose();
		stage.dispose();
	}


}
