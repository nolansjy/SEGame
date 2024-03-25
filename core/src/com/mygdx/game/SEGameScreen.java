package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public class SEGameScreen implements Screen {
	final SEMain game;
	Music rainMusic;
	OrthographicCamera camera;
	SpriteBatch batch;
	Vector3 touchPos;
	TextureAtlas textureAtlas;
	final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	public SEGameScreen(final SEMain game) {
		this.game = game;
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		textureAtlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480, 800);
		batch = new SpriteBatch();
		loadSprites();
	}



	@Override
	public void render (float delta) {
		ScreenUtils.clear(0.51f, 0.88f, 1, 1);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		drawSprite(sprites.get("sunbird"),20,20);
		drawSprite(sprites.get("collared"),100,100);
		drawSprite(sprites.get("eagle"),400,600);
		game.batch.end();

	}

	private void drawSprite(Sprite sprite, float x, float y) {
		sprite.setPosition(x, y);
		sprite.draw(game.batch);
	}

	private void loadSprites() {
		Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();

		for (TextureAtlas.AtlasRegion region : regions) {
			Sprite sprite = textureAtlas.createSprite(region.name);
			sprites.put(region.name, sprite);
		}
	}

	@Override
	public void resize(int width, int height) {
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
