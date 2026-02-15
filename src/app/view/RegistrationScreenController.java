/* Decompiler 297ms, total 670ms, lines 545 */
package app.view;

import app.ExMain;
import app.tools.Tools;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RegistrationScreenController {
   @FXML
   private GridPane mainGrid;
   private Label patientIdLabel = new Label("Patient ID");
   private TextField patientIdText = new TextField();
   private TextField factureNoText = new TextField();
   private Label factureNoLabel = new Label("Numero de la Facture: ");
   private ArrayList<Label> examLabels = new ArrayList<Label>();
   private ArrayList<Label> surgeryLabels = new ArrayList<Label>();
   private ArrayList<CheckBox> examChecks = new ArrayList<CheckBox>();
   private ArrayList<CheckBox> surgeryChecks = new ArrayList<CheckBox>();
   private DatePicker datepicker = new DatePicker();
   private boolean goOn = true;
   private String consommablePrice = "";
   private ExMain mainApp;
   @FXML
   private Button register;

   public void setMain(ExMain m) {
      this.mainApp = m;
   }

   public ArrayList<Label> getExamLabels() {
      return this.examLabels;
   }

   public ArrayList<Label> getSurgeryLabels() {
      return this.surgeryLabels;
   }

   public void initialize() {
      BooleanBinding patientId_FactureN = this.patientIdText.textProperty().isEmpty().or(this.factureNoText.textProperty().isEmpty());
      this.register.disableProperty().bind(patientId_FactureN);
      this.populateMainGrid();
      this.bindChecks();
      this.addEventOnExamChecks();
      this.addEventOnSurgeryChecks();
      UnaryOperator<Change> filter = (change) -> {
         String text = change.getText();
         return text.matches("\\+?[0-9]*") ? change : null;
      };
      TextFormatter<String> textFormatter = new TextFormatter<String>(filter);
      this.patientIdText.setTextFormatter(textFormatter);
      UnaryOperator<Change> filter_f = (change) -> {
         String text = change.getText();
         return text.matches("\\+?[0-9]*") ? change : null;
      };
      TextFormatter<String> textFormatter_f = new TextFormatter<String>(filter_f);
      this.factureNoText.setTextFormatter(textFormatter_f);
      this.datepicker.setDayCellFactory((param) -> {
         return new DateCell() {
            public void updateItem(LocalDate localDate, boolean empty) {
               super.updateItem(localDate, empty);
               this.setDisable(empty || localDate.compareTo(LocalDate.now()) > 0);
            }
         };
      });
      this.patientIdText.setOnKeyPressed((e) -> {
         if (e.getCode().equals(KeyCode.DOWN)) {
            this.factureNoText.requestFocus();
         }

      });
   }

   public String getExams() {
      String examText = "";

      for(int i = 0; i < this.examChecks.size(); ++i) {
         if (((CheckBox)this.examChecks.get(i)).isSelected()) {
            if (examText.length() != 0) {
               examText = examText + "/";
            }

            examText = examText + ((CheckBox)this.examChecks.get(i)).getText().trim();
         }
      }

      return examText;
   }

   public String getSurgeries() {
      String examText = "";

      for(int i = 0; i < this.surgeryChecks.size(); ++i) {
         if (((CheckBox)this.surgeryChecks.get(i)).isSelected()) {
            if (examText.length() != 0) {
               examText = examText + "/";
            }

            examText = examText + ((CheckBox)this.surgeryChecks.get(i)).getText().trim();
         }
      }

      return examText;
   }

   public boolean isThereExam() {
      for(int i = 0; i < this.examChecks.size(); ++i) {
         if (((CheckBox)this.examChecks.get(i)).isSelected()) {
            return true;
         }
      }

      return false;
   }

   public boolean isThereSurgery() {
      for(int i = 0; i < this.surgeryChecks.size(); ++i) {
         if (((CheckBox)this.surgeryChecks.get(i)).isSelected()) {
            return true;
         }
      }

      return false;
   }

   public void emptyRegister() {
      int i;
      for(i = 0; i < this.examChecks.size(); ++i) {
         ((CheckBox)this.examChecks.get(i)).setSelected(false);
      }

      for(i = 0; i < this.surgeryChecks.size(); ++i) {
         ((CheckBox)this.surgeryChecks.get(i)).setSelected(false);
      }

      this.patientIdText.setText("");
      this.factureNoText.setText("");
   }

   @FXML
   public void registerExam() {
      if (!this.isThereExam() && !this.isThereSurgery()) {
         Tools.showEphemereText("Aucun examen ni operation a enregistrer");
         this.emptyRegister();
      } else {
         Connection conn = Tools.getConnected();
         String today = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
         String sql = "INSERT INTO register(patientId, exams, surgery, doctor, monthYear, date, nofacture, registerDate, consommable) VALUES (?,?,?,?,?,?,?,?,?)";

         try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, this.patientIdText.getText().trim());
            ps.setString(2, this.getExams());
            ps.setString(3, this.getSurgeries());
            String surgeries = this.getSurgeries();
            if (!surgeries.isBlank()) {
               String[] surgeryList = surgeries.split("/");
               this.createConsommableGridPane(surgeryList);
            }

            ps.setString(4, this.mainApp.getPseudo());
            String dat = ((LocalDate)this.datepicker.getValue()).toString();
            String da = dat.substring(0, 7);
            String[] dataSplit = dat.split("-");
            String datus = dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
            ps.setString(5, da);
            ps.setString(6, datus);
            ps.setString(7, this.factureNoText.getText().trim());
            ps.setString(8, today);
            ps.setString(9, this.consommablePrice);
            if (this.goOn) {
               ps.executeUpdate();
               this.consommablePrice = "";
            }

            Tools.showEphemereText("Exams registered.");
            this.emptyRegister();
            this.mainApp.getAppHomeController().sumUp();
            this.mainApp.getAppHomeController().giveTodayStatics();
            this.patientIdText.requestFocus();
         } catch (SQLException var10) {
            var10.printStackTrace();
         }
      }

   }

   public void populateMainGrid() {
      this.clearGridPane(this.mainGrid);
      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
      double height = size.getHeight();
      this.getExamSurgeryChecksAndLabels();
      int length = this.examChecks.size() + this.surgeryChecks.size() + 4;
      double restr = height / (double)length;
      this.mainGrid.setVgap(restr / 5.0D);
      this.mainGrid.addRow(0, new Node[]{this.patientIdLabel, this.patientIdText});
      this.mainGrid.addRow(1, new Node[]{this.factureNoLabel, this.factureNoText});
      this.mainGrid.addRow(2, new Node[]{new Label(""), new Label()});
      int index = 3;

      int i;
      for(i = 0; i < this.examChecks.size(); ++i) {
         if (i == 0) {
            this.mainGrid.addRow(index, new Node[]{new Label("Exams"), (Node)this.examChecks.get(i)});
         } else {
            this.mainGrid.addRow(index, new Node[]{new Label(), (Node)this.examChecks.get(i)});
         }

         ++index;
      }

      this.mainGrid.addRow(index, new Node[]{new Label(), new Label()});
      ++index;

      for(i = 0; i < this.surgeryChecks.size(); ++i) {
         if (i == 0) {
            this.mainGrid.addRow(index, new Node[]{new Label("Surgeries"), (Node)this.surgeryChecks.get(i)});
         } else {
            this.mainGrid.addRow(index, new Node[]{new Label(), (Node)this.surgeryChecks.get(i)});
         }

         ++index;
      }

      this.mainGrid.addRow(index, new Node[]{new Label(), new Label()});
      ++index;
      this.mainGrid.addRow(index, new Node[]{new Label("Date of the exam: "), this.datepicker});
      this.datepicker.setValue(LocalDate.now());
   }

   public void clearGridPane(GridPane grid) {
      List<Node> children = grid.getChildren();
      grid.getChildren().removeAll(children);
      grid.getRowConstraints().removeAll(grid.getRowConstraints());
   }

   public void getExamSurgeryChecksAndLabels() {
      this.clearArrays();
      Connection conn = Tools.getConnected();
      String exSql = "SELECT * FROM exams";
      String surSql = "SELECT * FROM surgeries";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(exSql);

         CheckBox checks;
         Label labs;
         while(rst.next()) {
            if (rst.getBoolean("shown")) {
               checks = new CheckBox(rst.getString("exam"));
               labs = new Label(rst.getString("exam"));
               this.examChecks.add(checks);
               this.examLabels.add(labs);
            }
         }

         rst.close();
         rst = stm.executeQuery(surSql);

         while(rst.next()) {
            if (rst.getBoolean("shown")) {
               checks = new CheckBox(rst.getString("surgery"));
               labs = new Label(rst.getString("surgery"));
               this.surgeryChecks.add(checks);
               this.surgeryLabels.add(labs);
            }
         }

         rst.close();
      } catch (SQLException var8) {
         var8.printStackTrace();
      }

   }

   public void clearArrays() {
      this.examChecks.clear();
      this.surgeryChecks.clear();
      this.examLabels.clear();
      this.surgeryLabels.clear();
   }

   public void addEventOnExamChecks() {
      for(int i = 0; i < this.examChecks.size(); ++i) {
         CheckBox check = (CheckBox)this.examChecks.get(i);
         String label = ((Label)this.examLabels.get(i)).getText();
         check.setOnAction((e) -> {
            if (check.isSelected()) {
               String id = this.patientIdText.getText().trim();
               String facture = this.factureNoText.getText().trim();
               this.checkForTheSameExam(id, label, check, facture);
            } else {
               System.out.println(label + " is unchecked");
            }

         });
      }

   }

   public void addEventOnSurgeryChecks() {
      for(int i = 0; i < this.surgeryChecks.size(); ++i) {
         CheckBox check = (CheckBox)this.surgeryChecks.get(i);
         String label = ((Label)this.surgeryLabels.get(i)).getText();
         check.setOnAction((e) -> {
            if (check.isSelected()) {
               String id = this.patientIdText.getText().trim();
               String facture = this.factureNoText.getText().trim();
               this.checkForTheSameSurgery(id, label, check, facture);
            }

         });
      }

   }

   public void bindChecks() {
      BooleanBinding patientId_FactureN = this.patientIdText.textProperty().isEmpty().or(this.factureNoText.textProperty().isEmpty());

      int i;
      for(i = 0; i < this.examChecks.size(); ++i) {
         ((CheckBox)this.examChecks.get(i)).disableProperty().bind(patientId_FactureN);
      }

      for(i = 0; i < this.surgeryChecks.size(); ++i) {
         ((CheckBox)this.surgeryChecks.get(i)).disableProperty().bind(patientId_FactureN);
      }

   }

   public void checkForTheSameExam(String id, String exam, CheckBox check, String facture) {
      Connection conn = Tools.getConnected();
      String sql = "SELECT date, nofacture FROM register WHERE (patientId='" + id + "' AND exams LIKE '%" + exam + "%') ORDER BY date ASC";
      boolean est = false;
      String noFacture = "";
      String message = "Cet examen a été enregistré pour le même patient le ";

      try {
         Statement stm = conn.createStatement(1004, 1007);
         ResultSet rst = stm.executeQuery(sql);
         if (rst.last()) {
            message = message + rst.getString("date");
            noFacture = rst.getString("nofacture");
            est = true;
            message = message + "\nVoulez-vous l'enregistrer encore?";
         }
      } catch (SQLException var12) {
         var12.printStackTrace();
      }

      if (est) {
         if (noFacture.equals(facture)) {
            message = "L'examen avec le meme nom et numero de facture existe deja";
            this.alertExistingExam(message, check, "error");
         } else {
            this.alertExistingExam(message, check, "confirmation");
         }
      }

   }

   public void checkForTheSameSurgery(String id, String surgery, CheckBox check, String facture) {
    //  String monthYear = (new SimpleDateFormat("yyyy-MM")).format(Calendar.getInstance().getTime());
      Connection conn = Tools.getConnected();
      String sql = "SELECT date, nofacture FROM register WHERE (patientId='" + id + "' AND surgery LIKE '%" + surgery + "%') ORDER BY date ASC";
      boolean est = false;
      String noFacture = "";
      String message = "Cet acte a été dernièrement enregistré pour le même patient le ";

      try {
         Statement stm = conn.createStatement(1004, 1007);
         ResultSet rst = stm.executeQuery(sql);
         if (rst.last()) {
            message = message + rst.getString("date");
            noFacture = rst.getString("nofacture");
            est = true;
            message = message + "\nVoulez-vous l'enregistrer encore?";
         }
      } catch (SQLException var13) {
         var13.printStackTrace();
      }

      if (est) {
         if (noFacture.equals(facture)) {
            message = "L'acte pour le meme patient avec le meme numero de facture existe deja";
            this.alertExistingExam(message, check, "error");
         } else {
            this.alertExistingExam(message, check, "confirmation");
         }
      }

   }

   public void alertExistingExam(String message, CheckBox check, String type) {
      Alert alert = new Alert(AlertType.NONE);
      switch(type.hashCode()) {
      case 96784904:
         if (type.equals("error")) {
            alert.setAlertType(AlertType.ERROR);
         }
         break;
      case 2099153973:
         if (type.equals("confirmation")) {
            alert.setAlertType(AlertType.CONFIRMATION);
         }
      }

      alert.setHeaderText("ALERT MESSAGE");
      alert.setContentText(message);
      alert.setTitle("CONFIRMER LE RE-ENREGISTREMENT");
      if (type.equals("confirmation")) {
         Optional<ButtonType> feedback = alert.showAndWait();
         if (((ButtonType)feedback.get()).equals(ButtonType.CANCEL) || ((ButtonType)feedback.get()).equals(ButtonType.CLOSE)) {
            check.setSelected(false);
         }
      } else {
         check.setSelected(false);
         alert.showAndWait();
      }

   }

   public void alertConsommableNotFilled(String message) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setHeaderText("ALERT MESSAGE");
      alert.setContentText(message);
      alert.setTitle("ERREUR DE REMPLISSAGE");
      alert.showAndWait();
   }

   public void createConsommableGridPane(String[] surgeryList) {
      GridPane consGrid = new GridPane();
      Button addButton = new Button("Save");
      Button cancelButton = new Button("Cancel");
      ArrayList<Label> consLabel = new ArrayList<Label>();
      ArrayList<TextField> consText = new ArrayList<TextField>();
      consGrid.setVgap(10.0D);
      consGrid.setPadding(new Insets(10.0D));

      for(int i = 0; i < surgeryList.length; ++i) {
         Label lab = new Label(surgeryList[i]);
         consLabel.add(lab);
         consText.add(new TextField());
      }

      ColumnConstraints col = new ColumnConstraints(190.0D);
      consGrid.getColumnConstraints().add(col);
      consGrid.getColumnConstraints().add(col);

      for(int i = 0; i < consLabel.size(); ++i) {
         consGrid.addRow(i, new Node[]{(Node)consLabel.get(i), (Node)consText.get(i)});
      }

      Object[] textObj = consText.toArray();
      Node[] textNodes = new Node[textObj.length];

      for(int i = 0; i < textObj.length; ++i) {
         textNodes[i] = (Node)textObj[i];
      }

      Tools.addNavigation(textNodes);
      consGrid.addRow(consLabel.size(), new Node[]{addButton, cancelButton});
      consGrid.setPrefWidth(400.0D);
      consGrid.setPrefHeight(-1.0D);
      Scene scene = new Scene(consGrid);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.setTitle("Specifiez le prix de consommable pour chaque operation");
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initOwner(this.mainApp.getStagePrincipale());
      addButton.setOnAction((e) -> {
         if (this.isAllConsommableFilled(consText)) {
            this.consommablePrice = this.getConsommablePrices(consText);
            stage.close();
         } else {
            this.alertConsommableNotFilled("Fill all the consommable prices fields");
            this.goOn = false;
         }

      });
      cancelButton.setOnAction((e) -> {
         stage.close();
      });
      stage.showAndWait();
   }

   public boolean isAllConsommableFilled(ArrayList<TextField> array) {
      boolean filled = true;

      for(int i = 0; i < array.size(); ++i) {
         if (((TextField)array.get(i)).getText().isBlank()) {
            filled = false;
         }
      }

      return filled;
   }

   public String getConsommablePrices(ArrayList<TextField> array) {
      String consPrices = "";

      for(int i = 0; i < array.size(); ++i) {
         consPrices = consPrices + ((TextField)array.get(i)).getText().trim();
         if (i != array.size() - 1) {
            consPrices = consPrices + "/";
         }
      }

      return consPrices;
   }
}
