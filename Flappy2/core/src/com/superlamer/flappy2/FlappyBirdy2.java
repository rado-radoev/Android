package com.superlamer.flappy2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBirdy2 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
    Texture tubeTop;
    Texture tubeBottom;

	Texture[] birds;
	int flapState;
	float birdY = 0;
	float velocity = 0;
    float gravity = 2;
    float gap = 400;
    float maxTubeOffset;
    Random randomGenerator;
    float tubeVelocity = 4;
    int numberOfTubes = 4;
    float[] tubeX =  new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;

	int gameState = 0;


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		tubeTop = new Texture("toptube.png");
		tubeBottom = new Texture("bottomtube.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

        for (int i = 0; i < numberOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200) ;
            tubeX[i] = Gdx.graphics.getWidth() / 2 - tubeTop.getWidth() / 2 + i * distanceBetweenTubes;
        }
	}

	@Override
	public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState != 0) {


            if (Gdx.input.justTouched()) {
                velocity = -30;

            }
            for (int i = 0; i < numberOfTubes; i++) {

                if (tubeX[i] < -tubeTop.getWidth()) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                } else {
                    tubeX[i] -= tubeVelocity;
                }

                batch.draw(tubeTop, tubeX[i],
                        Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(tubeBottom, tubeX[i],
                        Gdx.graphics.getHeight() / 2 - gap  / 2 - tubeBottom.getHeight() + tubeOffset[i]);

            }


            if (birdY > 0 || velocity < 0) {

                velocity += gravity;
                birdY -= velocity;
            }

        } else {

            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        }

        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }

        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
                birdY);
        batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
        for (Texture bird : birds) {
            bird.dispose();
        }
	}
}
