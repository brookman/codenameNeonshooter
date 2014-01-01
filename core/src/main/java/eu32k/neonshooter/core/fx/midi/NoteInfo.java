package eu32k.neonshooter.core.fx.midi;

public class NoteInfo {
	public int channel;
	public int note;
	public boolean on;
	public String trackName;

	public NoteInfo(int channel, int note) {
		this.channel = channel;
		this.note = note;
	}
}
