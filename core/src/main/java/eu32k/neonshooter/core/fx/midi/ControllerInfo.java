package eu32k.neonshooter.core.fx.midi;

public class ControllerInfo {
	public int channel;
	public int type;
	public int initValue;
	public int value;
	public String trackName;

	public ControllerInfo(int channel, int note) {
		this.channel = channel;
		this.type = note;
	}

}
