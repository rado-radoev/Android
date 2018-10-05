package com.superlamer.flappy2;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class FlappyBirdy2 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
    Texture tubeTop;
    Texture tubeBottom;

    Texture gameOver;

    int score = 0;

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
    Circle birdCirle;
//    ShapeRenderer shapeRenderer;
    Rectangle[] topTubesRectangles;
    Rectangle[] bottomTubeRectangles;
    int scoringTube = 0;
    BitmapFont font;

	int gameState = 0;


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		tubeTop = new Texture("toptube.png");
		tubeBottom = new Texture("bottomtube.png");
		birdCirle = new Circle();
//		shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(10);
        gameOver = new Texture("gameover.png");

		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		randomGenerator = new Random();
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;
        topTubesRectangles = new Rectangle[numberOfTubes];
        bottomTubeRectangles = new Rectangle[numberOfTubes];

        startGame();
	}

	public void startGame() {
        birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

        for (int i = 0; i < numberOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200) ;
            tubeX[i] = Gdx.graphics.getWidth() / 2 - tubeTop.getWidth() / 2 + Gdx.graphics.getWidth() +i * distanceBetweenTubes;

            topTubesRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }
    }

	@Override
	public void render () {

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {

            if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {
                score++;
                Gdx.app.log("Sore", String.valueOf(score));
                if (scoringTube < numberOfTubes - 1) {
                    scoringTube++
                } else {
                    scoringTube = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                velocity = -30;

            }
            for (int i = 0; i < numberOfTubes; i++) {

                if (tubeX[i] < -tubeTop.getWidth()) {
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200) ;
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                } else {
                    tubeX[i] -= tubeVelocity;
                }

                batch.draw(tubeTop, tubeX[i],
                        Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(tubeBottom, tubeX[i],
                        Gdx.graphics.getHeight() / 2 - gap  / 2 - tubeBottom.getHeight() + tubeOffset[i]);

                topTubesRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],
                        tubeTop.getWidth(), tubeTop.getHeight());
                bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap  / 2 - tubeBottom.getHeight() + tubeOffset[i],
                        tubeBottom.getWidth(), tubeBottom.getHeight());

            }


            if (birdY > 0) {

                velocity += gravity;
                birdY -= velocity;
            } else {
                gameState = 2;
            }

        } else if (gameState == 0){

            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() /2,
                    Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);

            if (Gdx.input.justTouched()) {
                gameState = 1;
                score = 0;
                scoringTube = 0;
                velocity = 0;
                startGame();
            }
        }

        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }

        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
                birdY);
        font.draw(batch, String.valueOf(score), 100, 200);

        birdCirle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2,
                birds[0].getWidth() / 2);

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.RED);

//        shapeRenderer.circle(birdCirle.x, birdCirle.y, birdCirle.radius);

        for (int i = 0; i < numberOfTubes; i++) {
//            shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],
//                    tubeTop.getWidth(), tubeTop.getHeight());
//
//            shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap  / 2 - tubeBottom.getHeight() + tubeOffset[i],
//                    tubeBottom.getWidth(), tubeBottom.getHeight());

            if (Intersector.overlaps(birdCirle, topTubesRectangles[i]) ||
                    Intersector.overlaps(birdCirle, bottomTubeRectangles[i])) {
//                Gdx.app.log("Collision", "Detected");
                gameState = 2;
            }
        }

        batch.end();
//        shapeRenderer.end();
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
