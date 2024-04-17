package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SEMain extends Game{
    SkinLoader.SkinParameter skinParameter;
    AssetManager assetManager;

    public void create(){
        assetManager = new AssetManager();
        loadStart();
        assetManager.finishLoading();

        FileHandle userfile = User.getUserfile();
        if (!userfile.exists()) User.init();
        this.setScreen(new SEMainMenu(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    private void loadStart(){
        skinParameter = new SkinLoader.SkinParameter("earthskin-ui/earthskin.atlas");
        assetManager.load("earthskin-ui/earthskin.json",Skin.class,skinParameter);
        assetManager.load("dova20405.mp3", Music.class);

    }

    public void loadPostStart(){
        assetManager.load("sprites.atlas", TextureAtlas.class);
        assetManager.load("crow.ogg", Sound.class);
        assetManager.load("mynah.ogg", Sound.class);
        assetManager.load("sunbird.ogg", Sound.class);
        assetManager.load("oriole.ogg", Sound.class);
        assetManager.load("kingfisher.ogg", Sound.class);
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        assetManager.dispose();
    }
}
