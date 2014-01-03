package eu32k.neonshooter.core.fx;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class SoundSet implements Serializable {
   public String id;
   public String artist;
   public String track;
   public String audioFile;
   public String controlFile;

   public SoundSet() {
      this("", "", "", "", "");
   }

   public SoundSet(String id, String artist, String track, String audio, String control) {
      this.id = id;
      this.artist = artist;
      this.track = track;
      this.audioFile = audio;
      this.controlFile = control;
   }

   @Override
   public void write(Json json) {
      json.writeValue("id", id);
      json.writeValue("artist", artist);
      json.writeValue("track", track);
      json.writeValue("audio", audioFile);
      json.writeValue("control", controlFile);
   }

   @Override
   public void read(Json json, JsonValue jsonData) {
      id = jsonData.getString("id");
      artist = jsonData.getString("artist");
      track = jsonData.getString("track");
      audioFile = jsonData.getString("audio");
      controlFile = jsonData.getString("control");
   }

   @Override
   public String toString() {
      return id + " - " + artist + " - " + track;
   }
}
