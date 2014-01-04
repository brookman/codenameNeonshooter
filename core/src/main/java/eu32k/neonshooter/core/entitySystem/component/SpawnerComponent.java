package eu32k.neonshooter.core.entitySystem.component;

import eu32k.gdx.artemis.base.Component;
import eu32k.neonshooter.core.fx.midi.ControlTracks.TrackType;

public class SpawnerComponent extends Component {
   public SpawnInputSource source;
   public TrackType track;

   public enum SpawnInputSource {
      Note, AnyNote, On, ControllerValue
   }

   public SpawnerComponent init() {
      return this;
   }
}
