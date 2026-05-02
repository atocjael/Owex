/* Decompiler 25ms, total 331ms, lines 88 */
package app.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import app.tools.Tools;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserAdderController {
   @FXML
   private TextField userNom;
   @FXML
   private TextField userPrenom;
   @FXML
   private TextField userEmail;
   @FXML
   private TextField userTel;
   @FXML
   private TextField userPass;
   @FXML
   private TextField userPseudo;
   @FXML
   private ComboBox<String> userRole;
   @FXML
   private ComboBox<String> userTitle;
   @FXML
   private ComboBox<String> userEx;
   @FXML
   private ComboBox<String> userOp;
   @FXML
   private Button add;
   private Stage stage;

   public void setStage(Stage stg) {
      this.stage = stg;
   }

   public void initialize() {
      this.userRole.getItems().addAll(new String[]{"Admin", "User"});
      this.userRole.getSelectionModel().select(0);
      this.userTitle.getItems().addAll(new String[]{"Dr.", "Mr.", "Mme.", "Mlle"});
      this.userTitle.getSelectionModel().select(0);
      this.userEx.getItems().addAll(new String[]{"2", "4"});
      this.userEx.getSelectionModel().select(1);
      this.userOp.getItems().addAll(new String[]{"0", "50"});
      this.userOp.getSelectionModel().select(0);
      Node[] nodes = new Node[]{this.userNom, this.userPrenom, this.userEmail, this.userPseudo, this.userPass, this.userTel, this.userRole, this.userTitle, this.userEx, this.userOp};
      Tools.addNavigation(nodes);
      this.add.disableProperty().bind(this.userNom.textProperty().isEmpty().or(this.userPrenom.textProperty().isEmpty().or(this.userEmail.textProperty().isEmpty().or(this.userPseudo.textProperty().isEmpty().or(this.userPass.textProperty().isEmpty().or(this.userTel.textProperty().isEmpty()))))));
   }

   @FXML
   public void addUser() {
      Connection conn = Tools.getConnected();
      String sql = "INSERT INTO logintable (pseudo,password,nom,prenom,role,email,telephone,title,expourcentage,surpourcentage) VALUES (?,?,?,?,?,?,?,?,?,?)";

      try {
         PreparedStatement stm = conn.prepareStatement(sql);
         stm.setString(1, this.userPseudo.getText().trim());
         stm.setString(2, this.userPass.getText().trim());
         stm.setString(3, this.userNom.getText().trim());
         stm.setString(4, this.userPrenom.getText().trim());
         stm.setString(5, ((String)this.userRole.getValue()).trim());
         stm.setString(6, this.userEmail.getText().trim());
         stm.setString(7, this.userTel.getText().trim());
         stm.setString(8, ((String)this.userTitle.getValue()).trim());
         stm.setString(9, ((String)this.userEx.getValue()).trim());
         stm.setString(10, ((String)this.userOp.getValue()).trim());
         stm.executeUpdate();
         Tools.showEphemereText("User added successfully");
         this.stage.close();
      } catch (SQLException var4) {
         var4.printStackTrace();
      }

   }

   @FXML
   public void cancel() {
      this.stage.close();
   }
}
