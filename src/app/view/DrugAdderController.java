/* Decompiler 10ms, total 434ms, lines 60 */
package app.view;

import app.tools.Tools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DrugAdderController {
   @FXML
   private TextField delegueName;
   @FXML
   private TextField drugName;
   @FXML
   private Button addButton;
   @FXML
   private Button cancelButton;
   private Stage stage;

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void initialize() {
      Node[] nodes = new Node[]{this.drugName, this.delegueName, this.addButton, this.cancelButton};
      Tools.addNavigation(nodes);
   }

   @FXML
   public void addDrug() {
      if (!this.delegueName.getText().isBlank() && !this.drugName.getText().isBlank()) {
         Connection conn = Tools.getConnected();
         String sql = "INSERT INTO drugs(drug,delegue) VALUES (?,?)";

         try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, this.drugName.getText().trim());
            pst.setString(2, this.delegueName.getText().trim());
            pst.execute();
            Tools.showEphemereText("Drug added successfully");
            this.delegueName.setText("");
            this.drugName.setText("");
            this.drugName.requestFocus();
         } catch (SQLException var4) {
            var4.printStackTrace();
         }
      }

   }

   @FXML
   public void cancelAddition() {
      this.stage.close();
   }
}
