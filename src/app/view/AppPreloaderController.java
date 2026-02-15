/* Decompiler 7ms, total 574ms, lines 20 */
package app.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class AppPreloaderController {
   @FXML
   private ProgressBar progress;
   @FXML
   private Label realisateur;

   public void initialize() {
   }

   public void setProgressP(double doub) {
      this.progress.setProgress(doub);
   }
}
