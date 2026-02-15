/* Decompiler 33ms, total 412ms, lines 77 */
package app.view;

import app.ExMain;
import app.tools.Tools;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class PickTimeController {
   @FXML
   private ComboBox<String> periode;
   @FXML
   private Button save;
   @FXML
   private Button savePrint;
   private ExMain mainApp;

   public void setMainApp(ExMain m) {
      this.mainApp = m;
   }

   public void initialize() {
      this.save.disableProperty().bind(this.periode.valueProperty().isNull());
      this.savePrint.disableProperty().bind(this.periode.valueProperty().isNull());
      Connection conn = Tools.getConnected();
      String sql = "SELECT DISTINCT monthYear FROM REGISTER";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            this.periode.getItems().add(rst.getString("monthYear"));
         }
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

   }

   public boolean checkPeriode() {
      String per = (String)this.periode.getValue();
      return !per.isBlank();
   }

   @FXML
   public void save() {
      if (this.checkPeriode()) {
         this.mainApp.setPeriode(((String)this.periode.getValue()).trim());
         this.mainApp.setIsIntervalSet(true);
         this.mainApp.getTimeStage().close();
      }

   }

   @FXML
   public void saveAndPrint() {
      if (this.checkPeriode()) {
         this.mainApp.setPeriode(((String)this.periode.getValue()).trim());
         this.mainApp.setIsIntervalSet(true);
         this.mainApp.setIsForPrinting(true);
         this.mainApp.getTimeStage().close();
      }

   }

   @FXML
   public void cancelPeriodes() {
      this.mainApp.setIsIntervalSet(false);
      this.mainApp.getTimeStage().close();
   }
}
