package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter implements GestureDetector.GestureListener {
	SpriteBatch batch;
	Texture img, background;
	Sprite sprite;
	OrthographicCamera camera;
	float touchX = -8096, touchY = -8096;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("map.png");
		background = new Texture("background.png");
		sprite = new Sprite(img);
		sprite.setPosition(-img.getWidth()/2, -img.getHeight()/2);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.input.setInputProcessor(new GestureDetector(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, -4096, -4096);
		if (!sprite.getBoundingRectangle().contains(touchX, touchY))
		    sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		touchX = x;
		touchY = y;
		return true;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		float xc = camera.position.x;
		float yc = camera.position.y;
		if (xc - deltaX > -Gdx.graphics.getWidth()/2 && xc - deltaX < Gdx.graphics.getWidth()/2 &&
		yc + deltaY > -Gdx.graphics.getHeight()/2 && yc + deltaY < Gdx.graphics.getHeight()/2) {
			camera.translate(-deltaX, deltaY);
			camera.update();
		}
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		if (camera.zoom + 0.02 <= 1.8 && distance < initialDistance) {
			camera.zoom += 0.02;
			camera.update();
		}
		else if (camera.zoom - 0.02 >= 0.6 && distance > initialDistance) {
			camera.zoom -= 0.02;
			camera.update();
		}

		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public void pinchStop() {

	}
}
