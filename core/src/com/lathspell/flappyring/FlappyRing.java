package com.lathspell.flappyring;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;


public class FlappyRing extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture ring;
	Texture sauron;
	Texture sauronn;
	Texture sauronnn;
	float ringX;
	float ringY;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.1f;
	float enemyVelocity = 2;
	Random random;
	Circle ringCircle;

	int numberOfEnemies = 4;
	float[] enemyX = new float[numberOfEnemies];
	float[] enemyOffset1 = new float[numberOfEnemies];
	float[] enemyOffset2 = new float[numberOfEnemies];
	float[] enemyOffset3 = new float[numberOfEnemies];
	float distance = 0;

	Circle[] enemyCircles1;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;

	ShapeRenderer shapeRenderer;

	int score = 0;
	int scoredEnemy = 0;

	BitmapFont font;
	BitmapFont font2;

	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("mountdoom.png");
		ring = new Texture("thering.png");
		sauron = new Texture("sauron.png");
		sauronn = new Texture("sauron.png");
		sauronnn = new Texture("sauron.png");

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);



		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();

		shapeRenderer = new ShapeRenderer();


		ringX = Gdx.graphics.getWidth() / 2 - ring.getHeight() / 2;
		ringY = Gdx.graphics.getHeight() / 2;

		ringCircle = new Circle();
		enemyCircles1 = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		for (int i = 0; i < numberOfEnemies; i++) {

			enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = (Gdx.graphics.getWidth()) - (sauron.getWidth() / 2) + (i * distance);

			enemyCircles1[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();


		}
	}

	@Override
	public void render() {
		batch.begin();

		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());



		if (gameState == 1) {

			if (enemyX[scoredEnemy] < (Gdx.graphics.getWidth() / 2 - ring.getHeight() / 2)){
				score++;
				if (scoredEnemy<numberOfEnemies-1){
					scoredEnemy++;
				}
				else{
					scoredEnemy=0;
				}
			}

			if (Gdx.input.justTouched()) {

				velocity = -8;
			}

			for (int i = 0; i < numberOfEnemies; i++) {

				if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}

				batch.draw(sauron, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset1[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(sauronn, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(sauronnn, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffset3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

				enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOffset3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);


			}

			if (ringY > 0) {
				velocity = velocity + gravity;
				ringY = ringY - velocity;
			}
			else{
				gameState=2;
			}

		} else if (gameState==0){
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		}
		else if (gameState==2){

			font2.draw(batch,"You Didn't Pass | Tap to play again!", 150,250);

			if (Gdx.input.justTouched()) {
				gameState = 1;
				ringY = Gdx.graphics.getHeight() / 2;
				for (int i = 0; i < numberOfEnemies; i++) {

					enemyOffset1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffset3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = (Gdx.graphics.getWidth()) - (sauron.getWidth() / 2) + (i * distance);

					enemyCircles1[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();


				}

				velocity = 0;
				scoredEnemy=0;
				score=0;
			}




		}

		batch.draw(ring, ringX, ringY, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		ringCircle.set(ringX + Gdx.graphics.getWidth()/30, ringY + Gdx.graphics.getWidth()/30, (Gdx.graphics.getWidth() / 15) / 2);



		for (int i =0;i<numberOfEnemies;i++ ){


			if (Intersector.overlaps(ringCircle,enemyCircles1[i]) || Intersector.overlaps(ringCircle,enemyCircles2[i]) || Intersector.overlaps(ringCircle,enemyCircles3[i])){
				gameState=2;
			}
		}

		shapeRenderer.end();

	}

		@Override
		public void dispose () {
		}
	}
