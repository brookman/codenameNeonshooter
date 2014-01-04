package eu32k.neonshooter.core.entitySystem.component;

import eu32k.gdx.artemis.base.Component;
import eu32k.neonshooter.core.fx.midi.ControlTracks.TrackType;

public class SpawnerComponent extends Component {
   public SpawnInputSource source;
   public TrackType track;
   public boolean spawnContinously;
   public float spawnFrequency = 1f;
   public boolean spawnedLastTime;
   public float timer;
   public int value = 0;
   public String note = "C";

   public SpawnerComponent init() {
      source = SpawnInputSource.Note;
      spawnContinously = false;
      spawnedLastTime = false;
      timer = 0;
      spawnFrequency = 1f;
      track = TrackType.Beat;
      value = 0;
      return this;
   }

   public enum SpawnInputSource {
      Note, AnyNote, On, ControllerValue
   }
}
