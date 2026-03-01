package app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import app.model.Drug;
import app.model.Entry;
import app.tools.Tools;
import app.view.ActDeleterController;
import app.view.AppHomeController;
import app.view.DataDeleterController;
import app.view.DrugAdderController;
import app.view.DrugChoserController;
import app.view.DrugPrescriberController;
import app.view.EntryModifierController;
import app.view.ExamAdderController;
import app.view.ExamChoserController;
import app.view.ExamRemoverController;
import app.view.LoginScreenController;
import app.view.MessageEphemereController;
import app.view.PeriodeChoserController;
import app.view.PickTimeController;
import app.view.PrescriptionPeriodeChoserController;
import app.view.RegistrationScreenController;
import app.view.ServerClientController;
import app.view.UserAdderController;
import app.view.UserDeleterController;
import javafx.application.Application;
import javafx.application.Preloader.ProgressNotification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ExMain extends Application {
   private Scene scenePrincipale;
   private StackPane panePrincipale;
   private Stage stagePrincipale;
   private VBox loginPane;
   private Scene smsScene;
   private MessageEphemereController smsController;
   private Stage smsStage;
   private String pseudo;
   private AppHomeController appHomeController;
   private String creancesPath;
   private String periode;
   private boolean isIntervalSet = false;
   private Stage timeStage;
   private RegistrationScreenController registrationScreenController;
   private LoginScreenController loginController;
   private boolean isForPrinting = false;
   private ObservableList<Entry> entryList = FXCollections.observableArrayList();
   private ObservableList<Drug> drugList = FXCollections.observableArrayList();
   private String operativePeriode;
   private boolean isOperativePeriodeSet = false;
   private File chosenFile;
   private boolean isSelectedFileNull = false;

   public File getChosenFile() {
      return this.chosenFile;
   }

   public StackPane getPanePrincipale() {
      return this.panePrincipale;
   }

   public void setIsOperativePeriodeSet(boolean bool) {
      this.isOperativePeriodeSet = bool;
   }

   public void setIsSelectedFileNull(boolean b) {
      this.isSelectedFileNull = b;
   }

   public boolean getIsSelectedFileNull() {
      return this.isSelectedFileNull;
   }

   public boolean getIsOperativePeriodeSet() {
      return this.isOperativePeriodeSet;
   }

   public void setOperativePeriode(String str) {
      this.operativePeriode = str;
   }

   public String getOperativePeriode() {
      return this.operativePeriode;
   }

   public ObservableList<Entry> getEntryList() {
      return this.entryList;
   }

   public ObservableList<Drug> getDrugList() {
      return this.drugList;
   }

   public LoginScreenController getLoginController() {
      return this.loginController;
   }

   public void setIsForPrinting(boolean bool) {
      this.isForPrinting = bool;
   }

   public boolean getIsForPrinting() {
      return this.isForPrinting;
   }

   public RegistrationScreenController getRegistrationScreenController() {
      return this.registrationScreenController;
   }

   public VBox getLoginPane() {
      return this.loginPane;
   }

   public Stage getTimeStage() {
      return this.timeStage;
   }

   public void setPeriode(String per) {
      this.periode = per;
   }

   public void setIsIntervalSet(boolean b) {
      this.isIntervalSet = b;
   }

   public boolean getIsIntervalSet() {
      return this.isIntervalSet;
   }

   public String getPeriode() {
      return this.periode;
   }

   public String getCreancesPath() {
      return this.creancesPath;
   }

   public Scene getSmsScene() {
      return this.smsScene;
   }

   public Stage getSmsStage() {
      return this.smsStage;
   }

   public MessageEphemereController getSmsController() {
      return this.smsController;
   }

   public void setPseudo(String p) {
      this.pseudo = p;
   }

   public String getPseudo() {
      return this.pseudo;
   }

   public AppHomeController getAppHomeController() {
      return this.appHomeController;
   }

   public Stage getStagePrincipale() {
      return this.stagePrincipale;
   }

   public void init() {
      this.notifyPreloader(new ProgressNotification(0.0D));
      Tools.getServerProperties();
      this.prepareDocs();
      this.notifyPreloader(new ProgressNotification(0.5D));
      Tools.prepareTables();
      this.notifyPreloader(new ProgressNotification(0.8D));
      this.creerDossier();
   }

   public void smsFxml() throws IOException {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/MessageEphemere.fxml"));
      VBox sPane = (VBox)loader.load();
      this.smsScene = new Scene(sPane);
      this.smsScene.setFill(Color.TRANSPARENT);
      this.smsController = (MessageEphemereController)loader.getController();
   }

   public void setLoginPane() throws IOException {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/LoginScreen.fxml"));
      this.loginPane = (VBox)loader.load();
      this.loginController = (LoginScreenController)loader.getController();
      this.loginController.setMain(this);
   }

   public void prepareDocs() {
      String var10002 = System.getenv("SystemDrive");
      File rashotDir = new File(var10002 + File.separator + "Ph");
      if (!rashotDir.exists()) {
         rashotDir.mkdirs();
      }

      File f = new File(rashotDir.getAbsolutePath() + "/test.docx");

      try {
         f.createNewFile();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      InputStream prepare = ExMain.class.getResourceAsStream("sources/prepare.docx");
      Document doc = new Document(prepare, FileFormat.Docx_2013);
      doc.saveToFile(f.getAbsolutePath());
      if (f.exists()) {
         f.delete();
      }

      if (rashotDir.exists()) {
         rashotDir.delete();
      }

   }

   public void creerDossier() {
      String var10002 = System.getProperty("user.home");
      File dossier = new File(var10002 + File.separator + "Desktop" + File.separator + "CREANCES");
      if (!dossier.exists()) {
         dossier.mkdirs();
      }

      this.creancesPath = dossier.getAbsolutePath();
   }

   public void start(Stage primaryStage) {
      Tools.setMain(this);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/AppHome.fxml"));

      try {
         StackPane regHome = (StackPane)loader.load();
         this.panePrincipale = regHome;
         this.stagePrincipale = primaryStage;
         this.scenePrincipale = new Scene(this.panePrincipale);
         this.setLoginPane();
         this.appHomeController = (AppHomeController)loader.getController();
         primaryStage.getIcons().addAll(new Image[]{new Image(ExMain.class.getResourceAsStream("images/owexl.PNG"))});
         this.appHomeController.setMain(this);
         this.appHomeController.setCentralPane(this.loginPane);
         primaryStage.setScene(this.scenePrincipale);
         primaryStage.setTitle("Owen Exam Register");
         Screen screen = Screen.getPrimary();
         Rectangle2D bounds = screen.getVisualBounds();
         this.stagePrincipale.setWidth(bounds.getWidth());
         this.stagePrincipale.setHeight(bounds.getHeight());
         this.stagePrincipale.setX(bounds.getMinX());
         this.stagePrincipale.setY(bounds.getMinY());
         this.stagePrincipale.setMaximized(true);
         primaryStage.show();
         Node divider = this.appHomeController.getMainSplit().lookup(".split-pane-divider");
         if (divider != null) {
            divider.setStyle("-fx-background-color: transparent;");
         }

         this.smsFxml();
      } catch (IOException var7) {
         var7.printStackTrace();
      }

   }

   public static void main(String[] args) {
      System.setProperty("javafx.preloader", OwPreloader.class.getCanonicalName());
      launch(args);
   }

   public void showPickTime() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/PickTime.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         this.timeStage = new Stage();
         this.timeStage.setScene(timeScene);
         this.timeStage.initOwner(this.stagePrincipale);
         PickTimeController controller = (PickTimeController)loader.getController();
         controller.setMainApp(this);
         this.timeStage.setTitle("Owex-R - Chose periode");
         this.timeStage.showAndWait();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public void showRegistrationScreen() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/RegistrationScreen.fxml"));

      try {
         VBox box = (VBox)loader.load();
         this.getAppHomeController().setCentralPane(box);
         this.registrationScreenController = (RegistrationScreenController)loader.getController();
         this.registrationScreenController.setMain(this);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void showExamAdder() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/ExamAdder.fxml"));

      try {
         VBox box = (VBox)loader.load();
         Scene scene = new Scene(box);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.setTitle("OwEx - ADD EXAM OR SURGERY");
         stage.initModality(Modality.WINDOW_MODAL);
         stage.initOwner(this.stagePrincipale);
         ExamAdderController controller = (ExamAdderController)loader.getController();
         controller.setStage(stage);
         controller.setMain(this);
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showEntryModifier() {
      this.showPeriodeChoser();
      if (this.getIsOperativePeriodeSet()) {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(ExMain.class.getResource("view/EntryModifier.fxml"));

         try {
            VBox box = (VBox)loader.load();
            Scene scene = new Scene(box);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("OwEx - MODIFY EXAM OR SURGERY");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.stagePrincipale);
            EntryModifierController controller = (EntryModifierController)loader.getController();
            controller.setStage(stage);
            controller.setMain(this);
            stage.showAndWait();
         } catch (IOException var6) {
            var6.printStackTrace();
         }
      }

   }

   public void deleteAct() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/ActDeleter.fxml"));

      try {
         VBox box = (VBox)loader.load();
         Scene scene = new Scene(box);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.setTitle("OwEx - DELETE EXAM OR SURGERY");
         stage.initModality(Modality.WINDOW_MODAL);
         stage.initOwner(this.stagePrincipale);
         ActDeleterController controller = (ActDeleterController)loader.getController();
         controller.setStage(stage);
         controller.setMain(this);
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void deleteData() {
      this.populateEntryList();
      if (this.getIsOperativePeriodeSet()) {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(ExMain.class.getResource("view/DataDeleter.fxml"));

         try {
            VBox box = (VBox)loader.load();
            Scene scene = new Scene(box);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("OwEx - DELETE DATA");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.stagePrincipale);
            DataDeleterController controller = (DataDeleterController)loader.getController();
            controller.setStage(stage);
            controller.setMainApp(this);
            stage.showAndWait();
         } catch (IOException var6) {
            var6.printStackTrace();
         }
      }

   }

   public void deleteData(boolean b) {
      if (this.getIsOperativePeriodeSet()) {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(ExMain.class.getResource("view/DataDeleter.fxml"));

         try {
            VBox box = (VBox)loader.load();
            Scene scene = new Scene(box);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("OwEx - DELETE DATA");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(this.stagePrincipale);
            DataDeleterController controller = (DataDeleterController)loader.getController();
            controller.setStage(stage);
            controller.setMainApp(this);
            stage.showAndWait();
         } catch (IOException var7) {
            var7.printStackTrace();
         }
      }

   }

   public void populateEntryList() {
      this.entryList.clear();
      this.showPeriodeChoser();
      if (this.getIsOperativePeriodeSet()) {
         Connection conn = Tools.getConnected();
         String sql = "SELECT * FROM register WHERE monthYear='" + this.getOperativePeriode() + "'";

         try {
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(sql);

            while(rst.next()) {
               this.entryList.add(new Entry(rst.getInt("id"), rst.getString("patientId"), rst.getString("exams"), rst.getString("surgery"), rst.getString("date"), false));
            }
         } catch (SQLException var5) {
            var5.printStackTrace();
         }
      }

   }

   public void showPeriodeChoser() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/PeriodeChoser.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         PeriodeChoserController controller = (PeriodeChoserController)loader.getController();
         controller.setMain(this);
         controller.setStage(stage);
         stage.setTitle("Owex-R - Chose periode");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void chooseFile() {
      FileChooser filechooser = new FileChooser();
      filechooser.getExtensionFilters().add(new ExtensionFilter("Text Files", new String[]{"*.txt"}));
      this.chosenFile = filechooser.showOpenDialog(this.stagePrincipale);
   }

   public void showServerClientAdder() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/ServerClient.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         ServerClientController controller = (ServerClientController)loader.getController();
         controller.setStage(stage);
         stage.setTitle("Owex-R - Create new client");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showExamChoser() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/ExamChoser.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         ExamChoserController controller = (ExamChoserController)loader.getController();
         controller.setMain(this);
         controller.setStage(stage);
         stage.setTitle("Owex-R - Chose exams");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showExamRemover() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/ExamRemover.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         ExamRemoverController controller = (ExamRemoverController)loader.getController();
         controller.setMain(this);
         controller.setStage(stage);
         stage.setTitle("Owex-R - Chose exams");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showUserAdder() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/UserAdder.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         UserAdderController controller = (UserAdderController)loader.getController();
         controller.setStage(stage);
         stage.setTitle("Owex-R - Add user");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showUserDeleter() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/UserDeleter.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         UserDeleterController controller = (UserDeleterController)loader.getController();
         controller.setStage(stage);
         controller.setMain(this);
         stage.setTitle("Owex-R - Delete user");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showDrugAdder() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/DrugAdder.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         DrugAdderController controller = (DrugAdderController)loader.getController();
         controller.setStage(stage);
         stage.setTitle("Owex-R - Add drug to prescribe");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void populateDrugList() {
      this.drugList.clear();
      Connection conn = Tools.getConnected();
      String sql = "SELECT drug from drugs";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            this.drugList.add(new Drug(rst.getString("drug"), false));
         }
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

   }

   public void showDrugPrescriber() {
      this.populateDrugList();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/DrugPrescriber.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         DrugPrescriberController controller = (DrugPrescriberController)loader.getController();
         controller.setStage(stage);
         controller.setMain(this);
         stage.setTitle("Owex-R - Prescribe drugs");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showDrugChoser() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/DrugChoser.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         DrugChoserController controller = (DrugChoserController)loader.getController();
         controller.setStage(stage);
         stage.setTitle("Owex-R - Chose drugs to be deleted");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public void showPrescriptionPeriodeChoser() {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(ExMain.class.getResource("view/PrescriptionPeriodeChoser.fxml"));

      try {
         VBox tPane = (VBox)loader.load();
         Scene timeScene = new Scene(tPane);
         Stage stage = new Stage();
         stage.setScene(timeScene);
         stage.initOwner(this.stagePrincipale);
         PrescriptionPeriodeChoserController controller = (PrescriptionPeriodeChoserController)loader.getController();
         controller.setMain(this);
         controller.setStage(stage);
         stage.setTitle("Owex-R - Chose periode");
         stage.showAndWait();
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }
   
   public void showHelp() {
	      FXMLLoader loader = new FXMLLoader();
	      loader.setLocation(ExMain.class.getResource("view/Help.fxml"));

	      try {
	         VBox hPane = (VBox)loader.load();
	         Scene hScene = new Scene(hPane);
	         Stage stage = new Stage();
	         stage.setScene(hScene);
	         stage.initOwner(this.stagePrincipale);
	         stage.setTitle("Owex-R - Help");
	         stage.showAndWait();
	      } catch (IOException var6) {
	         var6.printStackTrace();
	      }

	   }
   
   
}
