package com.javarush.games.moonlander;

import com.javarush.engine.cell.*;

public class MoonLanderGame extends Game {
    public static final int WIDTH = 64;
    public static final int HEIGHT = 64;
    private Rocket rocket;
    private GameObject landscape;
    private int boost;
    private boolean isUpPressed;
    private boolean isLeftPressed;
    private boolean isRightPressed;
    private GameObject platform;
    private boolean isGameStopped;
    private int score;

    @Override
    public void setScore(int score) {
        super.setScore(score);
    }

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
        showGrid(false);
    }

    private void createGame(){
        isLeftPressed = false;
        isRightPressed = false;
        isUpPressed = false;
        isGameStopped = false;
        score = 1000;
        createGameObjects();
        drawScene();
        setTurnTimer(50);
    }

    private void drawScene(){
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++){
                setCellColor(i, j, Color.ORANGE);
            }
        }
        rocket.draw(this);
        landscape.draw(this);
    }

    private void createGameObjects(){
        rocket = new Rocket(WIDTH/2, 0);
        landscape = new GameObject(0, 25, ShapeMatrix.LANDSCAPE);
        platform = new GameObject(23, MoonLanderGame.HEIGHT-1, ShapeMatrix.PLATFORM);
    }

    @Override
    public void onTurn(int step) {
        rocket.move(isUpPressed, isLeftPressed, isRightPressed  );
        check();
        if (score > 0) score --;
        setScore(score);
        drawScene();
    }

    @Override
    public void setCellColor(int x, int y, Color color) {
        if (x < WIDTH && x > 0 && y < HEIGHT && y > 0){
        super.setCellColor(x, y, color);
        }
    }
    @Override
    public void onKeyPress(Key key){
        switch (key){
            case UP:
                isUpPressed = true;
                break;
            case RIGHT:
                isLeftPressed = false;
                isRightPressed = true;
                break;
            case LEFT:
                isRightPressed = false;
                isLeftPressed = true;
                break;
            case SPACE:
                if(isGameStopped){
                    createGame();
                    return;
                }

        }
    }
    @Override
    public void onKeyReleased(Key key){
        switch (key){
            case UP:
                isUpPressed = false;
            case RIGHT:
                isRightPressed = false;
            case LEFT:
                isLeftPressed = false;
        }
    }
    private void check (){
        if(rocket.isCollision(landscape)){
            gameOver();
        }else if (rocket.isCollision(platform) && rocket.isStopped()){
            win();
        }

    }
    private void gameOver(){
        rocket.crash();
        showMessageDialog(Color.GREENYELLOW, "Game Over", Color.RED, 75);
        isGameStopped = true;
        stopTurnTimer();
        score = 0;
    }
    private void win(){
        rocket.land();
        isGameStopped = true;
        showMessageDialog (Color.BLUE, "WIN", Color.AQUA, 75);
        stopTurnTimer();
    }
    private void isStoped(){

    }
}
