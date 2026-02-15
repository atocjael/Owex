/* Decompiler 42ms, total 347ms, lines 117 */
package app.view;

import app.ExMain;
import app.tools.Tools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class LoginScreenController {
   @FXML
   private TextField pseudo;
   @FXML
   private PasswordField password;
   @FXML
   private Button loginB;
   @FXML
   private Label errorLabel;
   private ExMain exMain;

   public void setMain(ExMain m) {
      this.exMain = m;
   }

   public void initialize() {
      this.loginB.disableProperty().bind(this.pseudo.textProperty().isEmpty());
      this.pseudo.setOnKeyPressed((e) -> {
         if (e.getCode().equals(KeyCode.DOWN)) {
            this.password.requestFocus();
         }

      });
      this.password.setOnKeyPressed((e) -> {
         if (e.getCode().equals(KeyCode.DOWN)) {
            this.loginB.requestFocus();
         }

      });
      this.loginB.setOnKeyPressed((e) -> {
         if (e.getCode().equals(KeyCode.ENTER)) {
            this.loginB.fire();
         }

      });
   }

   @FXML
   public void login() throws SQLException {
      String pseudoV = this.pseudo.getText().trim();
      String passwordV = this.password.getText().trim();
      if (!passwordV.isEmpty()) {
         Connection con = Tools.getConnected();
         if (con != null) {
            String sql = "SELECT * from logintable WHERE pseudo=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, pseudoV);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
               String passwordB = rst.getString("password");
               if (passwordV.equals(passwordB)) {
                  this.exMain.showRegistrationScreen();
                  this.exMain.setPseudo(pseudoV);
                  this.pseudo.setText("");
                  this.password.setText("");
                  this.exMain.getAppHomeController().setDisAllowDeclarerFalse();
                  if (pseudoV.equals("atomos") || pseudoV.equals("rich") ) {
                     this.exMain.getAppHomeController().setisNotAtomosFalse();
                  }

                  this.exMain.getAppHomeController().setNoUpdatableFalse();
                  this.exMain.getAppHomeController().setUpSumGrid();
                  this.exMain.getAppHomeController().sumUp();
                  this.exMain.getAppHomeController().giveTodayStatics();
                  this.exMain.getAppHomeController().getLogin().setText("Log out");
                  this.exMain.getAppHomeController().populateIolDirectory();
               } else {
                  this.writeLoginError("Mot de passe incorrect");
                  this.password.setText("");
                  this.password.requestFocus();
               }
            } else {
               this.writeLoginError("Pas d'utilisateur avec " + pseudoV + " comme pseudo");
               this.pseudo.setText("");
               this.password.setText("");
               this.pseudo.requestFocus();
            }
         }
      } else {
         this.writeLoginError("Entrer votre mot de passe");
         this.password.requestFocus();
      }

   }

   public void writeLoginError(String str) {
      this.errorLabel.setText(str);
      PauseTransition wait = new PauseTransition(Duration.seconds(3.0D));
      wait.setOnFinished((e) -> {
         this.errorLabel.setText("");
      });
      wait.play();
   }

   public void clearField() {
      this.pseudo.setText("");
      this.password.setText("");
   }
}
