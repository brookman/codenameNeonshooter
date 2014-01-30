package eu32k.neonshooter.core.input;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

public class Controls {

   public boolean move = false;
   public Vector2 moveDirection = new Vector2();

   public boolean shoot = false;
   public Vector2 shootDirection = new Vector2();

   public Touchpad padLeft;
   public Touchpad padRight;

   public boolean slowTime = false;

   private Controller controller;

   public void create() {
      for (Controller c : Controllers.getControllers()) {
         Gdx.app.log("Controls", "Controller: " + c.getName());
         controller = c;
         break;
      }
   }

   public void update() {
      // for (int i = 0; i < 10; i++) {
      // Gdx.app.log("Control", "button " + i + " " + controller.getButton(i));
      // }

      slowTime = false;

      if (controller != null) {
         moveDirection.set(controller.getAxis(0), -controller.getAxis(1));
         shootDirection.set(controller.getAxis(2), -controller.getAxis(3));
         slowTime |= controller.getButton(5);
      } else if (Gdx.app.getType() == ApplicationType.Android) {
         moveDirection.set(padLeft.getKnobPercentX(), padLeft.getKnobPercentY());
         shootDirection.set(padRight.getKnobPercentX(), padRight.getKnobPercentY()).nor();
      } else {
         moveDirection.set(0, 0);
         if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveDirection.add(0, 1);
         }
         if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveDirection.add(0, -1);
         }
         if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveDirection.add(-1, 0);
         }
         if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveDirection.add(1, 0);
         }
         shootDirection.set(Gdx.input.getX() - Gdx.graphics.getWidth() / 2.0f, -(Gdx.input.getY() - Gdx.graphics.getHeight() / 2.0f)).nor();
      }

      move = moveDirection.len() > 0.1f;
      shoot = shootDirection.len() > 0.1f;

      if (moveDirection.len() > 1) {
         moveDirection.nor();
      }

      slowTime |= Gdx.input.isKeyPressed(Input.Keys.T) || Gdx.input.isButtonPressed(Buttons.LEFT);

   }
}
