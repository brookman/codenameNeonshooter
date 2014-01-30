package eu32k.neonshooter.core.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import eu32k.neonshooter.core.model.loading.NeonScreen;

public class Ui {
   private Map<Class<?>, NeonScreen> screens;
   private Stack<Class<?>> stack;
   private LoadingScreen loadingScreen;

   public Ui() {
      screens = new HashMap<Class<?>, NeonScreen>();
      stack = new Stack<Class<?>>();
   }

   public void create() {
      loadingScreen = addScreen(new LoadingScreen());
      addScreen(new IntroScreen());
      addScreen(new MainMenuScreen());
      addScreen(new SettingsScreen());
      addScreen(new LevelSelectScreen());
      addScreen(new InGameScreen());
      addScreen(new PauseScreen());
      addScreen(new GameOverScreen());

      stack.push(MainMenuScreen.class);
      stack.push(IntroScreen.class);
   }

   private <T extends NeonScreen> T addScreen(T screen) {
      screens.put(screen.getClass(), screen);
      return screen;
   }

   public void pushScreen(Class<?> clazz) {
      stack.push(clazz);
      showTopScreen();
   }

   public void popScreen() {
      popScreens(1);
   }

   public void popScreens(int numberToPop) {
      for (int i = 0; i < numberToPop; i++) {
         stack.pop();
      }
      showTopScreen();
   }

   public void swapScreen(Class<?> clazz) {
      stack.pop();
      pushScreen(clazz);
   }

   public void showTopScreen() {
      loadingScreen.load(screens.get(stack.peek()));
   }
}
