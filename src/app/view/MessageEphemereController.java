/* Decompiler 3ms, total 324ms, lines 26 */
package app.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MessageEphemereController {
   @FXML
   private Pane ephemPane;
   @FXML
   private Label ephemLabel;
   @FXML
   private VBox box;

   public void initialize() {
      this.ephemPane.setBackground(Background.EMPTY);
      this.box.setBackground(Background.EMPTY);
   }

   public Label getEphemLabel() {
      return this.ephemLabel;
   }
}
