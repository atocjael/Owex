/* Decompiler 31ms, total 339ms, lines 55 */
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

public class PrescriptionPeriodeChoserController {
   @FXML
   private ComboBox<String> periodeCombo;
   private ExMain mainApp;
   private Stage stage;

   public void setMain(ExMain main) {
      this.mainApp = main;
   }

   public void setStage(Stage stg) {
      this.stage = stg;
      this.stage.setOnCloseRequest((e) -> {
         this.mainApp.setIsOperativePeriodeSet(false);
      });
   }

   public void initialize() {
      Connection conn = Tools.getConnected();
      String sql = "SELECT DISTINCT prescriptionMonth FROM prescription";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            this.periodeCombo.getItems().add(rst.getString("prescriptionMonth"));
         }
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

      this.periodeCombo.valueProperty().addListener((obs, old, nv) -> {
         if (!nv.equals("")) {
            this.mainApp.setOperativePeriode(nv);
            this.mainApp.setIsOperativePeriodeSet(true);
            this.stage.close();
         }

      });
   }
}
