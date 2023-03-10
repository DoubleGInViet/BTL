package utilz;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {
	
	public static final String PLAYER_ATLAS = "knight_mains.png";
	public static final String LEVEL_ATLAS = "outside_sprites.png";
	public static final String LEVEL_ONE_DATA = "level_one_data.png";
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		BufferedImage img = null;
		try {
			img = ImageIO.read(is);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	
	public static int[][] GetLevelData(){
		int[][] lvData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
		BufferedImage img = GetSpriteAtlas(LEVEL_ONE_DATA);
		for( int i = 0; i < img.getHeight(); i++) {
			for( int j =0; j< img.getWidth(); j++) {
				Color color = new Color(img.getRGB(j, i));
				int value = color.getRed();
				if( value >= 48) {
					value = 0;
				}
				lvData[i][j] = value;
			}
		}
		return lvData;
	}
}
