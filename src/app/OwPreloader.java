/* Decompiler 28ms, total 455ms, lines 86 */
package app;

import java.io.IOException;

import app.view.AppPreloaderController;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification.Type;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OwPreloader extends Preloader {
   private Scene preloaderScene;
   private AppPreloaderController controller;
   private Stage preloaderStage;
   // $FF: synthetic field
   private static volatile int[] $SWITCH_TABLE$javafx$application$Preloader$StateChangeNotification$Type;

   public void init() throws IOException {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/AppPreloader.fxml"));
      VBox box = (VBox)loader.load();
      this.preloaderScene = new Scene(box);
      this.controller = (AppPreloaderController)loader.getController();
   }

   public void start(Stage s) throws Exception {
      this.preloaderStage = s;
      this.preloaderStage.initStyle(StageStyle.UNDECORATED);
      this.preloaderStage.setScene(this.preloaderScene);
      this.preloaderScene.getStylesheets().add(ExMain.class.getResource("sources/preloader.css").toExternalForm());
      this.preloaderStage.getIcons().addAll(new Image[]{new Image(ExMain.class.getResourceAsStream("images/owexl.PNG"))});
      this.preloaderStage.show();
   }

   public void handleApplicationNotification(PreloaderNotification info) {
      if (info instanceof ProgressNotification) {
         ProgressNotification pn = (ProgressNotification)info;
         this.controller.setProgressP(pn.getProgress());
      }

   }

   public void handleStateChangeNotification(StateChangeNotification info) {
      Type type = info.getType();
      switch($SWITCH_TABLE$javafx$application$Preloader$StateChangeNotification$Type()[type.ordinal()]) {
      case 3:
         this.preloaderStage.hide();
      default:
      }
   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$javafx$application$Preloader$StateChangeNotification$Type() {
      int[] var10000 = $SWITCH_TABLE$javafx$application$Preloader$StateChangeNotification$Type;
      if (var10000 != null) {
         return var10000;
      } else {
         int[] var0 = new int[Type.values().length];

         try {
            var0[Type.BEFORE_INIT.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            var0[Type.BEFORE_LOAD.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
         }

         try {
            var0[Type.BEFORE_START.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

         $SWITCH_TABLE$javafx$application$Preloader$StateChangeNotification$Type = var0;
         return var0;
      }
   }
}
