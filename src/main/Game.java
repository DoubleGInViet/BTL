package main;

import java.awt.Graphics;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;


public class Game implements Runnable {
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;
	
	private Playing playing;
	private Menu menu;
	
	public static final int TILES_DEFAULT_SIZE = 32;
	public static final float SCALE = 1.5f;
	public static final int TILES_IN_WIDTH = 26;
	public static final int TILES_IN_HEIGHT = 14;
	public static final int TILE_SIZE = (int) (TILES_DEFAULT_SIZE*SCALE);
	public static final int GAME_WIDTH = TILE_SIZE * TILES_IN_WIDTH;
	public static final int GAME_HEIGHT = TILE_SIZE * TILES_IN_HEIGHT;
	
	
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		startGameLoop();
		
	}

	public void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
		switch( Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		default:
			break;
		}
		
		
	}
	
	public void render(Graphics g) {
		switch( Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		default:
			break;
		}
		
		
		
	}
	
	public void windowFocusLost() {
		if( Gamestate.state == Gamestate.PLAYING) {
			playing.getPlayer().resetDirBooleans();
		}
	}
	
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		double deltaU = 0;
		double deltaF = 0;

		int frames = 0;
		int updates = 0;

		while (true) {

			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			if (deltaU >= 1) {
				deltaU--;
				updates++;
			}

			deltaF += (currentTime - previousTime) / timePerFrame;
			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}
			previousTime = currentTime;

//			}

		}
		
		

	}

}
