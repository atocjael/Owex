/* Decompiler 26ms, total 333ms, lines 53 */
package app.view;

import app.tools.Tools;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class DrugChoserController {
   @FXML
   private ComboBox<String> drugCombo;
   private Stage stage;

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void initialize() {
      Connection conn = Tools.getConnected();
      String sql = "SELECT drug, delegue FROM drugs";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            this.drugCombo.getItems().add(rst.getString("drug"));
         }
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

      this.drugCombo.valueProperty().addListener((obs, old, nv) -> {
         if (!nv.equals("")) {
            String sqlu = "DELETE FROM drugs WHERE drug='" + nv + "'";

            try {
               Statement st = conn.createStatement();
               st.executeUpdate(sqlu);
            } catch (SQLException var7) {
               var7.printStackTrace();
            }

            this.stage.close();
         }

      });
   }
}
