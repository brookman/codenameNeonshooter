package ch.digitalmeat.poc.midishizzle;

public class ControllerInfo {
	public int channel;
	public int type;
	public int initValue;
	public int value;

	public ControllerInfo(int channel, int note) {
		this.channel = channel;
		this.type = note;
	}

}
