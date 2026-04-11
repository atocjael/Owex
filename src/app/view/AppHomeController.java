/* Decompiler 1196ms, total 1940ms, lines 2136 */
package app.view;

import java.awt.Desktop;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.spire.doc.AutoFitBehaviorType;
import com.spire.doc.CellWidthType;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.Table;
import com.spire.doc.TableRow;
import com.spire.doc.documents.BreakType;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.PageOrientation;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TableRowHeightType;
import com.spire.doc.documents.UnderlineStyle;
import com.spire.doc.documents.VerticalAlignment;
import com.spire.doc.fields.TextRange;
import com.spire.xls.CellRange;
import com.spire.xls.ExcelVersion;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import app.ExMain;
import app.model.Entry;
import app.tools.Tools;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class AppHomeController {
   @FXML
   private BorderPane borderpane;
   @FXML
   private AnchorPane thirdPane;
   @FXML
   private ComboBox<String> periodeCombo;
   @FXML
   private VBox vbox;
   private ExMain main;
   private Label price = new Label("0");
   private Label surPrice = new Label("0");
   private Label usurPrice = new Label("0");
   @FXML
   private GridPane sumGrid;
   @FXML
   private SplitPane mainSplit;
   @FXML
   private Label todaysReg;
   @FXML
   private Label creanceLabel;
   @FXML
   private Accordion todaysAccord;
   @FXML
   private Button declarerExam;
   @FXML
   private Button declarerSurgery;
   @FXML
   private Label login;
   @FXML
   private Label recap;
   @FXML
   private Button update;
   @FXML
   private Menu acts;
   @FXML
   private Menu rapports;
   @FXML
   private Menu data;
   @FXML
   private Menu users;
   @FXML
   private Menu drugs;
   @FXML
   private Menu protocols, help;
   @FXML
   private TreeView<String> fileTree;
   private VBox indicatorBox = new VBox();
   private ArrayList<Label> numberLabels = new ArrayList<Label>();
   private ArrayList<Label> examSurgeryLabels = new ArrayList<Label>();
   private ArrayList<String> examsDone = new ArrayList<String>();
   private ArrayList<String> surgeriesDone = new ArrayList<String>();
   private ArrayList<String> surgeriesDoneBySurgeon = new ArrayList<String>();
   private ArrayList<String> periodeSurgeriesDone = new ArrayList<String>();
   private ArrayList<String> tousLesSurgeries = new ArrayList<String>();
   private ArrayList<String> tousLesExamens = new ArrayList<String>();
   private int examCount = 0;
   private int surgeryBySurgeon=0;
   
   private int income;
   private int surgeryIncome;
   private int surgeryIncomeBySurgeon;
   private int examTotalDeclare;
   private int surgeryActeDeclare;
   private int totalRendu;
   private String surgeryPath;
   private SimpleBooleanProperty disAllowDeclarer = new SimpleBooleanProperty();
   private SimpleBooleanProperty isNotAtomos = new SimpleBooleanProperty();
   private SimpleBooleanProperty noUpdatable = new SimpleBooleanProperty();
   private Image folder = new Image(ExMain.class.getResourceAsStream("images/closedFolder.png"));
   private Image fileGraphic = new Image(ExMain.class.getResourceAsStream("images/file.png"));
   private String[] unite = new String[]{"", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf"};
   private String[] dizaine = new String[]{"dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"};
   private String[] multipleDeDix = new String[]{"", "", "vingt", "trente", "quarante", "cinquante", "soixante", "septante", "quatre-vingt", "nonante"};
   private String[] uniteNombre = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
   private String[] dizaineNombre = new String[]{"10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
   private String[] multipleNombre = new String[]{"", "", "20", "30", "40", "50", "60", "70", "80", "90"};
   private TreeItem<String> racine;
   private ArrayList<String> nonNullExam = new ArrayList<String>();
   private ArrayList<String> nonNullSurgery = new ArrayList<String>();


   public SplitPane getMainSplit() {
      return this.mainSplit;
   }

   public void setMain(ExMain m) {
      this.main = m;
   }

   public Label getLogin() {
      return this.login;
   }

   public ArrayList<String> getNonNullExam() {
      return this.nonNullExam;
   }

   public ArrayList<String> getNonNullSurgery() {
      return this.nonNullSurgery;
   }

   public void fillNumExSurLabels() {
      this.numberLabels.clear();
      this.examSurgeryLabels.clear();
      this.nonNullExam.clear();
      this.nonNullSurgery.clear();
      ArrayList<String> allExam = new ArrayList<String>();
      ArrayList<String> allSurgery = new ArrayList<String>();
      Connection conn = Tools.getConnected();
      String exSql = "SELECT exam FROM exams";
      String surSql = "SELECT surgery FROM surgeries";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(exSql);

         while(rst.next()) {
            allExam.add(rst.getString("exam"));
         }

         this.tousLesExamens = allExam;
         rst.close();

         int i;
         String ex;
         for(i = 0; i < allExam.size(); ++i) {
            ex = this.countNonNullExams(conn, ((String)allExam.get(i)).trim(), (new SimpleDateFormat("yyyy-MM")).format(Calendar.getInstance().getTime()), "exams");
            if (!ex.isBlank()) {
               this.nonNullExam.add(ex);
               this.examSurgeryLabels.add(new Label(ex));
               this.numberLabels.add(new Label("0"));
            }
         }

         //System.out.println(this.examSurgeryLabels);
         
         this.examSurgeryLabels.add(new Label(""));
         this.numberLabels.add(new Label(""));
         this.examSurgeryLabels.add(new Label(""));
         this.numberLabels.add(new Label(""));
         rst = stm.executeQuery(surSql);

         while(rst.next()) {
            allSurgery.add(rst.getString("surgery"));
         }

         this.tousLesSurgeries = allSurgery;
         rst.close();

         for(i = 0; i < allSurgery.size(); ++i) {
            ex = this.countNonNullExams(conn, ((String)allSurgery.get(i)).trim(), (new SimpleDateFormat("yyyy-MM")).format(Calendar.getInstance().getTime()), "surgery");
            if (!ex.isBlank()) {
               this.nonNullSurgery.add(ex);
               this.examSurgeryLabels.add(new Label(ex));
               this.numberLabels.add(new Label("0"));
            }
         }
      } catch (SQLException var10) {
         var10.printStackTrace();
      }

   }

   public void fillNumExSurLabels(String periode) {
      this.numberLabels.clear();
      this.examSurgeryLabels.clear();
      this.nonNullExam.clear();
      this.nonNullSurgery.clear();
      ArrayList<String> allExam = new ArrayList<String>();
      ArrayList<String> allSurgery = new ArrayList<String>();
      Connection conn = Tools.getConnected();
      String exSql = "SELECT exam FROM exams";
      String surSql = "SELECT surgery FROM surgeries";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(exSql);

         while(rst.next()) {
            allExam.add(rst.getString("exam"));
         }

         rst.close();

         int i;
         String ex;
         for(i = 0; i < allExam.size(); ++i) {
            ex = this.countNonNullExams(conn, ((String)allExam.get(i)).trim(), periode, "exams");
            if (!ex.isBlank()) {
               this.nonNullExam.add(ex);
               this.examSurgeryLabels.add(new Label(ex));
               this.numberLabels.add(new Label("0"));
            }
         }

         this.examSurgeryLabels.add(new Label(""));
         this.numberLabels.add(new Label(""));
         this.examSurgeryLabels.add(new Label(""));
         this.numberLabels.add(new Label(""));
         rst = stm.executeQuery(surSql);

         while(rst.next()) {
            allSurgery.add(rst.getString("surgery"));
         }

         rst.close();

         for(i = 0; i < allSurgery.size(); ++i) {
            ex = this.countNonNullExams(conn, ((String)allSurgery.get(i)).trim(), periode, "surgery");
            if (!ex.isBlank()) {
               this.nonNullSurgery.add(ex);
               this.examSurgeryLabels.add(new Label(ex));
               this.numberLabels.add(new Label("0"));
            }
         }
      } catch (SQLException var11) {
         var11.printStackTrace();
      }

   }

   public String countNonNullExams(Connection conn, String examination, String date, String type) {
      String ex = "";
      if (!examination.isBlank()) {
         String sql = "SELECT COUNT(" + type + ") AS totalEx FROM register WHERE (" + type + " LIKE '%" + examination + "%' AND monthYear='" + date + "')";

         try {
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(sql);
            if (rst.next()) {
               int nbreTotal = rst.getInt("totalEx");
               if (nbreTotal > 0) {
                  ex = examination;
               }
            }
         } catch (SQLException var10) {
            var10.printStackTrace();
         }
      }

      return ex;
   }

   public ArrayList<String> getNonNullSurgery(String periode) {
      Connection conn = Tools.getConnected();
      ArrayList<String> ts = new ArrayList<String>();

      for(int i = 0; i < this.tousLesSurgeries.size(); ++i) {
         String ex = this.countNonNullExams(conn, ((String)this.tousLesSurgeries.get(i)).trim(), periode, "surgery");
         if (!ex.isBlank()) {
            ts.add(ex);
         }
      }

      return ts;
   }

   public ArrayList<String> getNonNullExams(String periode) {
      Connection conn = Tools.getConnected();
      ArrayList<String> ts = new ArrayList<String>();

      for(int i = 0; i < this.tousLesExamens.size(); ++i) {
         String ex = this.countNonNullExams(conn, ((String)this.tousLesExamens.get(i)).trim(), periode, "exams");
         if (!ex.isBlank()) {
            ts.add(ex);
         }
      }

      return ts;
   }

   public void setUpSumGrid() {
      this.setThirdPaneVisible();
      this.main.getRegistrationScreenController().clearGridPane(this.sumGrid);
      this.fillNumExSurLabels();
      int length = this.examSurgeryLabels.size() + 3;
      double restr = (double)(400 / length);
      RowConstraints rc = new RowConstraints(restr);

      for(int i = 0; i < this.examSurgeryLabels.size(); ++i) {
         this.sumGrid.getRowConstraints().add(rc);
         this.sumGrid.addRow(i, new Node[]{(Node)this.examSurgeryLabels.get(i), (Node)this.numberLabels.get(i)});
      }

      this.sumGrid.getRowConstraints().add(rc);
      this.sumGrid.addRow(this.examSurgeryLabels.size(), new Node[]{new Label(), new Label()});
      this.sumGrid.getRowConstraints().add(rc);
      this.sumGrid.addRow(this.examSurgeryLabels.size() + 1, new Node[]{new Label("Exams income"), this.price});
      if (this.main.getPseudo().equals("atomos") || this.main.getPseudo().equals("rich")) {
          this.sumGrid.getRowConstraints().add(rc);
          this.sumGrid.addRow(this.examSurgeryLabels.size() + 2, new Node[]{new Label("Surgery income"), this.surPrice});
          this.sumGrid.getRowConstraints().add(rc);
          this.sumGrid.addRow(this.examSurgeryLabels.size() + 3, new Node[]{new Label("Surgery income by you"), this.usurPrice});
       }

   }

   public void setUpSumGrid(String periode) {
      this.setThirdPaneVisible();
      this.main.getRegistrationScreenController().clearGridPane(this.sumGrid);
      this.fillNumExSurLabels(periode);
      int length = this.examSurgeryLabels.size() + 3;
      double restr = (double)(500 / length);
      RowConstraints rc = new RowConstraints(restr);

      for(int i = 0; i < this.examSurgeryLabels.size(); ++i) {
         this.sumGrid.getRowConstraints().add(rc);
         this.sumGrid.addRow(i, new Node[]{(Node)this.examSurgeryLabels.get(i), (Node)this.numberLabels.get(i)});
      }

      this.sumGrid.getRowConstraints().add(rc);
      this.sumGrid.addRow(this.examSurgeryLabels.size(), new Node[]{new Label(), new Label()});
      this.sumGrid.getRowConstraints().add(rc);
      this.sumGrid.addRow(this.examSurgeryLabels.size() + 1, new Node[]{new Label("Exams income"), this.price});
      if (this.main.getPseudo().equals("atomos") || this.main.getPseudo().equals("rich")) {
         this.sumGrid.getRowConstraints().add(rc);
         this.sumGrid.addRow(this.examSurgeryLabels.size() + 2, new Node[]{new Label("Surgery income"), this.surPrice});
         this.sumGrid.getRowConstraints().add(rc);
         this.sumGrid.addRow(this.examSurgeryLabels.size() + 3, new Node[]{new Label("Surgery income by you"), this.usurPrice});
      }

   }

   public void initialize() {
      this.setButtonBindings();
      ColumnConstraints colc = new ColumnConstraints();
      ColumnConstraints colc2 = new ColumnConstraints();
      colc.setHgrow(Priority.ALWAYS);
      colc2.setHgrow(Priority.ALWAYS);
      colc.setPercentWidth(70.0D);
      colc2.setPercentWidth(30.0D);
      this.sumGrid.getColumnConstraints().add(0, colc);
      this.sumGrid.getColumnConstraints().add(1, colc2);
      this.income = 0;
      this.surgeryIncome = 0;
      this.surgeryBySurgeon=0;
      this.setThirdPaneInvisible();
      this.periodeCombo.getItems().add("");
      this.periodeCombo.getItems().addAll(this.getDistinctPeriodes());
      this.handleComboChanges();
      this.fileTree.setVisible(false);
      this.fileTree.setOnMouseClicked((event) -> {
         if (event.getClickCount() == 2) {
            TreeItem<String> item = (TreeItem<String>)this.fileTree.getSelectionModel().getSelectedItem();
            if (item != null && item.isLeaf()) {
               String filePath = "";
               TreeItem<String> parent1 = item.getParent();
               TreeItem<String> parent2 = parent1.getParent();
               String var10000 = this.main.getCreancesPath();
               filePath = var10000 + "/" + (String)parent2.getValue() + "/" + (String)parent1.getValue() + "/" + (String)item.getValue();
               File file = new File(filePath);
               if (file.exists()) {
                  String absPath = file.getAbsolutePath();
                  if (Desktop.isDesktopSupported()) {
                     try {
                        Desktop.getDesktop().open(new File(absPath));
                     } catch (IOException var9) {
                        var9.printStackTrace();
                     }
                  }
               }
            }
         }

      });
   }

   public BorderPane getBorderPane() {
      return this.borderpane;
   }

   public void setCentralPane(Node n) {
      this.borderpane.setCenter(n);
   }

   public void sumUp() {
      this.income = 0;
      this.surgeryIncome = 0;
      this.surgeryIncomeBySurgeon=0;
      String per = (new SimpleDateFormat("yyyy-MM")).format(Calendar.getInstance().getTime());
      this.setUpSumGrid();
      String currentMonthYear = (new SimpleDateFormat("MM/yyyy")).format(Calendar.getInstance().getTime());
      this.recap.setText("Sum up for " + currentMonthYear);
      Connection conn = Tools.getConnected();

      int j;
      for(j = 0; j < this.getNonNullExam().size(); ++j) {
         this.income += this.countExams(conn, (String)this.getNonNullExam().get(j), (Label)this.numberLabels.get(j));
      }

      for(j = 0; j < this.getNonNullSurgery().size(); ++j) {
         List<Label> surLabel = this.numberLabels.subList(this.getNonNullExam().size() + 2, this.numberLabels.size());
         this.surgeryIncome += this.countSurgeries(conn, (String)this.getNonNullSurgery().get(j), (Label)surLabel.get(j));
         this.surgeryIncomeBySurgeon+=this.countSurgeriesBySurgeon(conn, (String)this.getNonNullSurgery().get(j),per, this.main.getPseudo());
      }

      this.price.setText(String.valueOf(this.income));
      this.surPrice.setText(String.valueOf(this.surgeryIncome));
      this.usurPrice.setText(String.valueOf(this.surgeryIncomeBySurgeon));
      
      this.giveMonthsCreances(per);
      this.giveTodayStatics();
      this.periodeCombo.getSelectionModel().select(0);
   }

   public void sumUpChosenPeriode(String periode) {
      this.income = 0;
      this.surgeryIncome = 0;
      this.surgeryIncomeBySurgeon=0;
      
      this.setUpSumGrid(periode);
      String[] pSplit = periode.split("-");
      String pString = pSplit[1] + "/" + pSplit[0];
      this.recap.setText("Sum up for " + pString);
      Connection conn = Tools.getConnected();

      int j;
      for(j = 0; j < this.getNonNullExam().size(); ++j) {
         this.income += this.countPeriodeExams(conn, (String)this.getNonNullExam().get(j), (Label)this.numberLabels.get(j), periode);
      }

      for(j = 0; j < this.getNonNullSurgery().size(); ++j) {
         List<Label> surLabel = this.numberLabels.subList(this.getNonNullExam().size() + 2, this.numberLabels.size());
         this.surgeryIncome += this.countPeriodeSurgeries(conn, (String)this.getNonNullSurgery().get(j), (Label)surLabel.get(j), periode);
         this.surgeryIncomeBySurgeon+=this.countSurgeriesBySurgeon(conn, (String)this.getNonNullSurgery().get(j),periode, this.main.getPseudo());
      }

      this.price.setText(String.valueOf(this.income));
      this.surPrice.setText(String.valueOf(this.surgeryIncome));
      this.usurPrice.setText(String.valueOf(this.surgeryIncomeBySurgeon));
      this.giveMonthsCreances(periode);
   }

   public Integer[] sumUp(String date) {
      int exIncome = 0;
      int surIncome = 0;
      this.surgeryBySurgeon=0;
      Connection conn = Tools.getConnected();
      ArrayList<String> surgeries = this.getNonNullSurgery(date);
      ArrayList<String> exams = this.getNonNullExams(date);

      int j;
      for(j = 0; j < exams.size(); ++j) {
         exIncome += this.countExams(conn, ((String)exams.get(j)).trim(), date);
      }

      for(j = 0; j < surgeries.size(); ++j) {
         surIncome += this.countSurgeriesBySurgeon(conn, ((String)surgeries.get(j)).trim(), date, this.main.getPseudo());
      }

      Integer[] toReturn = new Integer[]{exIncome, surIncome};
      return toReturn;
   }
   
   public Integer[] sumUp(String date, String pseudo) {
	      int exIncome = 0;
	      int surIncome = 0;
	      this.surgeryBySurgeon=0;
	      Connection conn = Tools.getConnected();
	      ArrayList<String> surgeries = this.getNonNullSurgery(date);
	      ArrayList<String> exams = this.getNonNullExams(date);

	      int j;
	      for(j = 0; j < exams.size(); ++j) {
	         exIncome += this.countExams(conn, ((String)exams.get(j)).trim(), date);
	      }

	      for(j = 0; j < surgeries.size(); ++j) {
	         surIncome += this.countSurgeriesBySurgeon(conn, ((String)surgeries.get(j)).trim(), date, pseudo);
	      }

	      Integer[] toReturn = new Integer[]{exIncome, surIncome};
	      return toReturn;
	   }
   
   

   public void setDisAllowDeclarerFalse() {
      this.disAllowDeclarer.set(false);
   }

   public void setisNotAtomosFalse() {
      this.isNotAtomos.set(false);
   }

   public void setNoUpdatableFalse() {
      this.noUpdatable.set(false);
   }

   public int countExams(Connection conn, String examination, String date) {
      String sql = "SELECT COUNT(exams) AS totalEx FROM register WHERE (exams LIKE '%" + examination + "%' AND monthYear='" + date + "')";
      int totalPrice = 0;
      int nbreTotal = 0;

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);
         if (rst.next()) {
            nbreTotal = rst.getInt("totalEx");
            this.examCount += nbreTotal;
            if (nbreTotal > 0) {
               this.examsDone.add(examination);
            }
         }

         rst = stm.executeQuery("SELECT price FROM exams WHERE exam='" + examination + "'");
         rst.next();
         totalPrice = rst.getInt("price") * nbreTotal;
         stm.close();
      } catch (SQLException var9) {
         var9.printStackTrace();
      }

      return totalPrice;
   }

   public int countExams(Connection conn, String examination, Label lab) {
      String currentMonthYear = (new SimpleDateFormat("yyyy-MM")).format(Calendar.getInstance().getTime());
      String sql = "SELECT COUNT(exams) AS totalEx FROM register WHERE exams LIKE '%" + examination + "%' AND monthYear='" + currentMonthYear + "'";
      int totalPrice = 0;
      int nbreTotal = 0;

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);
         if (rst.next()) {
            lab.setText(String.valueOf(rst.getInt("totalEx")));
            nbreTotal = rst.getInt("totalEx");
         } else {
            lab.setText("0");
         }

         rst = stm.executeQuery("SELECT price FROM exams WHERE exam='" + examination + "'");
         rst.next();
         totalPrice = rst.getInt("price") * nbreTotal;
         stm.close();
      } catch (SQLException var10) {
         var10.printStackTrace();
      }

      return totalPrice;
   }

   public int countPeriodeExams(Connection conn, String examination, Label lab, String periode) {
      String sql = "SELECT COUNT(exams) AS totalEx FROM register WHERE exams LIKE '%" + examination + "%' AND monthYear='" + periode + "'";
      int totalPrice = 0;
      int nbreTotal = 0;

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);
         if (rst.next()) {
            lab.setText(String.valueOf(rst.getInt("totalEx")));
            nbreTotal = rst.getInt("totalEx");
         } else {
            lab.setText("0");
         }

         rst = stm.executeQuery("SELECT price FROM exams WHERE exam='" + examination + "'");
         rst.next();
         totalPrice = rst.getInt("price") * nbreTotal;
         stm.close();
      } catch (SQLException var10) {
         var10.printStackTrace();
      }

      return totalPrice;
   }

   public int countSurgeries(Connection conn, String examination, Label lab) {
      String currentMonthYear = (new SimpleDateFormat("yyyy-MM")).format(Calendar.getInstance().getTime());
      String sql = "SELECT COUNT(surgery) AS totalEx FROM register WHERE surgery LIKE '%" + examination + "%' AND monthYear='" + currentMonthYear + "'";
      int totalPrice = 0;
      int nbreTotal = 0;

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);
         if (rst.next()) {
            lab.setText(String.valueOf(rst.getInt("totalEx")));
            nbreTotal = rst.getInt("totalEx");
            if (nbreTotal > 0) {
               this.periodeSurgeriesDone.add(examination);
            }
         } else {
            lab.setText("0");
         }

         rst = stm.executeQuery("SELECT price FROM surgeries WHERE surgery='" + examination + "'");
         rst.next();
         totalPrice = rst.getInt("price") * nbreTotal;
         stm.close();
      } catch (SQLException var10) {
         var10.printStackTrace();
      }

      return totalPrice;
   }

   public int countPeriodeSurgeries(Connection conn, String examination, Label lab, String periode) {
      String sql = "SELECT COUNT(surgery) AS totalEx FROM register WHERE surgery LIKE '%" + examination + "%' AND monthYear='" + periode + "'";
      int totalPrice = 0;
      int nbreTotal = 0;

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);
         if (rst.next()) {
            lab.setText(String.valueOf(rst.getInt("totalEx")));
            nbreTotal = rst.getInt("totalEx");
            if (nbreTotal > 0) {
               this.periodeSurgeriesDone.add(examination);
            }
         } else {
            lab.setText("0");
         }

         rst = stm.executeQuery("SELECT price FROM surgeries WHERE surgery='" + examination + "'");
         rst.next();
         totalPrice = rst.getInt("price") * nbreTotal;
         stm.close();
      } catch (SQLException var10) {
         var10.printStackTrace();
      }

      return totalPrice;
   }

   public int countSurgeries(Connection conn, String examination, String date) {
      String sql = "SELECT COUNT(surgery) AS totalEx FROM register WHERE (surgery LIKE '%" + examination + "%' AND monthYear='" + date + "')";
      int totalPrice = 0;
      int nbreTotal = 0;

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);
         if (rst.next()) {
            nbreTotal = rst.getInt("totalEx");
            if (nbreTotal > 0) {
               this.surgeriesDone.add(examination);
            }
         }

         rst = stm.executeQuery("SELECT price FROM surgeries WHERE surgery='" + examination + "'");
         rst.next();
         totalPrice = rst.getInt("price") * nbreTotal;
         stm.close();
      } catch (SQLException var9) {
         var9.printStackTrace();
      }

      return totalPrice;
   }
   
   public int countSurgeriesBySurgeon(Connection conn, String examination, String date, String surgeon) {
	   String sql = "SELECT COUNT(surgery) AS totalEx FROM register WHERE (surgery LIKE '%" + examination + "%' AND monthYear='" + date + "' AND doctor='"+surgeon+"')";
	      int totalPrice = 0;
	      int nbreTotal = 0;

	      try {
	         Statement stm = conn.createStatement();
	         ResultSet rst = stm.executeQuery(sql);
	         if (rst.next()) {
	            nbreTotal = rst.getInt("totalEx");
	            this.surgeryBySurgeon += nbreTotal;
	            if (nbreTotal > 0) {
	               this.surgeriesDoneBySurgeon.add(examination);
	            }
	         }

	         rst = stm.executeQuery("SELECT price FROM surgeries WHERE surgery='" + examination + "'");
	         rst.next();
	         totalPrice = rst.getInt("price") * nbreTotal;
	         stm.close();
	      } catch (SQLException var9) {
	         var9.printStackTrace();
	      }

	      return totalPrice;
   }

   public void showIndicator() {
      ProgressIndicator pi = new ProgressIndicator();
      this.indicatorBox.getChildren().add(pi);
      this.indicatorBox.setAlignment(Pos.CENTER);
      pi.setMinSize(100.0D, 100.0D);
      this.mainSplit.setDisable(true);
      this.main.getPanePrincipale().getChildren().add(this.indicatorBox);
   }

   public void removeIndicator(String message) {
      this.indicatorBox.getChildren().clear();
      this.main.getPanePrincipale().getChildren().remove(this.indicatorBox);
      this.mainSplit.setDisable(false);
      Tools.showEphemereText(message);
      this.main.setIsIntervalSet(false);
      this.main.setIsOperativePeriodeSet(false);
      this.populateIolDirectory();
   }

   @FXML
   public void declarerExam() {
      this.clearAfterDeclaring();
      this.main.showPeriodeChoser();
      if (this.main.getIsOperativePeriodeSet()) {
         Thread thread = new Thread() {
            public void run() {
               AppHomeController.this.writeDeclarations();
               AppHomeController.this.createSecondExamsProofs();
               Platform.runLater(() -> {
                  AppHomeController.this.removeIndicator("Déclaration de créance créée avec succès");
               });
            }
         };
         this.showIndicator();
         thread.start();
      }

   }

   public void writeDeclarations() {
      String[] declarants = new String[]{"atomos", "rich", "renilde"};

      for(int i = 0; i < declarants.length; ++i) {
         String[] toReplace = new String[]{"@names@", "@telephone@", "@email@", "@date@", "@somme@", "@periode@", "@title@", "@expourcentage@"};
         String[] replaceWith = new String[toReplace.length];
         replaceWith[3] = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
         Integer[] sommes = this.sumUp(this.main.getOperativePeriode());
         double somme = 0.0D;
         this.examTotalDeclare = sommes[0];
         replaceWith[5] = this.getPeriode().toLowerCase();
         Connection conn = Tools.getConnected();

         int j;
         try {
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery("SELECT nom, prenom, telephone, email, title, expourcentage FROM loginTable WHERE pseudo='" + declarants[i] + "'");
            rst.next();
            String var10000 = rst.getString("nom");
            String names = var10000 + " " + rst.getString("prenom");
            replaceWith[0] = names;
            replaceWith[1] = rst.getString("telephone");
            replaceWith[2] = rst.getString("email");
            replaceWith[6] = rst.getString("title");
            replaceWith[7] = rst.getString("expourcentage");
            j = Integer.valueOf(rst.getString("expourcentage"));
            somme = (double)(sommes[0] * j / 100);
            String var10002 = this.numberToLetter((int)somme);
            replaceWith[4] = var10002 + " Francs Bu ( " + String.valueOf((int)somme) + " FBi)";
         } catch (SQLException var15) {
            var15.printStackTrace();
         }

         String filename = Tools.creerDocument(declarants[i], "EXAMS");
         Document creance = new Document();
         InputStream creanceEx = ExMain.class.getResourceAsStream("sources/creance_ex.docx");
         creance.loadFromStream(creanceEx, FileFormat.Docx_2013);

         for(j = 0; j < toReplace.length; ++j) {
            creance.replace(toReplace[j], replaceWith[j], true, true);
         }

         creance.saveToFile(filename);
         this.clearAfterDeclaring();
      }

   }

   @FXML
   public void declarerSurgery() {
      this.main.showPeriodeChoser();
      if (this.main.getIsOperativePeriodeSet()) {
         Thread thread = new Thread() {
            public void run() {
               writeSurgeryDeclarations();
               Platform.runLater(() -> {
                  AppHomeController.this.removeIndicator("Déclaration de créance créée avec succès");
               });
            }
         };
         this.showIndicator();
         thread.start();
      }

   }
   
   public void writeSurgeryDeclarations() {
	   String[] declarants = new String[]{"atomos", "rich"};
	   
	   for(int i=0; i<declarants.length; i++) {
		   String[] toReplace = new String[]{"@names@", "@telephone@", "@email@", "@date@", "@periode@"};
           String[] replaceWith = new String[toReplace.length];
           replaceWith[3] = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
           Integer[] sommes = AppHomeController.this.sumUp(AppHomeController.this.main.getOperativePeriode(), declarants[i]);
           AppHomeController.this.surgeryActeDeclare = sommes[1];
           replaceWith[4] = AppHomeController.this.getPeriode();
           Connection conn = Tools.getConnected();

           try {
              Statement stm = conn.createStatement();
              ResultSet rst = stm.executeQuery("SELECT nom, prenom, telephone, email FROM loginTable WHERE pseudo='" + declarants[i] + "'");
              rst.next();
              String var10000 = rst.getString("nom");
              String names = var10000 + " " + rst.getString("prenom");
              replaceWith[0] = names;
              replaceWith[1] = rst.getString("telephone");
              replaceWith[2] = rst.getString("email");
           } catch (SQLException var9) {
              var9.printStackTrace();
           }

           String filename = Tools.creerDocument(declarants[i], "SURGERIES");
           Document creance = new Document();
           InputStream creanceEx = ExMain.class.getResourceAsStream("sources/creances_sur.docx");
           creance.loadFromStream(creanceEx, FileFormat.Docx_2013);

           for(int j = 0; j < toReplace.length; ++j) {
              creance.replace(toReplace[j], replaceWith[j], true, true);
           }

           creance.saveToFile(filename);
           AppHomeController.this.surgeryPath = filename;
           
           AppHomeController.this.createSurgeryProof(declarants[i]);
           
           AppHomeController.this.clearAfterDeclaring();
	   }
   }

   public String numberToLetter(int number) {
      String letters = "";
      String snumber = String.valueOf(number);
      int size = snumber.length();
      if (size > 0) {
         if (size == 1) {
            letters = letters + this.transformOne(number);
         } else if (size == 2) {
            letters = letters + this.transformTwo(number);
         } else if (size == 3) {
            letters = letters + this.transformThree(number);
         } else if (size == 4) {
            letters = letters + this.transformFour(number);
         } else if (size == 5) {
            letters = letters + this.transformFive(number);
         } else if (size == 6) {
            letters = letters + this.transformSix(number);
         } else if (size == 7) {
            letters = letters + this.transformSeven(number);
         }
      }

      return letters;
   }

   public String transformTwo(int number) {
      String snumber = String.valueOf(number);
      String letters = "";
      if (number >= 10 && number < 20) {
         letters = letters + this.dizaine[Arrays.asList(this.dizaineNombre).indexOf(snumber)];
      } else if (snumber != "00") {
         String firstL = snumber.substring(0, 1);
         firstL = firstL + "0";
         String secondL = snumber.substring(1);
         letters = letters + this.multipleDeDix[Arrays.asList(this.multipleNombre).indexOf(firstL)];
         letters = letters + " " + this.unite[Arrays.asList(this.uniteNombre).indexOf(secondL)];
      }

      return letters;
   }

   public String transformOne(int number) {
      String letters = "";
      String snumber = String.valueOf(number);
      letters = letters + this.unite[Arrays.asList(this.uniteNombre).indexOf(snumber)];
      return letters;
   }

   public String transformThree(int number) {
      String letters = "";
      String snumber = String.valueOf(number);
      String unit = snumber.substring(0, 1);
      String diz = snumber.substring(1);
      String sec = snumber.substring(1, 2);
      if (Integer.valueOf(unit) != 0) {
         if (Integer.valueOf(unit) == 1) {
            letters = letters + "Cent";
         } else {
            letters = letters + this.unite[Arrays.asList(this.uniteNombre).indexOf(unit)] + " cent";
         }
      }

      if (Integer.valueOf(sec) == 0) {
         letters = letters + " " + this.transformOne(Integer.valueOf(snumber.substring(2)));
      } else {
         letters = letters + " " + this.transformTwo(Integer.valueOf(diz));
      }

      return letters;
   }

   public String transformFour(int number) {
      String letters = "";
      String snumber = String.valueOf(number);
      String unit = snumber.substring(0, 1);
      String cent = snumber.substring(1);
      if (Integer.valueOf(unit) == 1) {
         letters = letters + "Mille";
      } else {
         letters = letters + this.unite[Arrays.asList(this.uniteNombre).indexOf(unit)] + " milles";
      }

      letters = letters + this.gererTree(cent);
      return letters;
   }

   public String transformFive(int number) {
      String letters = "";
      String snumber = String.valueOf(number);
      String diz = snumber.substring(0, 2);
      String cent = snumber.substring(2);
      letters = letters + this.transformTwo(Integer.valueOf(diz));
      letters = letters + " milles";
      letters = letters + " " + this.gererTree(cent);
      return letters;
   }

   public String transformSix(int number) {
      String letters = "";
      String snumber = String.valueOf(number);
      String diz = snumber.substring(0, 3);
      String cent = snumber.substring(3);
      letters = letters + this.transformThree(Integer.valueOf(diz));
      letters = letters + " milles";
      letters = letters + " " + this.gererTree(cent);
      return letters;
   }

   public String transformSeven(int number) {
      String letters = "";
      String snumber = String.valueOf(number);
      String first = snumber.substring(0, 1);
      String diz = snumber.substring(1, 4);
      String cent = snumber.substring(4);
      letters = letters + this.unite[Arrays.asList(this.uniteNombre).indexOf(first)] + " million";
      if (Integer.valueOf(first) > 1) {
         letters = letters + "s";
      }

      letters = letters + " " + this.gererTree(diz);
      if (Integer.valueOf(diz) > 0) {
         letters = letters + " milles";
      }

      letters = letters + " " + this.gererTree(cent);
      return letters;
   }

   public String gererTree(String cent) {
      String letters = "";
      int centValue = Integer.valueOf(cent);
      int longeur = String.valueOf(centValue).length();
      switch(longeur) {
      case 1:
         letters = letters + this.transformOne(Integer.valueOf(cent));
         break;
      case 2:
         letters = letters + this.transformTwo(Integer.valueOf(cent));
         break;
      case 3:
         letters = letters + this.transformThree(Integer.valueOf(cent));
      }

      return letters;
   }

   public void printCreances(String filename) {
      PrinterJob job = PrinterJob.getPrinterJob();
      PageFormat format = job.defaultPage();
      Paper paper = format.getPaper();
      paper.setImageableArea(0.0D, 0.0D, format.getWidth(), format.getHeight());
      format.setPaper(paper);
      Document document = new Document();
      document.loadFromFile(filename);
      job.setPrintable(document, format);
      if (job.printDialog()) {
         try {
            job.print();
         } catch (PrinterException var7) {
            var7.printStackTrace();
         }
      }

   }

   public String getPeriode() {
      String str = this.main.getOperativePeriode();
      String[] split = str.split("-");
      Integer[] nbreDeJours = new Integer[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
      int id = Integer.valueOf(split[1]) - 1;
      if (Integer.valueOf(split[0]) % 4 == 0) {
         nbreDeJours[1] = 29;
      }

      String periode = "01/" + split[1] + "/" + split[0] + " AU " + String.valueOf(nbreDeJours[id]) + "/" + split[1] + "/" + split[0];
      return periode;
   }

   public void createExamsProof() {
      Document doc = new Document();
      Section section = doc.addSection();
      Paragraph premier = section.addParagraph();
      premier.getFormat().setAfterSpacing(20.0F);
      TextRange titre = premier.appendText("TANGANYIKA HOSPITAL \nSERVICE D'OPHTALMOLOGIE");
      titre.getCharacterFormat().setBold(true);
      titre.getCharacterFormat().setBold(true);
      premier.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
      titre.getCharacterFormat().setFontName("Times New Roman");
      titre.getCharacterFormat().setFontSize(14.0F);
      Paragraph pDeux = section.addParagraph();
      pDeux.getFormat().setAfterSpacing(20.0F);
      TextRange tableTitre = pDeux.appendText("LISTES DES EXAMENS REALISES EN SERVICE D'OPHTALMOLOGIE DU " + this.getPeriode());
      tableTitre.getCharacterFormat().setBold(true);
      tableTitre.getCharacterFormat().setUnderlineStyle(UnderlineStyle.Single);
      pDeux.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
      tableTitre.getCharacterFormat().setFontName("Times New Roman");
      tableTitre.getCharacterFormat().setFontSize(12.0F);
      Table table = section.addTable(true);
      System.out.println("Nombre d examens : " + this.examCount);
      table.resetCells(this.examCount + 2, 6);
      table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Window);
      TableRow headerRow = table.getRows().get(0);
      headerRow.isHeader(true);
      headerRow.setHeight(20.0F);
      headerRow.setHeightType(TableRowHeightType.At_Least);
      String[] pheaders = new String[]{"No", "Examen", "ID du patient", "Date de l'examen", "No de la facture", "Prix"};

      TextRange t;
      int index;
      for(index = 0; index < pheaders.length; ++index) {
         Paragraph pcell = headerRow.getCells().get(index).addParagraph();
         headerRow.getCells().get(index).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
         headerRow.getCells().get(index).setCellWidthType(CellWidthType.Auto);
         t = pcell.appendText(pheaders[index]);
         t.getCharacterFormat().setBold(true);
         t.getCharacterFormat().setFontName("Times New Roman");
         t.getCharacterFormat().setFontSize(12.0F);
      }

      index = 1;

      for(int j = 0; j < this.examsDone.size(); ++j) {
         ArrayList<ArrayList<String>> examData = this.getOneExamData((String)this.examsDone.get(j), this.main.getOperativePeriode());

         for(int n = 0; n < examData.size(); ++n) {
            ArrayList<String> oneLine = (ArrayList<String>)examData.get(n);
            ArrayList<String> part = new ArrayList<String>();
            part.add(String.valueOf(index));
            part.add((String)this.examsDone.get(j));
            part.addAll(oneLine);

            for(int m = 0; m < 6; ++m) {
               t = table.getRows().get(index).getCells().get(m).addParagraph().appendText((String)part.get(m));
               table.getRows().get(index).setHeight(20.0F);
               table.getRows().get(index).setHeightType(TableRowHeightType.At_Least);
               table.getRows().get(index).getCells().get(m).setCellWidthType(CellWidthType.Auto);
               t.getCharacterFormat().setFontName("Times New Roman");
               t.getCharacterFormat().setFontSize(12.0F);
            }

            ++index;
         }
      }

      this.addTotalLineToTable(table, this.examTotalDeclare, this.examCount + 1, 4);
      String filename = Tools.creerDocument(this.main.getPseudo(), "EXAMS_PROOFS");
      this.setMargin(doc, 50.0F);
      doc.saveToFile(filename, FileFormat.Docx_2013);
   }

   public void createSecondExamsProofs() {
      ArrayList<String> ids = this.getPatientIdByPeriode(this.main.getOperativePeriode());
      Document doc = new Document();
      Section section = doc.addSection();
      Paragraph premier = section.addParagraph();
      premier.getFormat().setAfterSpacing(20.0F);
      TextRange titre = premier.appendText("TANGANYIKA HOSPITAL \nSERVICE D'OPHTALMOLOGIE");
      titre.getCharacterFormat().setBold(true);
      titre.getCharacterFormat().setBold(true);
      premier.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
      titre.getCharacterFormat().setFontName("Times New Roman");
      titre.getCharacterFormat().setFontSize(14.0F);
      Paragraph pDeux = section.addParagraph();
      pDeux.getFormat().setAfterSpacing(20.0F);
      TextRange tableTitre = pDeux.appendText("LISTES DES EXAMENS REALISES EN SERVICE D'OPHTALMOLOGIE DU " + this.getPeriode());
      tableTitre.getCharacterFormat().setBold(true);
      tableTitre.getCharacterFormat().setUnderlineStyle(UnderlineStyle.Single);
      pDeux.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
      tableTitre.getCharacterFormat().setFontName("Times New Roman");
      tableTitre.getCharacterFormat().setFontSize(12.0F);
      Table table = section.addTable(true);
      table.resetCells(this.getExamsNumber() + 2, 6);
      table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Window);
      TableRow headerRow = table.getRows().get(0);
      headerRow.isHeader(true);
      headerRow.setHeight(20.0F);
      headerRow.setHeightType(TableRowHeightType.At_Least);
      String[] pheaders = new String[]{"No", "ID du patient", "Examen", "Date de l'examen", "No de la facture", "Prix"};

      TextRange t;
      int index;
      for(index = 0; index < pheaders.length; ++index) {
         Paragraph pcell = headerRow.getCells().get(index).addParagraph();
         headerRow.getCells().get(index).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
         headerRow.getCells().get(index).setCellWidthType(CellWidthType.Auto);
         t = pcell.appendText(pheaders[index]);
         t.getCharacterFormat().setBold(true);
         t.getCharacterFormat().setFontName("Times New Roman");
         t.getCharacterFormat().setFontSize(12.0F);
      }

      index = 1;

      for(int j = 0; j < ids.size(); ++j) {
         ArrayList<ArrayList<String>> onePatient = this.getOnePatientExams((String)ids.get(j), this.main.getOperativePeriode());
         int span = onePatient.size();
         table.applyVerticalMerge(1, index, index - 1 + span);

         for(int m = 0; m < onePatient.size(); ++m) {
            ArrayList<String> oneExam = (ArrayList<String>)onePatient.get(m);
            oneExam.add(0, String.valueOf(index));
            int n;
            if (m == 0) {
               for(n = 0; n < oneExam.size() - 1; ++n) {
                  t = table.getRows().get(index).getCells().get(n).addParagraph().appendText((String)oneExam.get(n));
                  t.getCharacterFormat().setFontName("Times New Roman");
                  t.getCharacterFormat().setFontSize(12.0F);
                  table.getRows().get(index).getCells().get(n).setCellWidthType(CellWidthType.Auto);
               }
            } else {
               for(n = 0; n < oneExam.size() - 1; ++n) {
                  if (n != 1) {
                     t = table.getRows().get(index).getCells().get(n).addParagraph().appendText((String)oneExam.get(n));
                     t.getCharacterFormat().setFontName("Times New Roman");
                     t.getCharacterFormat().setFontSize(12.0F);
                     table.getRows().get(index).getCells().get(n).setCellWidthType(CellWidthType.Auto);
                  }
               }
            }

            table.getRows().get(index).setHeight(20.0F);
            table.getRows().get(index).setHeightType(TableRowHeightType.At_Least);
            ++index;
         }
      }

      this.addTotalLineToTable(table, this.examTotalDeclare, this.getExamsNumber() + 1, 4);
      String filename = Tools.creerDocument(this.main.getPseudo(), "EXAMS_PROOFS");
      this.setMargin(doc, 50.0F);
      doc.saveToFile(filename, FileFormat.Docx_2013);
   }

   public void createSurgeryProof() {
      Document doc = new Document();
      Section section = doc.addSection();
      section.getPageSetup().setOrientation(PageOrientation.Landscape);
      Paragraph premier = section.addParagraph();
      premier.getFormat().setAfterSpacing(20.0F);
      TextRange titre = premier.appendText("TANGANYIKA HOSPITAL \nSERVICE D'OPHTALMOLOGIE");
      titre.getCharacterFormat().setBold(true);
      titre.getCharacterFormat().setBold(true);
      premier.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
      titre.getCharacterFormat().setFontName("Times New Roman");
      titre.getCharacterFormat().setFontSize(14.0F);
      Paragraph pDeux = section.addParagraph();
      pDeux.getFormat().setAfterSpacing(20.0F);
      TextRange tableTitre = pDeux.appendText("LISTES DES OPERATIONS REALISES EN SERVICE D'OPHTALMOLOGIE DU " + this.getPeriode()+" PAR ");
      tableTitre.getCharacterFormat().setBold(true);
      tableTitre.getCharacterFormat().setUnderlineStyle(UnderlineStyle.Single);
      pDeux.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
      tableTitre.getCharacterFormat().setFontName("Times New Roman");
      tableTitre.getCharacterFormat().setFontSize(12.0F);
      Table table = section.addTable(true);
      table.resetCells(this.surgeryBySurgeon + 2, 8);
      table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Window);
      TableRow headerRow = table.getRows().get(0);
      headerRow.isHeader(true);
      headerRow.setHeight(20.0F);
      headerRow.setHeightType(TableRowHeightType.At_Least);
      String[] pheaders = new String[]{"No", "Acte", "ID du patient", "Date", "No Facture", "Consommable", "Prix", "Acte"};

      TextRange t;
      int index;
      for(index = 0; index < pheaders.length; ++index) {
         Paragraph pcell = headerRow.getCells().get(index).addParagraph();
         headerRow.getCells().get(index).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
         headerRow.getCells().get(index).setCellWidthType(CellWidthType.Auto);
         t = pcell.appendText(pheaders[index]);
         t.getCharacterFormat().setBold(true);
         t.getCharacterFormat().setFontName("Times New Roman");
         t.getCharacterFormat().setFontSize(12.0F);
      }

      index = 1;

      int n;
      for(int j = 0; j < this.surgeriesDoneBySurgeon.size(); ++j) {
         ArrayList<ArrayList<String>> examData = this.getOneSurgeryData((String)this.surgeriesDoneBySurgeon.get(j), this.main.getOperativePeriode(), this.main.getPseudo());

         for(n = 0; n < examData.size(); ++n) {
            ArrayList<String> oneLine = (ArrayList<String>)examData.get(n);
            this.surgeryActeDeclare -= Integer.valueOf((String)oneLine.get(3));
            ArrayList<String> part = new ArrayList<String>();
            part.add(String.valueOf(index));
            part.add((String)this.surgeriesDoneBySurgeon.get(j));
            part.addAll(oneLine);

            for(int m = 0; m < 8; ++m) {
               //System.out.println(part.get(m));
               t = table.getRows().get(index).getCells().get(m).addParagraph().appendText((String)part.get(m));
               table.getRows().get(index).setHeight(20.0F);
               table.getRows().get(index).setHeightType(TableRowHeightType.At_Least);
               table.getRows().get(index).getCells().get(m).setCellWidthType(CellWidthType.Auto);
               t.getCharacterFormat().setFontName("Times New Roman");
               t.getCharacterFormat().setFontSize(12.0F);
            }

            ++index;
         }
      }

      this.addTotalLineToTable(table, this.surgeryActeDeclare, this.surgeryBySurgeon + 1, 6);
      Paragraph pTrois = section.addParagraph();
      int assIpr = this.surgeryActeDeclare / 2 - 150000;
      n = assIpr * 15 / 100;
      int arendre = this.surgeryActeDeclare / 2 - n;
      this.totalRendu = arendre;
      this.replaceSomme(this.totalRendu);
      TextRange t3 = pTrois.appendText("Montant assujeti à l'IPR = (" + this.surgeryActeDeclare + " / 2 ) - 150000 = " + assIpr + "\nMontant de l'IPR= " + assIpr + " * 15 / 100 = " + n + "\nMontant net à payer = (" + this.surgeryActeDeclare + " / 2) - " + n + " = " + arendre);
      pTrois.getFormat().setBeforeSpacing(20.0F);
      pTrois.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
      t3.getCharacterFormat().setFontName("Times New Roman");
      t3.getCharacterFormat().setFontSize(12.0F);
      String filename = Tools.creerDocument(this.main.getPseudo(), "SURGERY_PROOFS");
      this.setMargin(doc, 25.0F);
      doc.saveToFile(filename, FileFormat.Docx_2013);
   }
   
   public void createSurgeryProof(String pseudo) {
	      Document doc = new Document();
	      Section section = doc.addSection();
	      section.getPageSetup().setOrientation(PageOrientation.Landscape);
	      Paragraph premier = section.addParagraph();
	      premier.getFormat().setAfterSpacing(20.0F);
	      TextRange titre = premier.appendText("TANGANYIKA HOSPITAL \nSERVICE D'OPHTALMOLOGIE");
	      titre.getCharacterFormat().setBold(true);
	      titre.getCharacterFormat().setBold(true);
	      premier.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
	      titre.getCharacterFormat().setFontName("Times New Roman");
	      titre.getCharacterFormat().setFontSize(14.0F);
	      Paragraph pDeux = section.addParagraph();
	      pDeux.getFormat().setAfterSpacing(20.0F);
	      TextRange tableTitre = pDeux.appendText("LISTES DES OPERATIONS REALISES EN SERVICE D'OPHTALMOLOGIE DU " + this.getPeriode()+" PAR Dr. "+this.getUsersInfo(pseudo)[0].toUpperCase());
	      tableTitre.getCharacterFormat().setBold(true);
	      tableTitre.getCharacterFormat().setUnderlineStyle(UnderlineStyle.Single);
	      pDeux.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
	      tableTitre.getCharacterFormat().setFontName("Times New Roman");
	      tableTitre.getCharacterFormat().setFontSize(12.0F);
	      Table table = section.addTable(true);
	      table.resetCells(this.surgeryBySurgeon + 2, 8);
	      table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Window);
	      TableRow headerRow = table.getRows().get(0);
	      headerRow.isHeader(true);
	      headerRow.setHeight(20.0F);
	      headerRow.setHeightType(TableRowHeightType.At_Least);
	      String[] pheaders = new String[]{"No", "Acte", "ID du patient", "Date", "No Facture", "Consommable", "Prix", "Acte"};

	      TextRange t;
	      int index;
	      for(index = 0; index < pheaders.length; ++index) {
	         Paragraph pcell = headerRow.getCells().get(index).addParagraph();
	         headerRow.getCells().get(index).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
	         headerRow.getCells().get(index).setCellWidthType(CellWidthType.Auto);
	         t = pcell.appendText(pheaders[index]);
	         t.getCharacterFormat().setBold(true);
	         t.getCharacterFormat().setFontName("Times New Roman");
	         t.getCharacterFormat().setFontSize(12.0F);
	      }

	      index = 1;

	      int n;
	      for(int j = 0; j < this.surgeriesDoneBySurgeon.size(); ++j) {
	         ArrayList<ArrayList<String>> examData = this.getOneSurgeryData((String)this.surgeriesDoneBySurgeon.get(j), this.main.getOperativePeriode(), pseudo);

	         for(n = 0; n < examData.size(); ++n) {
	            ArrayList<String> oneLine = (ArrayList<String>)examData.get(n);
	            this.surgeryActeDeclare -= Integer.valueOf((String)oneLine.get(3));
	            ArrayList<String> part = new ArrayList<String>();
	            part.add(String.valueOf(index));
	            part.add((String)this.surgeriesDoneBySurgeon.get(j));
	            part.addAll(oneLine);

	            for(int m = 0; m < 8; ++m) {
	               //System.out.println(part.get(m));
	               t = table.getRows().get(index).getCells().get(m).addParagraph().appendText((String)part.get(m));
	               table.getRows().get(index).setHeight(20.0F);
	               table.getRows().get(index).setHeightType(TableRowHeightType.At_Least);
	               table.getRows().get(index).getCells().get(m).setCellWidthType(CellWidthType.Auto);
	               t.getCharacterFormat().setFontName("Times New Roman");
	               t.getCharacterFormat().setFontSize(12.0F);
	            }

	            ++index;
	         }
	      }

	      this.addTotalLineToTable(table, this.surgeryActeDeclare, this.surgeryBySurgeon + 1, 6);
	      Paragraph pTrois = section.addParagraph();
	      int assIpr = this.surgeryActeDeclare / 2 - 150000;
	      
	      boolean ipr=false;
	      
	      if(assIpr>0) {
	    	  n = assIpr * 15 / 100;
	    	  ipr=true;
	      }else {
	    	  n = 0;
	      }
	      
	      int arendre = this.surgeryActeDeclare / 2 - n;
	      this.totalRendu = arendre;
	      this.replaceSomme(this.totalRendu);
	      
	      if(ipr) {
	    	  TextRange t3 = pTrois.appendText("Montant assujeti à l'IPR = (" + this.surgeryActeDeclare + " / 2 ) - 150000 = " + assIpr + "\nMontant de l'IPR= " + assIpr + " * 15 / 100 = " + n + "\nMontant net à payer = (" + this.surgeryActeDeclare + " / 2) - " + n + " = " + arendre);
	    	  t3.getCharacterFormat().setFontName("Times New Roman");
		      t3.getCharacterFormat().setFontSize(12.0F);
	      }else {
	    	  TextRange t3 = pTrois.appendText("Montant assujeti à l'IPR = (" + this.surgeryActeDeclare + " / 2 ) - 150000 = " + assIpr + "\nMontant de l'IPR= "+ n + "\nMontant net à payer = (" + this.surgeryActeDeclare + " / 2) - " + n + " = " + arendre);
	    	  t3.getCharacterFormat().setFontName("Times New Roman");
		      t3.getCharacterFormat().setFontSize(12.0F);
	      }
	      
	      pTrois.getFormat().setBeforeSpacing(20.0F);
	      pTrois.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
	      
	      String filename = Tools.creerDocument(pseudo, "SURGERY_PROOFS");
	      this.setMargin(doc, 25.0F);
	      doc.saveToFile(filename, FileFormat.Docx_2013);
	   }
   
   
   

   public int calculateSurgeryIncome(String periode) {
      int inc = 0;
      //int argentNet = Integer.valueOf(this.surPrice.getText());
      int arN=this.surgeryIncomeBySurgeon;
      int assIpr;
      /*for(assIpr = 0; assIpr < this.periodeSurgeriesDone.size(); ++assIpr) {
         ArrayList<ArrayList<String>> examData = this.getOneSurgeryData((String)this.periodeSurgeriesDone.get(assIpr), periode);

         for(int n = 0; n < examData.size(); ++n) {
            ArrayList<String> oneLine = (ArrayList<String>)examData.get(n);
            argentNet -= Integer.valueOf((String)oneLine.get(3));
         }
      }*/
      
      for(assIpr = 0; assIpr < this.surgeriesDoneBySurgeon.size(); ++assIpr) {
          ArrayList<ArrayList<String>> examData = this.getOneSurgeryData((String)this.surgeriesDoneBySurgeon.get(assIpr), periode, this.main.getPseudo());

          for(int n = 0; n < examData.size(); ++n) {
             ArrayList<String> oneLine = (ArrayList<String>)examData.get(n);
             arN -= Integer.valueOf((String)oneLine.get(3));
          }
      }

      if (arN > 0) {
         assIpr = arN / 2 - 150000;
         if (assIpr > 0) {
            int n = assIpr * 15 / 100;
            inc = arN / 2 - n;
         } else {
            inc = arN / 2;
         }
      }

      this.surgeriesDoneBySurgeon.clear();
      return inc;
   }

   public ArrayList<ArrayList<String>> getOneExamData(String examination, String date) {
      ArrayList<ArrayList<String>> examData = new ArrayList<ArrayList<String>>();
      String sql = "SELECT * FROM register WHERE exams LIKE '%" + examination + "%' AND monthYear='" + date + "'";
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            ArrayList<String> examD = new ArrayList<String>();
            examD.add(rst.getString("patientID"));
            examD.add(rst.getString("date"));
            examD.add(rst.getString("nofacture"));
            Statement nstm = conn.createStatement();
            ResultSet nrst = nstm.executeQuery("SELECT price FROM exams WHERE exam='" + examination + "'");

            while(nrst.next()) {
               examD.add(String.valueOf(nrst.getInt("price")));
            }

            examData.add(examD);
         }
      } catch (SQLException var11) {
         var11.printStackTrace();
      }

      return examData;
   }

   public void replaceSomme(int somme) {
      Document doc = new Document();
      doc.loadFromFile(this.surgeryPath);
      String var10000 = this.numberToLetter(somme);
      String montant = var10000 + " Francs Bu ( " + String.valueOf(somme) + " FBi)";
      doc.replace("@somme@", montant, true, true);
      doc.saveToFile(this.surgeryPath, FileFormat.Docx_2013);
   }

   public ArrayList<ArrayList<String>> getOneSurgeryData(String examination, String date, String surgeon) {
      ArrayList<ArrayList<String>> examData = new ArrayList<ArrayList<String>>();
      String sql = "SELECT * FROM register WHERE surgery LIKE '%" + examination + "%' AND monthYear='" + date + "' AND doctor='"+surgeon+"'";
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            ArrayList<String> examD = new ArrayList<String>();
            examD.add(rst.getString("patientID"));
            examD.add(rst.getString("date"));
            examD.add(rst.getString("nofacture"));
            String surgery = rst.getString("surgery");
            String consommable = rst.getString("consommable");
            String cons = "";
            if (surgery.indexOf("/") != -1) {
               String[] surSplit = surgery.split("/");
               String[] consSplit = consommable.split("/");
               int index = Arrays.asList(surSplit).indexOf(examination);
               if (consSplit.length == surSplit.length) {
                  cons = consSplit[index];
               }
            } else {
               cons = consommable;
            }

            examD.add(cons);
            String price = "";
            Statement nstm = conn.createStatement();

            for(ResultSet nrst = nstm.executeQuery("SELECT price FROM surgeries WHERE surgery='" + examination + "'"); nrst.next(); price = String.valueOf(nrst.getInt("price"))) {
            }

            examD.add(price);
            double acte = 0.0D;
            if (!cons.isBlank()) {
               acte = Double.valueOf(price) - Double.valueOf(cons);
            }

            examD.add(String.valueOf(acte));
            examData.add(examD);
         }
      } catch (SQLException var16) {
         var16.printStackTrace();
      }

      return examData;
   }

   @FXML
   public void newSurgeryExam() {
      this.main.showExamAdder();
   }

   @FXML
   public void deleteAct() {
      this.main.deleteAct();
   }

   @FXML
   public void showExamsOnscreen() {
      this.main.showExamChoser();
   }

   @FXML
   public void removeExamsFromScreen() {
      this.main.showExamRemover();
   }

   public void logOut() {
      this.login.setText("Log in");
      this.main.setPseudo("");
      this.setCentralPane(this.main.getLoginPane());
      this.main.getLoginController().clearField();
      this.numberLabels.clear();
      this.main.getRegistrationScreenController().clearGridPane(this.sumGrid);
      this.main.getRegistrationScreenController().clearArrays();
      this.setButtonBindings();
      this.creanceLabel.setText("");
      this.todaysReg.setText("");
      this.clearAccordion(this.todaysAccord);
      this.setThirdPaneInvisible();
      this.fileTree.setVisible(false);
   }

   public void setButtonBindings() {
      this.disAllowDeclarer.set(true);
      this.isNotAtomos.set(true);
      this.noUpdatable.set(true);
      this.declarerExam.disableProperty().bind(this.disAllowDeclarer);
      this.declarerSurgery.disableProperty().bind(this.isNotAtomos);
      this.update.disableProperty().bind(this.noUpdatable);
      this.acts.disableProperty().bind(this.noUpdatable);
      this.rapports.disableProperty().bind(this.noUpdatable);
      this.data.disableProperty().bind(this.noUpdatable);
      this.users.disableProperty().bind(this.noUpdatable);
      this.drugs.disableProperty().bind(this.noUpdatable);
      this.protocols.disableProperty().bind(this.noUpdatable);
      this.help.disableProperty().bind(noUpdatable);
   }

   public void clearAfterDeclaring() {
      this.surgeriesDone.clear();
      this.surgeriesDoneBySurgeon.clear();
      this.surgeryBySurgeon=0;
      this.examsDone.clear();
      this.examCount = 0;
   }

   public void setMargin(Document doc, float margin) {
      Section section = doc.getSections().get(0);
      section.getPageSetup().getMargins().setAll(margin);
   }

   public String[] getUsersInfo(String pseudo) {
      String[] userInfo = new String[2];
      Connection conn = Tools.getConnected();
      String sql = "SELECT prenom, expourcentage FROM logintable where pseudo='" + pseudo + "'";

      try {
         Statement stm = conn.createStatement();

         for(ResultSet rst = stm.executeQuery(sql); rst.next(); userInfo[1] = rst.getString("expourcentage")) {
            userInfo[0] = rst.getString("prenom");
         }
      } catch (SQLException var7) {
         var7.printStackTrace();
      }

      return userInfo;
   }

   public void giveTodayStatics() {
      String today = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
      ArrayList<String[]> data = this.setAccordionTitles();
      if (data.size() > 0) {
         this.todaysReg.setText("Exams registered on " + today);
      } else {
         this.todaysReg.setText("No exams registered on " + today);
      }

      this.setAccordionContent(data, today);
   }

   public ArrayList<String[]> setAccordionTitles() {
      ArrayList<String[]> examsD = new ArrayList<String[]>();
      String today = (new SimpleDateFormat("dd/MM/yyyy")).format(Calendar.getInstance().getTime());
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();

         for(int i = 0; i < this.main.getRegistrationScreenController().getExamLabels().size(); ++i) {
            String examination = ((Label)this.main.getRegistrationScreenController().getExamLabels().get(i)).getText().trim();
            String sql = "SELECT COUNT(exams) AS total FROM register WHERE exams LIKE '%" + examination + "%' AND registerDate='" + today + "'";
            ResultSet rst = stm.executeQuery(sql);

            while(rst.next()) {
               int number = rst.getInt("total");
               if (number > 0) {
                  String[] one = new String[]{examination, String.valueOf(number)};
                  examsD.add(one);
               }
            }
         }
      } catch (SQLException var11) {
         var11.printStackTrace();
      }

      return examsD;
   }

   public void setAccordionContent(ArrayList<String[]> data, String today) {
      this.clearAccordion(this.todaysAccord);
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();

         for(int i = 0; i < data.size(); ++i) {
            String[] one = (String[])data.get(i);
            String sql = "SELECT patientId from register where exams LIKE '%" + one[0] + "%' AND registerDate='" + today + "'";
            ResultSet rst = stm.executeQuery(sql);
            TextArea content = new TextArea();

            String texte;
            for(texte = "ID des patients: \n\n"; rst.next(); texte = texte + rst.getString("patientId") + "\n") {
            }

            content.setText(texte);
            content.setEditable(false);
            String title = one[0] + " (" + one[1] + ")";
            TitledPane pane = new TitledPane(title, content);
            pane.setPrefHeight(-1.0D);
            this.todaysAccord.getPanes().add(pane);
         }
      } catch (SQLException var13) {
         var13.printStackTrace();
      }

   }

   public void clearAccordion(Accordion acc) {
      List<TitledPane> panes = acc.getPanes();
      acc.getPanes().removeAll(panes);
   }

   public void giveMonthsCreances() {
      String[] userInfo = this.getUsersInfo(this.main.getPseudo());
      int pourcentage = Integer.valueOf(userInfo[1]);
      int creance = Integer.valueOf(this.income * pourcentage / 100);
      this.creanceLabel.setText("Your monthly creances : " + creance);
   }

   public void giveMonthsCreances(String periode) {
      String[] userInfo = this.getUsersInfo(this.main.getPseudo());
      int pourcentage = Integer.valueOf(userInfo[1]);
      int creance = Integer.valueOf(this.income * pourcentage / 100);
      String creanceText = "Your monthly creances for " + periode + " : \n\n\tFor exams: " + creance;
      if (this.main.getPseudo().equals("atomos")|| this.main.getPseudo().equals("rich")) {
         int sur = this.calculateSurgeryIncome(periode);
         int tot = creance + sur;
         creanceText = creanceText + "\n\n\tFor surgery : " + sur + "\n\n\tTotal : " + tot;
      }

      this.creanceLabel.setText(creanceText);
   }

   public void setThirdPaneInvisible() {
      this.thirdPane.setVisible(false);
   }

   public void setThirdPaneVisible() {
      this.thirdPane.setVisible(true);
   }

   public ArrayList<String> getDistinctPeriodes() {
      ArrayList<String> periodes = new ArrayList<String>();
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery("SELECT DISTINCT monthYear FROM register ORDER BY monthYear DESC LIMIT 5");

         while(rst.next()) {
            periodes.add(rst.getString("monthYear"));
         }
      } catch (SQLException var5) {
         var5.printStackTrace();
      }

      return periodes;
   }

   public void handleComboChanges() {
	      this.periodeCombo.valueProperty().addListener(new ChangeListener<String>() {
	         public void changed(ObservableValue<? extends String> obs, String old, String vnew) {
	            if (vnew.equals("")) {
	               AppHomeController.this.sumUp();
	            } else {
	               AppHomeController.this.sumUpChosenPeriode(vnew);
	            }

	         }
	      });
	   }

   @FXML
   public void getPeriodePatientList() {
      this.main.showPeriodeChoser();
      if (this.main.getIsOperativePeriodeSet()) {
         Connection conn = Tools.getConnected();
         ArrayList<String[]> id_dates = new ArrayList<String[]>();

         String first;
         try {
            Statement stm = conn.createStatement();
            first = "SELECT DISTINCT patientId, date FROM register WHERE monthYear='" + this.main.getOperativePeriode() + "' ORDER BY date ASC";
            ResultSet rst = stm.executeQuery(first);

            while(rst.next()) {
               String[] entry = new String[]{rst.getString("date"), rst.getString("patientId")};
               id_dates.add(entry);
            }
         } catch (SQLException var14) {
            var14.printStackTrace();
         }

         String[] headers = new String[]{"Date", "Patient Id"};
         if (id_dates.size() > 0) {
            first = "TANGANYIKA HOSPITAL \nSERVICE D'OPHTALMOLOGIE";
            String second = "LISTES DES PATIENTS AVEC EXAMENS SUR LA PERIODE DU " + this.getPeriode();
            Document doc = this.createDocumentAndHeaders(first, second);
            Section section2 = doc.addSection();
            section2.addColumn(160.0F, 10.0F);
            section2.addColumn(160.0F, 10.0F);
            Table table = section2.addTable(true);
            table.resetCells(id_dates.size() + 1, 2);
            table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Window);
            TableRow headerRow = table.getRows().get(0);
            headerRow.isHeader(true);
            headerRow.setHeight(20.0F);
            headerRow.setHeightType(TableRowHeightType.At_Least);

            TextRange t;
            int j;
            for(j = 0; j < headers.length; ++j) {
               Paragraph pcell = headerRow.getCells().get(j).addParagraph();
               headerRow.getCells().get(j).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
               headerRow.getCells().get(j).setCellWidthType(CellWidthType.Auto);
               t = pcell.appendText(headers[j]);
               t.getCharacterFormat().setBold(true);
               t.getCharacterFormat().setFontName("Times New Roman");
               t.getCharacterFormat().setFontSize(12.0F);
            }

            for(j = 0; j < id_dates.size(); ++j) {
               String[] entry = (String[])id_dates.get(j);

               for(int m = 0; m < 2; ++m) {
                  t = table.getRows().get(j + 1).getCells().get(m).addParagraph().appendText(entry[m]);
                  table.getRows().get(j + 1).setHeight(20.0F);
                  table.getRows().get(j + 1).setHeightType(TableRowHeightType.At_Least);
                  table.getRows().get(j + 1).getCells().get(m).setCellWidthType(CellWidthType.Auto);
                  t.getCharacterFormat().setFontName("Times New Roman");
                  t.getCharacterFormat().setFontSize(12.0F);
               }
            }

            String filename = Tools.creerDocument(this.main.getPseudo(), "RAPPORTS");
            this.setMargin(doc, 50.0F);
            doc.saveToFile(filename, FileFormat.Docx_2013);
            Tools.showEphemereText("Rapport créée avec succès");
            this.main.setIsIntervalSet(false);
         }
      }

   }

   public Document createDocumentAndHeaders(String first, String second) {
      Document doc = new Document();
      Section section = doc.addSection();
      Paragraph premier = section.addParagraph();
      premier.getFormat().setAfterSpacing(20.0F);
      TextRange titre = premier.appendText(first);
      titre.getCharacterFormat().setBold(true);
      titre.getCharacterFormat().setBold(true);
      premier.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
      titre.getCharacterFormat().setFontName("Times New Roman");
      titre.getCharacterFormat().setFontSize(14.0F);
      Paragraph pDeux = section.addParagraph();
      pDeux.getFormat().setAfterSpacing(20.0F);
      TextRange tableTitre = pDeux.appendText(second);
      tableTitre.getCharacterFormat().setBold(true);
      tableTitre.getCharacterFormat().setUnderlineStyle(UnderlineStyle.Single);
      pDeux.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
      tableTitre.getCharacterFormat().setFontName("Times New Roman");
      tableTitre.getCharacterFormat().setFontSize(12.0F);
      return doc;
   }

   public void addTotalLineToTable(Table table, int totalEx, int rowIndex, int span) {
      TextRange t = table.getRows().get(rowIndex).getCells().get(0).addParagraph().appendText("TOTAL");
      table.getRows().get(rowIndex).getCells().get(0).setCellWidthType(CellWidthType.Auto);
      table.applyHorizontalMerge(rowIndex, 0, span);
      table.getRows().get(rowIndex).setHeight(20.0F);
      table.getRows().get(rowIndex).setHeightType(TableRowHeightType.At_Least);
      t.getCharacterFormat().setFontName("Times New Roman");
      t.getCharacterFormat().setFontSize(12.0F);
      t = table.getRows().get(rowIndex).getCells().get(span + 1).addParagraph().appendText(String.valueOf(totalEx));
      table.getRows().get(rowIndex).getCells().get(span + 1).setCellWidthType(CellWidthType.Auto);
   }

   @FXML
   public void deleteDataEntry() {
      this.main.deleteData();
   }

   @FXML
   public void modifyEntry() {
      this.main.showEntryModifier();
   }

   public void testOnePatient(String date) {
      ArrayList<String> ids = this.getPatientIdByPeriode(date);

      for(int i = 0; i < ids.size(); ++i) {
         this.getOnePatientExams((String)ids.get(i), date);
      }

   }

   public ArrayList<String> getPatientIdByPeriode(String date) {
      ArrayList<String> patientIds = new ArrayList<String>();
      Connection conn = Tools.getConnected();
      String sql = "SELECT DISTINCT patientId, date FROM register WHERE monthYear='" + date + "' AND exams <> '' ORDER BY date ASC";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            patientIds.add(rst.getString("patientId"));
         }
      } catch (SQLException var7) {
         var7.printStackTrace();
      }

      return patientIds;
   }

   public ArrayList<String> getPatientIdByPeriode(String date, boolean b) {
      ArrayList<String> patientIds = new ArrayList<String>();
      Connection conn = Tools.getConnected();
      String sql = "SELECT DISTINCT patientId FROM register WHERE monthYear='" + date + "' AND exams <> ''";

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            patientIds.add(rst.getString("patientId"));
         }
      } catch (SQLException var8) {
         var8.printStackTrace();
      }

      return patientIds;
   }

   public ArrayList<ArrayList<String>> getOnePatientExams(String id, String date) {
      ArrayList<ArrayList<String>> patientExams = new ArrayList<ArrayList<String>>();
      String sql = "SELECT * FROM register WHERE patientID='" + id + "' AND monthYear='" + date + "'";
      Connection conn = Tools.getConnected();

      try {
         Statement stm = conn.createStatement();
         ResultSet rst = stm.executeQuery(sql);

         while(rst.next()) {
            String exams = rst.getString("exams");
            String[] examSplit = exams.split("/");

            for(int i = 0; i < examSplit.length; ++i) {
               ArrayList<String> oneExam = new ArrayList<String>();
               oneExam.add(id);
               oneExam.add(examSplit[i]);
               oneExam.add(rst.getString("date"));
               oneExam.add(rst.getString("nofacture"));
               Statement nstm = conn.createStatement();
               ResultSet nrst = nstm.executeQuery("SELECT price FROM exams WHERE exam='" + examSplit[i] + "'");

               while(nrst.next()) {
                  oneExam.add(String.valueOf(nrst.getInt("price")));
               }

               oneExam.add(String.valueOf(examSplit.length));
               patientExams.add(oneExam);
            }
         }
      } catch (SQLException var14) {
         var14.printStackTrace();
      }

      return patientExams;
   }

   public int getExamsNumber() {
      int examNumber = 0;
      ArrayList<String> ids = this.getPatientIdByPeriode(this.main.getOperativePeriode());

      for(int i = 0; i < ids.size(); ++i) {
         ArrayList<ArrayList<String>> onePatient = this.getOnePatientExams((String)ids.get(i), this.main.getOperativePeriode());
         examNumber += onePatient.size();
      }

      return examNumber;
   }

   public void showDoubleEntries() {
      this.main.showPeriodeChoser();
      this.main.getEntryList().clear();
      if (this.main.getIsOperativePeriodeSet()) {
         ArrayList<String> ids = this.getPatientIdByPeriode(this.main.getOperativePeriode(), true);
         Connection conn = Tools.getConnected();

         try {
            Statement stm = conn.createStatement();

            for(int i = 0; i < ids.size(); ++i) {
               String var10000 = (String)ids.get(i);
               String sql = "SELECT COUNT(id) as totalId FROM register WHERE (patientId='" + var10000 + "' AND monthYear='" + this.main.getOperativePeriode() + "')";
               ResultSet rst = stm.executeQuery(sql);
               rst.next();
               int idNumber = rst.getInt("totalId");
               if (idNumber > 1) {
                  String var10001 = (String)ids.get(i);
                  ResultSet rs = stm.executeQuery("SELECT * FROM register WHERE (patientId='" + var10001 + "' AND monthYear='" + this.main.getOperativePeriode() + "')");

                  while(rs.next()) {
                     this.main.getEntryList().add(new Entry(rs.getInt("id"), rs.getString("patientId"), rs.getString("exams"), rs.getString("surgery"), rs.getString("date"), false));
                  }
               }
            }
         } catch (SQLException var11) {
            var11.printStackTrace();
         }

         this.main.deleteData(true);
      }

   }
   
   public ArrayList<String[]> readFromXls(File file) {
	   
       
       ArrayList<String[]> idsDates= new ArrayList<String[]>();
       
       if(file!=null) {
    	   
    	   boolean success = false;
           int tentatives = 0;
           int maxTentatives = 3;
           
           while (!success && tentatives < maxTentatives) {
    	   
        	   	try {
        	   		Workbook wb = new Workbook();
        	   		wb.loadFromFile(file.getAbsolutePath());
        	   		Worksheet sourceSheet = wb.getWorksheets().get(0);
        	   		Worksheet destSheet = wb.getWorksheets().get(1);
    	   
        	   		CellRange sourceRange = sourceSheet.getAllocatedRange();
    	   
        	   		CellRange destRange = destSheet.getCellRange(1, 1);
    	   
        	   		sourceRange.copy(destRange);
    	   
        	   		wb.saveToFile(file.getAbsolutePath(), ExcelVersion.Version2013);
        	   		
    	   
        	   		CellRange locatedRange = wb.getWorksheets().get(1).getAllocatedRange();
        	   		
        	   		String[] ids = new String[locatedRange.getRowCount()-2];
        	   		String[] dates = new String[locatedRange.getRowCount()-2];
        	   		
        	   		
    	   
        	   		for(int i=2; i<locatedRange.getRowCount(); i++) {
        	   			int a=i-2;
        	   			ids[a]=locatedRange.get(i,4).getValue();
        	   			dates[a]=locatedRange.get(i,3).getValue().trim().split(" ")[0];
        	   		}
        	   		
        	   		success=true;
        	   		
        	   		idsDates.add(ids);
        	   		idsDates.add(dates);
    	   
        	   	}catch (Exception e) {
        	   		System.err.println("Erreur : Le fichier est occupé ou inaccessible.");
                    
                    if (tentatives < maxTentatives) {
                        closeExcel();
                        
                        try {
                            System.out.println("Attente avant nouvel essai...");
                            TimeUnit.SECONDS.sleep(2); 
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    } else {
                        System.err.println("Échec final après " + maxTentatives + " tentatives.");
                    }
               
        	   	}
           }
       }
       return idsDates;
   }
   
   public static void closeExcel() {
	    try {
	        
	        ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", "excel.exe", "/T");
	        
	        
	        Process process = pb.start();
	        int exitCode = process.waitFor();

	        if (exitCode == 0) {
	            System.out.println("Excel a été fermé de force.");
	        } else {
	            System.out.println("Excel n'était probablement pas ouvert (Code : " + exitCode + ").");
	        }
	    } catch (Exception ex) {
	        System.err.println("Erreur lors de la fermeture d'Excel : " + ex.getMessage());
	    }
	}

 
   public void getNotRegisteredId() {
	   this.main.showPeriodeChoser();
	   if (this.main.getIsOperativePeriodeSet()) {
		   final File file = this.main.chooseFile("xls");
		   Thread thread = new Thread() {
	 	       public void run() {
	 	        	ArrayList<String[]> idsDates= readFromXls(file);
	 	            String[] textSplit = idsDates.get(0);
	 	            String[] dateSplit = idsDates.get(1);
	 	            
	 	            
	 	            ArrayList<ArrayList<String>> notF = AppHomeController.this.verifyIfRegistered(textSplit, dateSplit, AppHomeController.this.main.getOperativePeriode());
	 	                  
	 	            if (notF.size() > 0) {
	 	               String filename = Tools.creerDocument(AppHomeController.this.main.getPseudo(), "RAPPORTS", "notfoundids");
	 	                     
	 	               Document doc = new Document();
	 	               Section section = doc.addSection();
	 	               section.getPageSetup().setOrientation(PageOrientation.Landscape);
	 	               section.addColumn(100, 20);
	 	               section.addColumn(100, 20);
	 	               section.addColumn(100, 20);
	 	                              
	 	               Paragraph premier = section.addParagraph();
	 	               premier.appendText("IDS PRESENTS SUR LA LISTE DU SIS - ADMIN NON ENREGISTRE DANS OWEX");
	 	               premier.appendBreak(BreakType.Line_Break);
	 	               premier.appendBreak(BreakType.Line_Break);

	 	               for(int i = 0; i < notF.size(); ++i) {
	 	                  String var10001 = (String)((ArrayList<String>)notF.get(i)).get(0);
	 	                  premier.appendText(var10001 + "    |     " + (String)((ArrayList<String>)notF.get(i)).get(1));
	 	                  premier.appendBreak(BreakType.Line_Break);
	 	               }

	 	               doc.saveToFile(filename, FileFormat.Docx_2013);
	 	            }
	 	                  
	 	            Platform.runLater(() -> {
	 	               AppHomeController.this.removeIndicator("Le rapport a été créé avec succès!");
	 	            });
	 	                   
	 	         } //run

	 	      }; //thread

	 	      this.showIndicator();
	 	      thread.start();
	      }
	}

   public ArrayList<ArrayList<String>> verifyIfRegistered(String[] ids, String[] dates, String date) {
      ArrayList<ArrayList<String>> notFoundIds = new ArrayList<ArrayList<String>>();
      ArrayList<String> nfIds= new ArrayList<String>();
      
      Connection conn = Tools.getConnected();

      for(int i = 0; i < ids.length; ++i) {
         String sql = "SELECT COUNT(id) as totalId from register WHERE patientId='" + ids[i] + "' AND monthYear='" + date + "'";

         try {
            Statement stm = conn.createStatement();
            ResultSet rst = stm.executeQuery(sql);
            rst.next();
            int idNumber = rst.getInt("totalId");
            if (idNumber == 0) {
            	if(!nfIds.contains(ids[i])) {
            		ArrayList<String> entree = new ArrayList<String>();
            		String id=ids[i];
            		
                    entree.add(this.allongerId(id, this.getLongestIdLength(ids)));
                    entree.add(dates[i]);
                    notFoundIds.add(entree);
                    nfIds.add(ids[i]);
            	}
            }
         } catch (SQLException var12) {
            var12.printStackTrace();
         }
      }

      return notFoundIds;
   }

   @FXML
   public void creerNouveauClient() {
      this.main.showServerClientAdder();
   }

   public void populateIolDirectory() {
      this.fileTree.setVisible(true);
      this.racine = new TreeItem<String>();
      File f = new File(this.main.getCreancesPath());
      if (f.isDirectory()) {
         TreeItem<String> lecteur = new TreeItem<String>(f.getName(), new ImageView(this.folder));
         File[] enfants = f.listFiles();
         Arrays.sort(enfants, Comparator.comparingLong(File::lastModified).reversed());
         if (enfants == null) {
            this.racine.getChildren().add(lecteur);
         } else {
            File[] var7 = enfants;
            int var6 = enfants.length;

            for(int var5 = 0; var5 < var6; ++var5) {
               File fil = var7[var5];
               TreeItem<String> petit = new TreeItem<String>(fil.getName());
               if (fil.isDirectory()) {
                  lecteur.setGraphic(new ImageView(this.folder));
                  lecteur.getChildren().add(this.findEnfants(fil, petit));
               } else {
                  lecteur.setGraphic(new ImageView(this.fileGraphic));
                  lecteur.getChildren().add(petit);
               }
            }

            lecteur.setExpanded(true);
            this.racine.getChildren().add(lecteur);
         }
      }

      this.racine.setExpanded(true);
      this.fileTree.setRoot(this.racine);
      this.fileTree.setShowRoot(false);
   }

   public TreeItem<String> findEnfants(File file, TreeItem<String> node) {
      node.setGraphic(new ImageView(this.folder));
      File[] childs = file.listFiles();
      Arrays.sort(childs, Comparator.comparingLong(File::lastModified).reversed());
      if (childs != null) {
         File[] var7 = childs;
         int var6 = childs.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            File f = var7[var5];
            TreeItem<String> ppetit = new TreeItem<String>(f.getName());
            if (f.isDirectory()) {
               ppetit.setGraphic(new ImageView(this.folder));
               node.getChildren().add(this.findEnfants(f, ppetit));
            } else {
               ppetit.setGraphic(new ImageView(this.fileGraphic));
               node.getChildren().add(ppetit);
            }
         }
      }

      return node;
   }

   @FXML
   public void addUser() {
      this.main.showUserAdder();
   }

   @FXML
   public void deleteUser() {
      this.main.showUserDeleter();
   }

   @FXML
   public void addDrug() {
      this.main.showDrugAdder();
   }

   @FXML
   public void prescribeDrug() {
      this.main.showDrugPrescriber();
   }

   @FXML
   public void deleteDrug() {
      this.main.showDrugChoser();
   }

   @FXML
   public void createDrugsReports() {
      this.main.showPrescriptionPeriodeChoser();
      if (this.main.getIsOperativePeriodeSet()) {
         this.showIndicator();
         Thread thread = new Thread() {
            public void run() {
               String sql = "SELECT DISTINCT delegue FROM drugs";
               ArrayList<String> delegues = new ArrayList<String>();
               Connection conn = Tools.getConnected();

               try {
                  Statement stm = conn.createStatement();
                  ResultSet rst = stm.executeQuery(sql);

                  while(rst.next()) {
                     delegues.add(rst.getString("delegue"));
                  }

                  rst.close();

                  for(int i = 0; i < delegues.size(); ++i) {
                     String del = (String)delegues.get(i);
                     ResultSet rstm = stm.executeQuery("SELECT drug FROM drugs WHERE delegue='" + del + "'");
                     System.out.println(del + "\n*********************************");
                     ArrayList<ArrayList<String>> drug_total = new ArrayList<ArrayList<String>>();
                     ArrayList<String> drugs = new ArrayList<String>();

                     while(rstm.next()) {
                        drugs.add(rstm.getString("drug"));
                     }

                     rstm.close();

                     for(int j = 0; j < drugs.size(); ++j) {
                        String var10001 = (String)drugs.get(j);
                        ResultSet rs = stm.executeQuery("SELECT COUNT(drug) AS total FROM prescription WHERE drug LIKE '%" + var10001 + "%' AND prescriptionmonth='" + AppHomeController.this.main.getOperativePeriode() + "'");
                        rs.next();
                        ArrayList<String> dt = new ArrayList<String>();
                        dt.add((String)drugs.get(j));
                        dt.add(String.valueOf(rs.getInt("total")));
                        drug_total.add(dt);
                        PrintStream var10000 = System.out;
                        var10001 = (String)drugs.get(j);
                        var10000.println(var10001 + "   |   " + rs.getInt("total") + "\n----------------------------------");
                     }

                     System.out.println("\n\n");
                     AppHomeController.this.createOneDelegueRapport(del, drug_total);
                  }

                  Platform.runLater(() -> {
                     AppHomeController.this.removeIndicator("Rapports des delegues créés avec succès");
                  });
               } catch (SQLException var14) {
                  var14.printStackTrace();
               }

            }
         };
         thread.start();
      }

   }
   
   @FXML
   public void showHelp() {
	   this.main.showHelp();
   }
   
   @FXML
   public void showFundusProtocol() {
	   this.main.showFundusProtocol();
   }

   public void createOneDelegueRapport(String del, ArrayList<ArrayList<String>> drug_total) {
      String var10000 = del.toUpperCase();
      String titre = "RESUME DE LA PRESCRIPTION DES MEDICAMENTS DE " + var10000 + " POUR LA PERIODE DE " + this.main.getOperativePeriode();
      Document doc = this.createDocumentAndHeaders("", titre);
      String[] headers = new String[]{"No", "Nom du médicament", "Nombre de prescription"};
      Section section = doc.getSections().get(0);
      Table table = section.addTable();
      table.resetCells(drug_total.size() + 1, 3);
      table.autoFit(AutoFitBehaviorType.Auto_Fit_To_Window);
      TableRow headerRow = table.getRows().get(0);
      headerRow.isHeader(true);
      headerRow.setHeight(20.0F);
      headerRow.setHeightType(TableRowHeightType.At_Least);

      TextRange t;
      int index;
      for(index = 0; index < headers.length; ++index) {
         Paragraph pcell = headerRow.getCells().get(index).addParagraph();
         headerRow.getCells().get(index).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
         headerRow.getCells().get(index).setCellWidthType(CellWidthType.Auto);
         t = pcell.appendText(headers[index]);
         t.getCharacterFormat().setBold(true);
         t.getCharacterFormat().setFontName("Times New Roman");
         t.getCharacterFormat().setFontSize(12.0F);
      }

      index = 1;

      for(int j = 0; j < drug_total.size(); ++j) {
         ArrayList<String> entry = (ArrayList<String>)drug_total.get(j);
         entry.add(0, String.valueOf(index));

         for(int m = 0; m < 3; ++m) {
            t = table.getRows().get(j + 1).getCells().get(m).addParagraph().appendText((String)entry.get(m));
            table.getRows().get(j + 1).setHeight(20.0F);
            table.getRows().get(j + 1).setHeightType(TableRowHeightType.At_Least);
            table.getRows().get(j + 1).getCells().get(m).setCellWidthType(CellWidthType.Auto);
            t.getCharacterFormat().setFontName("Times New Roman");
            t.getCharacterFormat().setFontSize(12.0F);
         }

         ++index;
      }

      String filename = Tools.creerDocument(del, "RAPPORTS");
      this.setMargin(doc, 50.0F);
      doc.saveToFile(filename, FileFormat.Docx_2013);
   }
   
   public int getLongestIdLength(String[] ids) {
	   int length=0;
	   for(String str: ids) {
		   if(str!=null && str.length()>length) {
			   length=str.length();
		   }
	   }
	  
	   return length;
   }
   
   public String allongerId(String id, int length) {
	   if(id.length()<length) {
		   while(id.length()<length) {
			   id+=" ";
		   }
	   }
	   return id;
   }
}
