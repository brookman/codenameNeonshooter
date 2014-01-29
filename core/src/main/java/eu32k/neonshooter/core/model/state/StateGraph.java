package eu32k.neonshooter.core.model.state;

public class StateGraph {

   public StateGraph() {
      State intro = new State("intro");
      State mainMenu = new State("mainMenu");
      State mainSettings = new State("mainSettings");
      State inGameSettings = new State("inGameSettings");
      State start = new State("start");
      State game = new State("game");
      State pause = new State("pause");
      State gameOver = new State("gameOver");

      intro.add(mainMenu);

      mainMenu.add(mainSettings, start);

      mainSettings.add(mainMenu);

      start.add(mainMenu, game);

      game.add(pause, gameOver);

      pause.add(game, mainMenu, inGameSettings);

      inGameSettings.add(pause);

   }
}
