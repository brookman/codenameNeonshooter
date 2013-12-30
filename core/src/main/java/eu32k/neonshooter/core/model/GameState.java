package eu32k.neonshooter.core.model;

public class GameState {
	public String nextLevel = "levels/test.tmx";

	private LevelData level;

	public GameState() {
		level = new LevelData();
	}

	public LevelData level() {
		return level;
	}

	public void create() {

	}
}
