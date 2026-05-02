/* Decompiler 14ms, total 363ms, lines 80 */
package app.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.ExMain;
import app.tools.Tools;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExamAdderController {
   @FXML
   private TextField examName;
   @FXML
   private TextField examPrice;
   @FXML
   private ComboBox<String> type;
   @FXML
   private CheckBox showCheck;
   @FXML
   private Button save;
   private Stage stage;
   private ExMain main;

   public void setMain(ExMain m) {
      this.main = m;
   }

   public void setStage(Stage st) {
      this.stage = st;
   }

   public void initialize() {
      BooleanBinding bool = this.examName.textProperty().isEmpty().or(this.examPrice.textProperty().isEmpty());
      this.type.getItems().add("Exam");
      this.type.getItems().add("Surgery");
      this.save.disableProperty().bind(bool);
      Node[] nodes = new Node[]{this.examName, this.examPrice, this.type, this.showCheck};
      Tools.addNavigation(nodes);
   }

   @FXML
   public void saveExam() {
      String typ = (String)this.type.getValue();
      String sql = "INSERT INTO ";
      sql = sql + (typ == "Exam" ? "exams" : "surgeries");
      sql = sql + (typ == "Exam" ? "(exam" : "(surgery");
      sql = sql + ", price, shown)";
      sql = sql + " VALUES (?,?,?)";
      Connection conn = Tools.getConnected();

      try {
         PreparedStatement pst = conn.prepareStatement(sql);
         pst.setString(1, this.examName.getText().trim());
         pst.setString(2, this.examPrice.getText().trim());
         pst.setBoolean(3, this.showCheck.isSelected());
         System.out.println(sql);
         pst.executeUpdate();
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

      this.main.getRegistrationScreenController().populateMainGrid();
      this.main.getAppHomeController().setUpSumGrid();
      this.main.getAppHomeController().sumUp();
      this.stage.close();
   }

   @FXML
   public void cancelAdd() {
      this.stage.close();
   }
}
