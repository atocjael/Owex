/* Decompiler 13ms, total 370ms, lines 69 */
package app.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import app.ExMain;
import app.tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerClientController {
   @FXML
   private TextField clientName;
   @FXML
   private TextField clientHost;
   @FXML
   private PasswordField clientPass;
   private Stage stage;

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void setMain(ExMain m) {
   }

   public void initialize() {
      Node[] nodes = new Node[]{this.clientName, this.clientHost, this.clientPass};
      Tools.addNavigation(nodes);
   }

   public void registerClient() {
      Connection conn = Tools.getConnected();
      if (this.verifyClientData()) {
         String var10000 = this.clientName.getText().trim();
         String newClient = var10000 + "'@'" + this.clientHost.getText().trim();
         String sql = "CREATE USER IF NOT EXISTS '" + newClient + "' IDENTIFIED BY '" + this.clientPass.getText().trim() + "'";
         String privileges = "GRANT ALL PRIVILEGES ON *.* TO '" + newClient + "' WITH GRANT OPTION";
         System.out.println(sql + "\n" + privileges);

         try {
            Statement stm = conn.createStatement();
            stm.executeUpdate(sql);
            stm.executeUpdate(privileges);
         } catch (SQLException var6) {
            var6.printStackTrace();
         }
      }

      this.stage.close();
   }

   public boolean verifyClientData() {
      boolean ok = true;
      if (this.clientName.getText().isBlank() || this.clientHost.getText().isBlank() || this.clientPass.getText().isBlank()) {
         ok = false;
      }

      return ok;
   }

   public void cancelCreation() {
      this.stage.close();
   }
}
