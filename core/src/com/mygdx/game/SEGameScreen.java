package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FillViewport;

import java.util.HashMap;

public class SEGameScreen implements Screen {
	final SEMain game;
	Music rainMusic;
	OrthographicCamera camera;
	Stage stage;
	SpriteBatch batch;
	Vector3 touchPos;
	TextureAtlas textureAtlas;
	Texture background;
	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	public SEGameScreen(final SEMain game) {
		this.game = game;
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));
		background = new Texture(Gdx.files.internal("background.png"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 450, 854);
		stage = new Stage(new FillViewport(450,854,camera));
		batch = new SpriteBatch();

		loadSprites();
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


	private void loadSprites() { // loads sprites from atlas
		Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();

		for (TextureAtlas.AtlasRegion region : regions) {
			Sprite sprite = textureAtlas.createSprite(region.name);
			sprites.put(region.name, sprite);
		}
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
		sprites.clear();
		textureAtlas.dispose();
		rainMusic.dispose();
		batch.dispose();
	}


}
