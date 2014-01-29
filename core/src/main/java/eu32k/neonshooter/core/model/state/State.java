package eu32k.neonshooter.core.model.state;

import java.util.HashSet;
import java.util.Set;

public class State {
   public String name;
   public Set<State> outStates = new HashSet<State>();

   public State(String name) {
      this.name = name;
   }

   public void add(State... states) {
      for (State state : states) {
         outStates.add(state);
      }
   }
}
