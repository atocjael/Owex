/* Decompiler 31ms, total 370ms, lines 62 */
package app.view;

import app.ExMain;
import app.tools.Tools;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class UserDeleterController {
   @FXML
   private ComboBox<String> userCombo;
   private Stage stage;
   private ExMain main;

   public void setMain(ExMain m) {
      this.main = m;
   }

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void initialize() {
      Connection conn = Tools.getConnected();
      String sql = "SELECT pseudo from loginTable";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            this.userCombo.getItems().add(rst.getString("pseudo"));
         }
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

      this.userCombo.valueProperty().addListener((obs, old, nv) -> {
         if (!nv.equals("")) {
            String sqlu = "DELETE FROM loginTable WHERE pseudo='" + nv + "'";

            try {
               Statement st = conn.createStatement();
               if (!nv.equals(this.main.getPseudo())) {
                  st.executeUpdate(sqlu);
                  this.stage.close();
               } else {
                  Tools.showEphemereText("You can't delete the current user");
               }
            } catch (SQLException var7) {
               var7.printStackTrace();
            }
         }

      });
   }
}
