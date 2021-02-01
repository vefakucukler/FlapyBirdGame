package com.vkyazilim.ucankus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class UcanKus extends ApplicationAdapter {
	SpriteBatch batch;//objeler.içerisine koyduğumuz label text vs için kullanıyoruz.
	Texture background; // bir objenin imajını aldığı paten
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;
	float birdX=0;
	float birdY=0;
	int gameState=0;//oyun durumu
	float velocity=0;//hız
	float gravity = 0.7f;
	float enemyVelocity=3;
	Random random;
	Circle birdCircle;
	ShapeRenderer shapeRenderer;
	int score=0;
	int scoreEnemy=0;
	BitmapFont font;
	BitmapFont font2;


	int dusmanSayisi=4 ;
	float[] enemyX = new float[dusmanSayisi];
	float[] enemyOffset1 = new float[dusmanSayisi];
	float[] enemyOffset2 = new float[dusmanSayisi];
	float[] enemyOffset3 = new float[dusmanSayisi];
	float mesafe=0;
	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;
	@Override
	public void create () {//uygulama açıldığında ne olucaksa burada yazıyoruz.
	 	batch = new SpriteBatch();//spriteBatch sınıfından bir nesne oluşturduk.
		background = new Texture("background.png");//Arka planı oluşturduk.
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");
		mesafe = Gdx.graphics.getWidth()/2;
		random = new Random();

		bird = new Texture("bird.png");// kuşu oluşturduk.
		birdX =Gdx.graphics.getWidth()/5;//kuşun X koordinatında ki konumunu birdX değişkenine atadık.
		birdY =Gdx.graphics.getHeight()/2;// kuşun Y koordinatında ki konumunu birdY değişkenine atadık.
        shapeRenderer= new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircle1 = new Circle[dusmanSayisi];
		enemyCircle2 = new Circle[dusmanSayisi];
		enemyCircle3 = new Circle[dusmanSayisi];

		font=new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().scale(4);
		font2=new BitmapFont();
		font2.setColor(Color.GOLD);
		font2.getData().scale(5);


		for (int i=0;i<dusmanSayisi;i++){

			enemyOffset1[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
			enemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
			enemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());


			enemyX[i]=Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * mesafe;//Her enemy için aralarında bir mesafe olmasını sağlıyoruz.
            enemyCircle1[i]= new Circle();
            enemyCircle2[i]=new Circle();
            enemyCircle3[i]=new Circle();
		}
	}

	@Override
	public void render () {//oyun devam ettiği sürece devamlı çağrılan bir metod.Süreli olmasını istediğimiz şeyleri burada yazıcaz.
		batch.begin(); //batch i çalıştırmamız gereken kodlar
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());//burada arka planımızı çiziyoruz.

		if(Gdx.input.justTouched()){//kullanıcı ekrana dokununca neler olacağını burada yazacağız.
			gameState=1;
		}
		if (gameState==1){

			if(enemyX[scoreEnemy]<birdX){
				score++;

				if(scoreEnemy<dusmanSayisi-1){
					scoreEnemy++;
				}
				else{
					scoreEnemy=0;
				}
			}



			if(Gdx.input.justTouched()){
				velocity=-15;
			}

			    for (int i=0;i<dusmanSayisi;i++){

					if(enemyX[i]<-Gdx.graphics.getWidth()/15){
						enemyX[i]=enemyX[i]+dusmanSayisi*mesafe;
						enemyOffset1[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());//düşmanların y ekseninde random olarak gelmesini sağladık.
						enemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
						enemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
					}
						else {
						enemyX[i]=enemyX[i]-enemyVelocity;//her oluşturduğumuz düşman seti için hız belirledik.
					}
					batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/2+ enemyOffset1[i],Gdx.graphics.getWidth()/11,Gdx.graphics.getHeight()/9);
					batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2+ enemyOffset2[i],Gdx.graphics.getWidth()/11,Gdx.graphics.getHeight()/9);
					batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2+ enemyOffset3[i],Gdx.graphics.getWidth()/11,Gdx.graphics.getHeight()/9);

					enemyCircle1[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/22,Gdx.graphics.getHeight()/2+ enemyOffset1[i]+Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth()/22);
					enemyCircle2[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/22,Gdx.graphics.getHeight()/2+ enemyOffset2[i]+Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth()/22);
					enemyCircle3[i]=new Circle(enemyX[i]+Gdx.graphics.getWidth()/22,Gdx.graphics.getHeight()/2+ enemyOffset3[i]+Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth()/22);
				}
			}
			if(birdY>0) {// bu if blokğuyla kuşun ekrandan çıkmamasını sağladık.
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			}else{
			gameState=2;
			}
			if(birdY>(background.getHeight()/2)+250){
			gameState=2;
		}

		    if (gameState==0){
			if(Gdx.input.justTouched()){
				gameState=1;
			}
		}
		else if(gameState==2){

			font2.draw(batch,"Oyun Bitti!Oynamak için tekrar tiklayin.",100,Gdx.graphics.getHeight()/2);
				if(Gdx.input.justTouched()){
					gameState=1;
					birdY =Gdx.graphics.getHeight()/2;
					for (int i=0;i<dusmanSayisi;i++){

						enemyOffset1[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
						enemyOffset2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());
						enemyOffset3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight());

						enemyX[i]=Gdx.graphics.getWidth() - enemy1.getWidth()/2 + i * mesafe;//Her enemy için aralarında bir mesafe olmasını sağlıyoruz.
						enemyCircle1[i]= new Circle();
						enemyCircle2[i]=new Circle();
						enemyCircle3[i]=new Circle();
					}
					velocity=0;
					scoreEnemy=0;
					score=0;

				}
			}
		batch.draw(bird,birdX,birdY,Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/10);//kuşun başlangıç konumunu çiziyoruz.
		font.draw(batch,String.valueOf(score),100,200);
        batch.end();
        birdCircle.set(birdX+Gdx.graphics.getWidth()/24,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/24);
        /*
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);
        */
        for(int i=0; i<dusmanSayisi;i++){
        	/*
        	shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/22,Gdx.graphics.getHeight()/2+ enemyOffset1[i]+Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth()/22);
			shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/22,Gdx.graphics.getHeight()/2+ enemyOffset2[i]+Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth()/22);
			shapeRenderer.circle(enemyX[i]+Gdx.graphics.getWidth()/22,Gdx.graphics.getHeight()/2+ enemyOffset3[i]+Gdx.graphics.getHeight()/18,Gdx.graphics.getWidth()/22);
            */
			if(Intersector.overlaps(birdCircle,enemyCircle1[i])
					||Intersector.overlaps(birdCircle,enemyCircle2[i])
					||Intersector.overlaps(birdCircle,enemyCircle3[i])){
				gameState=2;
			}

        }


        //shapeRenderer.end();
		}
		@Override
	public void dispose () {

	}
}
