package app.view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.fields.TextRange;

import app.ExMain;
import app.tools.Tools;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FundusController {

    @FXML
    private RadioButton acquiredT;

    @FXML
    private TextField woolText;

    @FXML
    private RadioButton refPresent;

    @FXML
    private CheckBox vbaseCheck;

    @FXML
    private RadioButton oraN;

    @FXML
    private RadioButton copperLight;

    @FXML
    private RadioButton tHazy;

    @FXML
    private RadioButton vbaseN;

    @FXML
    private RadioButton pDepression;

    @FXML
    private TextField anevrismText;

    @FXML
    private CheckBox cnevusCheck;

    @FXML
    private CheckBox gunnCheck;

    @FXML
    private RadioButton oraY;

    @FXML
    private ComboBox<String> pEye;

    @FXML
    private RadioButton sLarge;

    @FXML
    private Tab identification;

    @FXML
    private RadioButton pvdPartial;

    @FXML
    private CheckBox cupAssymetry;

    @FXML
    private RadioButton iCellophane;

    @FXML
    private CheckBox bonnetCheck;

    @FXML
    private RadioButton vbaseY;

    @FXML
    private RadioButton pElevation;

    @FXML
    private TextField cvenusText;

    @FXML
    private TextField avRatio;

    @FXML
    private CheckBox flameCheck;

    @FXML
    private RadioButton detachment;

    @FXML
    private CheckBox woolCheck;

    @FXML
    private TextField emboliText;

    @FXML
    private RadioButton retinoschisis;

    @FXML
    private CheckBox dotCheck;

    @FXML
    private TextField infectiousText;

    @FXML
    private CheckBox aNarrowing;

    @FXML
    private RadioButton mHazy;

    @FXML
    private RadioButton Tnormal;

    @FXML
    private TextField hRatio;

    @FXML
    private CheckBox sheathCheck;

    @FXML
    private CheckBox salusCheck;

    @FXML
    private RadioButton rHyperemia;

    @FXML
    private CheckBox neoCheck;

    @FXML
    private RadioButton pvdComplete;

    @FXML
    private RadioButton cPale;

    @FXML
    private TextField flameText;

    @FXML
    private VBox maculaChecks;

    @FXML
    private TextField vRatio;

    @FXML
    private RadioButton aHole;

    @FXML
    private RadioButton tuft;

    @FXML
    private RadioButton refAbsent;

    @FXML
    private RadioButton mSharp;

    @FXML
    private CheckBox riskCheck;

    @FXML
    private RadioButton pFlat;

    @FXML
    private CheckBox oraCheck;

    @FXML
    private CheckBox vDilation;

    @FXML
    private RadioButton pvdAbsent;

    @FXML
    private RadioButton silverLight;

    @FXML
    private RadioButton mElevated;

    @FXML
    private CheckBox boatCheck;

    @FXML
    private CheckBox pNeoCheck;

    @FXML
    private RadioButton iSatin;

    @FXML
    private RadioButton pvdTraction;

    @FXML
    private CheckBox irmaCheck;

    @FXML
    private RadioButton rPale;

    @FXML
    private TextField pName, lastName;

    @FXML
    private CheckBox beadingCheck;

    @FXML
    private RadioButton tear;

    @FXML
    private RadioButton sSmall;

    @FXML
    private Tab retina;

    @FXML
    private TextField pAge;

    @FXML
    private RadioButton nLight;

    @FXML
    private Tab periphery;

    @FXML
    private RadioButton cNormal;

    @FXML
    private RadioButton sNormal;

    @FXML
    private RadioButton congenitalT;

    @FXML
    private CheckBox benignCheck;

    @FXML
    private RadioButton rPink;

    @FXML
    private CheckBox acuteCheck;

    @FXML
    private Tab onh;

    @FXML
    private CheckBox anevrismCheck;

    @FXML
    private RadioButton cTigroid;

    @FXML
    private RadioButton pavingstone;

    @FXML
    private TextField neoText;

    @FXML
    private ComboBox<String> pGender, fundusIndication;

    @FXML
    private TextField dotText;

    @FXML
    private CheckBox emboliCheck;

    @FXML
    private CheckBox tortCheck;

    @FXML
    private CheckBox infectiousCheck;

    @FXML
    private RadioButton wwop;

    @FXML
    private RadioButton lattice;

    @FXML
    private VBox discOtherSigns, retinaBox, vesselBox, pBox;

    @FXML
    private Tab macula;

    @FXML
    private Tab vessels;
    
    @FXML
    private Button changeEyeBtn, saveBtn , onhBtn, finishBtn, maculaBtn, retinaBtn, vesselsBtn, peripheryBtn;
    
    @FXML
    private GridPane vascGrid, embGrid; 
    
    @FXML
    private ToggleGroup discSize, margins, rimColor, freflex, rprofile, ireflex, pvd, transparency, bcolor, arterialR,
    serrata, vitreousb;
    
    @FXML
    private TabPane globTabs;
    
    @FXML
    private CheckBox pavCheck, wwopCheck, latticeCheck, tuftCheck, schisisCheck, tearCheck, holeCheck, detCheck;
    
    @FXML
    private TreeView<String> files;
    
    @FXML
    private BorderPane borderP;
    
    @FXML
    private VBox generalBox;
    
    @FXML
    private StackPane generalPane;
    
    private BooleanProperty isPatientInfoSet= new SimpleBooleanProperty();
    
    //variable pour stocker le resultat final ---length 2 au max si l oeil atete change -- 
    private ArrayList<ArrayList<ArrayList<String>>> fundusResults= new ArrayList<ArrayList<ArrayList<String>>>();
    
    //suivre le changement de l'oeil - on ne peut que changer une seule fois 
    private BooleanProperty isEyeChanged= new SimpleBooleanProperty();
    //boolean controlling the closing and eye change ---- 
    private BooleanProperty isBothEyes= new SimpleBooleanProperty();
    
    private BooleanProperty isOnhCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isMaculaCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isRetinaCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isVesselsCompleted= new SimpleBooleanProperty();
    
    private BooleanProperty isPeripheryCompleted= new SimpleBooleanProperty();
    
    private ArrayList<ArrayList<String>> activeEye= new ArrayList<ArrayList<String>>();
    
    private ExMain main;
    
    private Stage stage;
    
    private boolean allowFinish=false, allowOneEye=false, allowEyeIncomplet=false, allowQuit=false, isFileDeleted=false;
    
    private String filename="";
    
    private int numerotation=0;
    
    private Image folder = new Image(ExMain.class.getResourceAsStream("images/closedFolder.png"));
    
    private Image fileGraphic = new Image(ExMain.class.getResourceAsStream("images/file.png"));
    
    private VBox indicatorBox= new VBox();
    
    
    
    public String getFilename() {
    	return this.filename; 
    }
    
    public void setMain(ExMain m) {
    	this.main=m;
    	populateTreeView(files, main.getCreancesPath()+"/PROTOCOLS");
    }
    public void setStage(Stage stg) {
    	this.stage=stg;
    	stage.setOnCloseRequest((e)->{
    		if(this.isPatientInfoSet.get()) {
    			finishCompletion(e);
    		}
    	});
    }
    
    public void initialize() {
    	//------------------------Info tabs -------------------------
    	//initialisation des valeurs
    	isPatientInfoSet.set(false);
    	isEyeChanged.set(false);
    	this.isBothEyes.set(false);
    	
    	bindTabs(); //les 4 tabs ne peuvent souvrir que si les infos de patients sont deja conserve
    	Node[] pInfos= {pName, lastName, pAge, saveBtn};
    	Tools.addNavigation(pInfos);
    	
    	//initialisons les genres et les yeux 
    	pEye.getItems().add("OD");
    	pEye.getItems().add("OS");
    	pEye.getItems().add("OU");
    	pEye.getSelectionModel().select("OD");
    	
    	pGender.getItems().add("M");
    	pGender.getItems().add("F");
    	pGender.getSelectionModel().select("M");
    	
    	pEye.valueProperty().addListener((obs, old, nv) -> {
            if (nv.equals("OU")) {
               isBothEyes.set(true);
            }else {
            	isBothEyes.set(false);
            }

        });
    	
    	this.fundusIndication.getItems().addAll(List.of("Exploration","Diabetes", "Arterial Hypertension", "Intracranial Hypertension"));
    	fundusIndication.getSelectionModel().select(0);
    	
    	this.initializeToggle();
    	
    	
    	//sur tab retina se rassurer k on a cocher le checkbox afin de pouvoir indiquer l emplacement de la lesion 
    	setGridBindings(embGrid);
    	setGridBindings(vascGrid);
    	
    	periphery.setOnSelectionChanged((e)->{
    		if(periphery.isSelected()) {
    			if(this.isEyeChanged.get()) {
    				this.peripheryBtn.setText("Save and Finish");
    			}else {
    				this.peripheryBtn.setText("Save and Change eye");
    			}
    		}
    	});
    	
    	  files.setOnMouseClicked((event) -> {
    	         if (event.getClickCount() == 2) {
    	            TreeItem<String> item = (TreeItem<String>)this.files.getSelectionModel().getSelectedItem();
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
    	  
    	 ArrayList<CheckBox> checks= new ArrayList<CheckBox>();
    	 CheckBox[] ck= {cnevusCheck, gunnCheck, cupAssymetry, bonnetCheck, flameCheck, woolCheck, dotCheck, aNarrowing, sheathCheck, salusCheck, neoCheck, vDilation, boatCheck, pNeoCheck, irmaCheck, beadingCheck, anevrismCheck, emboliCheck, tortCheck, infectiousCheck, pavCheck, wwopCheck, latticeCheck, tuftCheck, schisisCheck, tearCheck, holeCheck, detCheck};
    	 
    	 int i=0;
    	 for (CheckBox cb : ck) {
    	        if (cb == null) {
    	            System.err.println("ERREUR : Un CheckBox est null dans le FXML !"+i);
    	        } else {
    	            checks.add(cb);
    	        }
    	        
    	        i++;
    	  }
    	 
    	 checks.addAll(getChecksFromBox(maculaChecks));
    	 checks.addAll(getChecksFromBox(discOtherSigns));
    	  for(CheckBox check: checks) {
    		  if(check!=null) {
        		  check.setOnMouseClicked((e)->{
        			  Tooltip tool=check.getTooltip();
        			  String tip=(tool==null)?"":tool.getText();
        			  
        			  if(e.getClickCount()==2) {
        				 try {
        					 URL dossierURL = getClass().getResource("/app/fundusImage");
        					 String dossierPath = Paths.get(dossierURL.toURI()).toFile().getAbsolutePath();
        					 
        					 List<String> list=this.chercherFichiers(dossierPath, this.chooseCheckPhoto(check.getText().trim()));
        					 showGallery(list, check.getText().trim(), tip);
        					 
    					} catch (IOException | URISyntaxException e1) {
    						// TODO Auto-generated catch block
    						e1.printStackTrace();
    					}
        			  }
        			  
        		  }); 
    		  }
    		  
    	  }
    	
    }
   
    public void focusPatientName() {
    	pName.requestFocus();
    }
    
    //-------------Methodes lies au fxml ---------------------------------------------------------------
    
    @FXML
    public void savePatientInfos() {
    	//verifier si le nom - est complete ---
    	if(this.isPatientInfoCompleted()) {
    		ArrayList<ArrayList<String>> oneEye= new ArrayList<ArrayList<String>>();
    		this.fundusResults.add(oneEye);
    		
    		//on verifie si l oeil a tet change pr savoir ou ;ettre les resultas
    		if(this.isEyeChanged.get()) {
    			this.activeEye=this.fundusResults.get(1);
    		}else {
    			this.activeEye=this.fundusResults.get(0);
    		}
    		
    		//save patient infos 
    		ArrayList<String> pinfo= new ArrayList<String>();
    		pinfo.add(pName.getText().trim());
    		pinfo.add(lastName.getText().trim());
    		pinfo.add(pAge.getText().trim());
    		pinfo.add(fundusIndication.getSelectionModel().getSelectedItem().trim());
    		pinfo.add(pGender.getSelectionModel().getSelectedItem().trim());
    		pinfo.add(pEye.getSelectionModel().getSelectedItem().trim());
    		
    		this.activeEye.add(pinfo);
    		
    		String[] toReplace= {"@names@","@lastname@", "@age@", "@indication@", "@sexe@", "@eye@"};
    		String[] replaceWith= pinfo.toArray(new String[0]);
    		filename = Tools.creerDocument(pName.getText().trim()+"_"+lastName.getText().trim()+"_"+pEye.getSelectionModel().getSelectedItem().trim(), "PROTOCOLS");
    		
    		Thread th= new Thread(()->{
    			registerPart("","",toReplace, replaceWith, true);
    			Platform.runLater(()->{
    				removeIndicator("Patienyt info successfully registered");
    				this.isPatientInfoSet.set(true);
    	    		globTabs.getSelectionModel().select(onh);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		showIndicator();
    	}else {
    		System.out.println("Complete all info");
    	}
    	//enregistrer 
    }
    
   
    
    
    
    @FXML
    public void saveOnhResults() {
    	ToggleGroup[] onhToggle={discSize, margins, rimColor};
    	if(verifyToggle(onhToggle) && !vRatio.getText().isBlank() && !hRatio.getText().isBlank()) {
    		onhBtn.setDisable(true);
    		ArrayList<String> onhResults= new ArrayList<String>();
    		
    		onhResults.addAll(getSelectedValues(onhToggle));
    		
    		onhResults.add(vRatio.getText().trim());
    		onhResults.add(hRatio.getText().trim());
    		onhResults.add((cupAssymetry.isSelected())?"Asymétrie entre OD/OS >0.2 ":"Pas d'asymétrie significative de la ratio C/D entre OD et OS");
    		
    		onhResults.add(getSelectedCkecks(discOtherSigns));
    		
    		this.activeEye.add(onhResults);
    		
    		String[] toReplace= {"@size@", "@margin@", "@rim@", "@cdv@", "@cdh@", "@asy@" ,"@signesonh@"};
    		String[] replaceWith= onhResults.toArray(new String[0]);
    		String onh="Tête du nerf optique de taille @size@, à bords : @margin@. La couleur de l’anneau neuro-rétinien est @rim@. L’excavation est de @cdv@, C/D vertical et @cdh@, C/D horizontal ; @asy@.\n"
    				+ "Autres signes associés retrouvés : @signesonh@\n";
    		String titre=numerotation+". ONH";
    		
    		Thread th= new Thread(()->{
    			registerPart(titre,onh,toReplace, replaceWith, false);
    			Platform.runLater(()->{
    				removeIndicator("ONH info successfully registered");
    	    		globTabs.getSelectionModel().select(macula);
    	    		isOnhCompleted.set(true);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		showIndicator();
    	}else {
    		System.out.println("Complete disc size, margins, rim color and C/D ratio It's mandatory");
    	}
    }
    
    @FXML
    public void saveMaculaResults() {
    	ToggleGroup[] mToggle= {freflex, rprofile, ireflex, pvd};
    	ArrayList<String> results= new ArrayList<String>();
    	if(this.verifyToggle(mToggle)) {
        	maculaBtn.setDisable(true);
    		results.addAll(getSelectedValues(mToggle));
    		results.add(this.getSelectedCkecks(maculaChecks));
    		
    		activeEye.add(results);
    		
    		String[] toReplace= {"@mreflex@", "@profile@", "@ilm@", "@dvp@", "@lesion@"};
    		String[] replaceWith= results.toArray(new String[0]);
    		String mac="Le reflet maculaire est @mreflex@. Le profil maculaire @profile@ et le reflet de l’ILM est @ilm@.\n"
    				+ "Décollement postérieur du vitré @dvp@.\n"
    				+ "Œdèmes et autres lésions : @lesion@\n";
    		String t=numerotation+". Macula";
    		
    		
    		Thread th= new Thread(()->{
    			registerPart(t,mac,toReplace, replaceWith, false);
    			Platform.runLater(()->{
    				removeIndicator("Macula info successfully registered");
    	    		globTabs.getSelectionModel().select(retina);
    	    		isMaculaCompleted.set(true);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		showIndicator();
    		
    		
    	}else {
    		System.out.println("It's mandatory to complete reflex - profile - ILm and PVD states");
    	}
    }
    
    @FXML
    public void saveRetinaResults() {
    	ToggleGroup[] rToggle= {transparency, bcolor};
    	ArrayList<String> r= new ArrayList<String>();
    	if(this.verifyToggle(rToggle)) {
    		retinaBtn.setDisable(true);
    		r.addAll(this.getSelectedValues(rToggle));
    		r.add(this.getResultsFromGrid(vascGrid));
    		r.add(this.getResultsFromGrid(embGrid));
    		
    		activeEye.add(r);
        	
        	String[] toReplace= {"@fond@", "@color@", "@diabetes@", "@signesretina@"};
    		String[] replaceWith= r.toArray(new String[0]);
    		String ret="Le fond du fond d’œil est @fond@, de couleur @color@.\n"
    				+ "Signes vasculaires diabétiques ou ischémiques : @diabetes@\n"
    				+ "Autres signes : @signesretina@\n";
    		
    		String t=numerotation+". Rétine";
    		
    		Thread th= new Thread(()->{
    			registerPart(t,ret,toReplace, replaceWith, false);
    			Platform.runLater(()->{
    				removeIndicator("Retina info successfully registered");
    				globTabs.getSelectionModel().select(vessels);
    				isRetinaCompleted.set(true);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		showIndicator();
    	}
    }
    
    @FXML
    public void saveVesselsResults() {
    	ToggleGroup[] vToggle= {arterialR};
    	ArrayList<String> r= new ArrayList<String>();
    	if(this.verifyToggle(vToggle) && !this.avRatio.getText().isBlank() ) {
    		vesselsBtn.setDisable(true);
    		r.addAll(this.getSelectedValues(vToggle));
    		r.add(this.avRatio.getText().trim());
    		
    		CheckBox[] av= {aNarrowing, vDilation};
    		CheckBox[] cross= {gunnCheck, salusCheck, bonnetCheck};
    		CheckBox[] other= {tortCheck, sheathCheck};
    		
    		r.add((getSelectedCkecks(av).equals("Aucun"))?"":"avec "+getSelectedCkecks(av));
    		r.add(this.getSelectedCkecks(cross));
    		r.add(this.getSelectedCkecks(other));
    		
    		
    		activeEye.add(r);
        	
        	String[] toReplace= {"@reflet@","@av@", "@retrecissement@", "@croisement@", "@autres@"};
        	
    		String[] replaceWith= r.toArray(new String[0]);
    		String vess="Le ratio A/V est @av@ @retrecissement@. Le reflet artériel est @reflet@\n"
    				+ "Signes de croisement A/V : @croisement@\n"
    				+ "Autres signes  : @autres@\n";
    		
    		String t=numerotation+". Vaisseaux rétiniens";
    		Thread th= new Thread(()->{
    			registerPart(t,vess,toReplace, replaceWith, false);
    			Platform.runLater(()->{
    				removeIndicator("Vessels info successfully registered");
    				globTabs.getSelectionModel().select(periphery);
    				this.isVesselsCompleted.set(true);
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		showIndicator();
    	}
    	
    }
    
    @FXML
    public void savePeripheryResults() {
    	ToggleGroup[] pToggle= {serrata, vitreousb};
    	ArrayList<String> r= new ArrayList<String>();
    	if(this.verifyToggle(pToggle)) {
    		peripheryBtn.setDisable(true);
    		r.addAll(this.getSelectedValues(pToggle));
    		
    		
    		
    		CheckBox[] benign={pavCheck, wwopCheck};
    		CheckBox[] risk= {latticeCheck, tuftCheck, schisisCheck};
    		CheckBox[] acute= {tearCheck, holeCheck, detCheck, pNeoCheck};
    		
    		r.add(this.getSelectedCkecks(benign));
    		r.add(this.getSelectedCkecks(risk));
    		r.add(this.getSelectedCkecks(acute));
    		

        	activeEye.add(r);
        	
        	String[] toReplace= {"@ora@", "@base@", "@benign@", "@high@", "@acute@"};
    		String[] replaceWith= r.toArray(new String[0]);
    		String per="L’ora serrata est @ora@ et la base du vitré est @base@.\n"
    				+ "Dégénérescences courantes (souvent sans danger) : @benign@\n"
    				+ "Zones de fragilité (à surveiller) : @high@\n"
    				+ "Lésions aigue : @acute@\n";
    		String t=numerotation+". Périphérie";
    		Thread th= new Thread(()->{
    			registerPart(t,per,toReplace, replaceWith, false);
    			Platform.runLater(()->{
    				removeIndicator("Periphery info successfully registered");
    				this.isPeripheryCompleted.set(true);
    				
    				if(!isEyeChanged.get()) {
    					this.changeEyeBtn.fire();
    				}else {
    					this.finishBtn.fire();
    				}
    			});
    		});
    		
    		th.setDaemon(true);
    		th.start();
    		
    		showIndicator();
    	}
    }
    
    @FXML
    public void changeEye() {
    	if(isPatientInfoSet.get()) {//on verifie si les infos etaient deja enregistre ds ce csd le vqleur est true
    		if(this.verifyTabsCompletion() || this.allowEyeIncomplet) { 
    			getConclusion();
    			//la condition cihaut verifie si les tabs sont tous completes pour l'oeil actif
    			//ou si on a decide de continuer sans completer l'autre oeil lors de la fermeture
    			int i=pEye.getSelectionModel().getSelectedIndex();
        		if(i==0) {
        			i++;
        		}else {
        			i--;
        		}
        		
        		pEye.getSelectionModel().select(i);
        		isEyeChanged.set(true);
        		savePatientInfos();
        		
        		//on efface les donnees ds les tabs / reinitiqlise les boutons 
        		reset(false);
        		initializeToggle();
        		setPermittersFalse();
    		}else {
    			String sms=this.getNotCompletedMessage();
    			this.showMessage(sms, sms, ()->this.changeEye(), ()->stopEyeChange(), false);
    		}
    		
    	}else {
    		System.out.println("Infos not yet registered - change it manually");
    	}
    }
    
    @FXML
    public void finishCompletionBtn() {
    	if(verifyTabsCompletion() || allowFinish) {//verifie si ts les tqbs de l'oeil on ete rempli --si oui
			if(isEyeChanged.get() || allowOneEye || isBothEyes.get()) {
				getConclusion();
				reset(true);  //reinitialiser 
    		}else {
    			String sms="Only one eye is compeleted.\nDo you want to go on with one eye ?";
    			this.showMessage(sms, "eye", ()->this.finishCompletionBtn(), ()->this.changeEye(),false);
    			//on informe k l autre eyen est pqs rempli et on demqnde de remplir ou lq permision de continuer
    		}
    	}else if(allowQuit){
    		
    		reset(true);
    		fundusResults.clear();
    		setPermittersFalse();
    		initializeToggle();
        	globTabs.getSelectionModel().select(0);
    	}else {
    		String sms=this.getNotCompletedMessage();
   		 	this.showMessage(sms, "tabs", ()->this.finishCompletionBtn(), ()->this.stopCompletion(),true);
    	}
    }
    
    
    
   
    
    // *** autres methodes de la classe-----------------------------------------------------------------------
    
    
    public void bindTabs() {
    	Tab[] tabs= {onh, macula, retina, vessels, periphery};
    	Node[] pInfos= {pName, fundusIndication, pGender, pAge, pEye};
    	
    	for (Tab tab:tabs){
    		tab.disableProperty().bind(isPatientInfoSet.not());
    	}
    	
    	for(Node n : pInfos) {
    		n.disableProperty().bind(isPatientInfoSet);
    	}
    	
    	changeEyeBtn.disableProperty().bind(isEyeChanged.or(isPatientInfoSet.not().or(isBothEyes)));
    	saveBtn.disableProperty().bind(isPatientInfoSet);
    	finishBtn.disableProperty().bind(isPatientInfoSet.not());
    }
    
    
    public void finishCompletion(Event e) {
    	//TODO -verifie si l oeil en cours d enregistrement est bien enregistre
    	//si oui on finit le stage sinon propose de completer en vide ou de continuer ou d'annuler l enreg
    	if(!verifyTabsCompletion() && !allowFinish && !allowQuit) {  
    		String sms=this.getNotCompletedMessage();
   		 	this.showMessage(sms, "tabs", ()->this.finishCompletion(e), ()->this.stopCompletion(e),true);	
    	}
    }
   
    
    
    public void showMessage(String message, String type,  Runnable onYes, Runnable onNo, boolean bool) {
    	 
		 Alert alert = new Alert(AlertType.CONFIRMATION);
         alert.setHeaderText(message);
         alert.setTitle("Confirm action");
         ButtonType da = new ButtonType("Yes");
         ButtonType net = new ButtonType("No");
         ButtonType udalit = new ButtonType("Quit");
        // Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        // stage.getIcons().add(new Image(IolMain.class.getResourceAsStream("images/logot.png")));
         alert.getButtonTypes().clear();
         alert.getButtonTypes().add(da);
         alert.getButtonTypes().add(net);
         
         if(bool) {
        	 alert.getButtonTypes().add(udalit);
         }
         
         
         Optional<ButtonType> option = alert.showAndWait();
         if (option.get() == da) {
        	registerEmptyTabs(onYes, type);  //on complete les tabs vide et on donne les permissions
         }else if(option.get()==net) {  //on appuie sur non ---
        	 onNo.run();
         }else if(option.get()==udalit) { // on appuie sur Quit 
        	 try {
        		 closeWord();
        		 
                 boolean deleted=Files.deleteIfExists(Paths.get(filename));
                 
                 if (deleted) {
                     main.getAppHomeController().populateIolDirectory();
                 } 
             } catch (IOException e) {
                e.printStackTrace();
             }
        	 allowQuit=true;
        	 onYes.run();
         }
    }
    
    
	public void registerEmptyTabs(Runnable onYes, String type) {
		
		switch(type) {
		case "tabs":
			this.allowFinish=true;
			break;
		case "eye":
			this.allowOneEye=true;
			break;
		default:
			this.allowEyeIncomplet=true;
			break;
		}
    	
    	if(this.isAnyTabCompleted()) {
			onYes.run();
    	}else {
    		 try {
        		 closeWord();
        		 
                 boolean deleted=Files.deleteIfExists(Paths.get(filename));
                 isFileDeleted=true;
                 
                 if (deleted) {
                     main.getAppHomeController().populateIolDirectory();
                 } 
             } catch (IOException e) {
                e.printStackTrace();
             }
    		 
    		 onYes.run();
    	}
	}
    
    public ArrayList<String[][]> getEmptyTabsReplacements(){
    	BooleanProperty[] bools= {isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isVesselsCompleted, isPeripheryCompleted};
    	String[] onhr= {"@size@", "@margin@", "@rim@", "@cdv@", "@cdh@", "@asy@" ,"@signesonh@"};
    	String[] macular= {"@mreflex@", "@profile@", "@ilm@", "@dvp@", "@lesion@"};
    	String[] retinar= {"@fond@", "@color@", "@diabetes@", "@signesretina@"};
    	String[] vesselsr= {"@reflet@","@av@", "@retrecissement@", "@croisement@", "@autres@"};
    	String[] peripheryr= {"@ora@", "@base@", "@benign@", "@high@", "@acute@"};
    	
    	String[][] toReplaces= {onhr, macular, retinar, vesselsr, peripheryr};
    	
    	int index=0;
    	
    	ArrayList<String[][]> emptyTabs= new ArrayList<String[][]>();
    	
    	for(BooleanProperty b: bools) {
    		if(!b.get()) {
    			String[] toReplace=toReplaces[index];
    			String[] replaceW= new String[toReplace.length];
    			
    			for(int i=0; i<toReplace.length; i++) {
    				replaceW[i]="";
    			}
    			
    			String[][] etab= {toReplace, replaceW};
    			
    			print_r(etab, "");
    			emptyTabs.add(etab);
    			b.set(true);
    		}
    		
    		index++;
    	}
    	
    	return emptyTabs; 
    }
    
    public boolean isAnyTabCompleted() {
    	BooleanProperty[] bools= {isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isVesselsCompleted, isPeripheryCompleted};
    	
    	for(BooleanProperty b: bools) {
    		if(b.get()) {
    			return true;
    		}
    	}
    	
    	return false;
    	
    }
    
    
    
	public void stopCompletion() {
    	
    }
	
	public void stopCompletion(Event e) {
		main.getStagePrincipale().setIconified(false);
    	e.consume();
    }

    public void stopEyeChange() {
    	
    }
    
    public boolean isPatientInfoCompleted() { //verifie si les infos du patient sont complet
    	return (!pName.getText().isBlank() && !pGender.getSelectionModel().getSelectedItem().isBlank() &&
    			!pEye.getSelectionModel().getSelectedItem().isBlank() &&!pAge.getText().isBlank() &&
    			!lastName.getText().isBlank()); 
    }
    
    
    public static void print_r(Object obj, String indent) {
        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            System.out.println(indent + "[");
            for (Object item : list) {
                print_r(item, indent + "    ");
            }
            System.out.println(indent + "]");
        } else {
            System.out.println(indent + obj);
        }
    }
    
    public String getSelectedCkecks(VBox myVBox) {
      String selectedItems ="";
      String [] orig= {"Defects of RFNL", "Undermining of vessels", "Baring of vessels", "Nasal displacement", "Bayoneting sign", "Laminar dot sign", "Beta zone", "Alpha zone", "Disc hemorrhage", "ISNT Rule violation", "Notching", "Neovascularisation on the disc","Cystoid spaces", "Serous Retinal Detachment", "Pigment Epithelial Detachment", "Hard drusens", "Soft drusens", "Cuticular drusens", "Hard Exudates", "Geographic atrophy", "Pigment clumping", "Macular Nevus", "Subretinal Hemorrhage", "Full thickness macular hole", "Lamellar Hole"};
  	String[] trad= {"Déficits de la couche des fibres nerveuses rétiniennes", "Érosion des vaisseaux", "Mise à nu des vaisseaux", "Déplacement nasal", "Signe de la baïonnette", "Signe des pores de la lame criblée", "Zone bêta", "Zone alpha", "Hémorragie du disque", "Violation de la règle ISNT", "Encoche", "Néovascularisation sur le disque", "Espaces cystoïdes", "Décollement séreux rétinien", "Décollement de l'épithélium pigmentaire", "Drusens durs", "Drusens mous", "Drusens cuticulaires", "Exsudats durs", "Atrophie géographique", "Amas de pigments", "Naevus maculaire", "Hémorragie sous-rétinienne", "Trou maculaire de pleine épaisseur", "Trou lamellaire"};
        for (Node node : myVBox.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                if (cb.isSelected()) {
                	String sel=cb.getText().trim();
                	
                	String traduit= trad[Arrays.asList(orig).indexOf(sel)];
                    selectedItems+=(selectedItems.isBlank())?traduit:" , "+traduit;
                }
            }
        }
        
        if(selectedItems.isBlank()) {
        	selectedItems="Aucun";
        }
        
        return selectedItems;
    }
    
    public ArrayList<CheckBox> getChecksFromBox(VBox box){
    	ArrayList<CheckBox> clist=new ArrayList<CheckBox>();
    	
    	for (Node node : box.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox cb = (CheckBox) node;
                clist.add(cb);
            }
        }
    	
    	return clist;
    }
    
    public String getSelectedCkecks(CheckBox[] checks) {
        String selectedItems ="";
        String[] orig= {"Arterial narrowing", "Venous dilation", "Gunn sign", "Salus sign", "Bonnet sign", "Abnormal tortuosity", "Sheathing", "Pavingstone", "White-without-pressure", "Lattice", "Vitreoretinal tuft", "Retinoschisis", "Tear", "Atrophic hole", "Retinal detachment", "Peripheral neovascularization"};
        String[] trad= {"Rétrécissement artériel", "Dilatation veineuse", "Signe de Gunn", "Signe de Salus", "Signe de Bonnet", "Tortuosité anormale", "Engainement", "Dégénérescence en pavés", "Blanc sans pression", "Lattice", "Touffe vitréo-rétinienne", "Rétinoschisis", "Déchirure", "Trou atrophique", "Décollement de rétine", "Néovascularisation périphérique"};
          for (CheckBox node : checks) {
             if (node.isSelected()) {
            	String sel=node.getText().trim();
            
             	String traduit= trad[Arrays.asList(orig).indexOf(sel)];
                selectedItems+=(selectedItems.isBlank())?traduit:" , "+traduit;
                
             }  
          }
          
          if(selectedItems.isBlank()) {
          	selectedItems="Aucun";
          }
          
          return selectedItems;
      }
    
    
    
    public String getSelectedValue(ToggleGroup t) {
    	if(t.getSelectedToggle()!=null) {
    		RadioButton r=(RadioButton)t.getSelectedToggle();
    		String str=r.getText().trim();
    		String traduit="";
    		
    		switch(str) {
    		case "small":
    			traduit="petit";
    			break;
    		case "Normal":
    			traduit="normal";
    			break;
    			
    		case "Large":
    			traduit="large";
    			break;
    		
    		case "Sharp and well-defined":
    			traduit="nets et bien définis";
    			break;
    			
    		case "Hazy":
    			traduit="flous";
    			break;
    			
    		case "Elevated":
    			traduit="surélevés";
    			break;
    			
    		case "Pink":
    			traduit="Rose";
    			break;
    			
    		case "Pale":
    			traduit="Pâle";
    			break;
    			
    		case "Hyperemia":
    			traduit="Hyperhémie";
    			break;
    			
    		case "Present":
    			traduit="Présent";
    			break;
    			
    		case "Absent":
    			traduit="Absent";
    			break;
    		case "Flat":
    			traduit="Plat";
    			break;
    		case "Elevation":
    			traduit="Élévation";
    			break;
    		case "Depression":
    			traduit="Dépression";
    			break;
    		case "Cellophane metallic glint":
    			traduit="Reflet métallique de cellophane";
    			break;
    			
    		case "Satin-like sheen":
    			traduit="Éclat satiné";
    			break;
    			
    		case "Partial with traction":
    			traduit="Partiel avec traction";
    			break;
    			
    		case "Partial":
    			traduit="Partiel";
    			break;
    			
    		case "Complete":
    			traduit="Complet";
    			break;
    			
    		case "Tigroid":
    			traduit="Tigré";
    			break;
    			
    		case "Copper wiring":
    			traduit="Reflet en fil de cuivre";
    			break;
    			
    		case "Silver wiring":
    			traduit="Reflet en fil d'argent";
    			break;
    			
    		case "Visible 360":
    			traduit="Visible sur 360°";
    			break;
    			
    		case "Limited visibility":
    			traduit="Visibilité limitée";
    			break;
    		case "Abnormal":
    			traduit="Anormal";
    			break;
    			
    		default:
    			traduit=str;
    			break;
    			
    		}
    		
    		return traduit.toLowerCase();
    	}else {
    		return "";
    	}
    }
    
    public ArrayList<String> getSelectedValues(ToggleGroup[] tg){
    	ArrayList<String> values= new ArrayList<String>();
    	for(ToggleGroup t: tg) {
    		values.add(this.getSelectedValue(t));
    	}
    	
    	return values;
    }
    
    public boolean verifyToggle(ToggleGroup [] tg) {
    	boolean selected=true;
    	
    	for(ToggleGroup t:tg) {
    		if(t.getSelectedToggle()==null) {
    			selected=false;
    		}
    	}
    	
    	return selected;
    }
    
    public void unselectToggle(ToggleGroup[] tg) {
    	for(ToggleGroup t:tg) {
    		if(t.getSelectedToggle()!=null) {
    			t.getSelectedToggle().setSelected(false);
    		}
    	}
    }
    
    public void unselectVbox(Parent vb) {
    	 for (Node node : vb.getChildrenUnmodifiable()) {
             if (node instanceof CheckBox) {
                 CheckBox cb = (CheckBox) node;
                 if (cb.isSelected()) {
                     cb.setSelected(false);
                 }
             }
             
             if(node instanceof Parent) {
            	 unselectVbox((Parent)node);
             }
         }
    }
    
    public void clearTextField(Parent vb) {
    	for (Node node : vb.getChildrenUnmodifiable()) {
            if (node instanceof TextField) {
                TextField cb = (TextField) node;
                cb.setText("");
            }
            
            if(node instanceof Parent) {
            	clearTextField((Parent)node);
            }
        }
    }
    
    public void clearTab(String tabname) {
    	switch(tabname) {
    	case "onh":
    		ToggleGroup[] onhToggle={discSize, margins, rimColor};
    		unselectToggle(onhToggle);
    		unselectVbox(discOtherSigns);
    		hRatio.setText("");
    		vRatio.setText("");
    		cupAssymetry.setSelected(false);
    		
    		break;
    		
    	case "macula":
    		ToggleGroup[] mToggle= {freflex, rprofile, ireflex, pvd};
    		unselectToggle(mToggle);
    		unselectVbox(maculaChecks);
    		break;
    		
    	case "retina":
    		ToggleGroup[] rToggle= {transparency, bcolor};
    		unselectToggle(rToggle);
    		unselectVbox(retinaBox);
    		clearTextField(retinaBox);
    		break;
    		
    	case "vessels":
    		avRatio.setText("");
    		ToggleGroup[] vToggle= {arterialR};
    		this.unselectToggle(vToggle);
    		this.unselectVbox(vesselBox);
    		break;
    		
    	case "periphery":
    		ToggleGroup[] pToggle= {serrata, vitreousb};
    		this.unselectToggle(pToggle);
    		this.unselectVbox(pBox);
    	}
    }
    
    public void setGridBindings(GridPane gp) {
    	 for (int i = 0; i < gp.getRowCount(); i++) {
    	        Node checkNode = getNodeByRowColumnIndex(i, 0, gp); // Colonne 0
    	        Node textNode = getNodeByRowColumnIndex(i, 1, gp);  // Colonne 1

    	        if (checkNode instanceof CheckBox && textNode instanceof TextField) {
    	            ((TextField) textNode).disableProperty().bind(((CheckBox) checkNode).selectedProperty().not());
    	        }
    	    }
    	
    }
    
    public String getResultsFromGrid(GridPane gp){
    	String[] orig= {"Microaneurysms", "Cotton Wool Spots", "Dot and Blot hemorrhage", "Flame-Shaped Hemorrhages", "Neovascularisation else where", "Boat-shaped/Preretinal hemorrhage", "Venous beading / looping", "IRMA", "Emboli", "Infectious / inflammatory lesions", "Choroid nevus"};
    	String[] trad= {"Microanévrismes", "Nodules cotonneux", "Hémorragies en taches et en points", "Hémorragies en flammes", "Néovascularisation ailleurs", "Hémorragie en bateau/prérétinienne", "Chapelet veineux / boucles veineuses", "AMIR", "Emboles", "Lésions infectieuses / inflammatoires", "Naevus choroïdien"};
    	
    	String res="";
    	for (int i = 0; i < gp.getRowCount(); i++) {
 	        Node checkNode = getNodeByRowColumnIndex(i, 0, gp); // Colonne 0
 	        Node textNode = getNodeByRowColumnIndex(i, 1, gp);  // Colonne 1

 	        if (checkNode instanceof CheckBox && textNode instanceof TextField) {
 	          if(((CheckBox) checkNode).isSelected()) {
 	        	  
 	        	  String sel=((CheckBox) checkNode).getText().trim();
 	        	  System.out.println(sel);
 	        	  String traduit= trad[Arrays.asList(orig).indexOf(sel)];
 	        	  
 	        	     //((CheckBox) checkNode).getText()+"/"+((TextField) textNode).getText();
 	        	  String rep=traduit+" "+((TextField) textNode).getText();
 	        	  res+=(res.isBlank())?rep:","+rep;
 	          }
 	        }
 	    }
    	
    	if(res.isBlank()) {
    		res="Aucun";
    	}
    	return res;
    	
    	
    }
    
    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
        	int r=(GridPane.getRowIndex(node)==null)?0:GridPane.getRowIndex(node);
        	int c=(GridPane.getColumnIndex(node)==null)?0:GridPane.getColumnIndex(node);
        	
            if (r == row && c== column) {
                return node;
            }
        }
        return null;
    }
    
    
    public void clearAllTabs() {
    	//TODO efface les valeurs choisi ds les tabs et reactive les boutons des tabs 
    	String[] tabs= {"onh", "macula", "retina", "vessels", "periphery"};
    	for(String  t: tabs) {
    		clearTab(t);
    	}
    	
    	enableBtns();
    }
    
    
    
    public boolean verifyTabsCompletion() {
    	
    	BooleanProperty[] bools= {isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isPeripheryCompleted,isVesselsCompleted};
    	for(BooleanProperty b: bools) {
    		if(!b.get()) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public String getNotCompletedMessage() {
    	String message="Be careful! \n The following tabs are not completed for this eye.\n";
    	BooleanProperty[] bools= {isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isVesselsCompleted, isPeripheryCompleted};
    	String[] tabs= {"onh", "macula", "retina", "vessels", "periphery"};
    	int index=0;
    	for(BooleanProperty b: bools) {
    		if(!b.get()) {
    			message+=" → "+tabs[index]+"\n";
    		}
    		index++;
    	}
    	
    	message+="Do you want to leave them uncompleted?";
    	return message;
    }
    
    public void createFundusProtocol() {
		reset(true);
		fundusResults.clear();
    	globTabs.getSelectionModel().select(0);
    }
    
    public void enableBtns() {
    	Button[] btns= {onhBtn, maculaBtn, retinaBtn, vesselsBtn, peripheryBtn};
    	for(Button btn: btns) {
    		btn.setDisable(false);
    	}
    }
    
    public void reset(boolean bool) {
    	//TODO remettre les tabs a defaut - les valeurs des booleans a defaut - if true on efface le patient ---finish method --if false on laisse info --changeEye method
    	System.out.println("---reset called-------");
    	ArrayList<BooleanProperty> properties= new ArrayList<BooleanProperty>(List.of(isOnhCompleted, isMaculaCompleted, isRetinaCompleted,isPeripheryCompleted,isVesselsCompleted));
    	
    	if(bool) {
    		System.out.println("---patient-------");
    		properties.addAll(List.of(isPatientInfoSet, isEyeChanged));
    		TextField [] pInfos= {pName, pAge};
    		for(TextField tf : pInfos) {
        		tf.setText("");
        	}
    	}
    	
    	//reset booleans 
    	for(BooleanProperty b: properties) { 
    		b.set(false);
    	}
    	
    	//clear tabs 
    	clearAllTabs(); //efface tous les choix et active les btns
    	setPermittersFalse(); //rend false les allowxxx
    	initializeToggle();  //remets les valeurs par defaut
    	numerotation=0;
    	
    	if(bool) {
    		System.out.println("---tabs change------");
    		globTabs.getSelectionModel().select(0); //on montre info
    		pName.requestFocus();
    	}else {
    		globTabs.getSelectionModel().select(1); //onh
    	}
    	
    }
    
    public void initializeToggle() {
    	// ------------------------ONH-----------------------------------   
    	discSize.selectToggle(this.sNormal);
    	margins.selectToggle(this.mSharp);
    	rimColor.selectToggle(this.rPink);
    	
    	vRatio.setText("0.4");
    	hRatio.setText("0.3");
    	
    	//-------------------------Macula -------------------------------
    	freflex.selectToggle(refPresent);
    	rprofile.selectToggle(pFlat);
    	ireflex.selectToggle(iCellophane);
    	pvd.selectToggle(this.pvdAbsent);
    	
    	//----------------------retina----------------------------
    	transparency.selectToggle(Tnormal);
    	bcolor.selectToggle(cNormal);
    	
    	//**------------------------------vessels------------------
    	arterialR.selectToggle(nLight);
    	avRatio.setText("2/3");
    	
    	//***-------------------------periphery-------------------
    	serrata.selectToggle(oraY);
    	vitreousb.selectToggle(vbaseY);
    }
    
    public void setPermittersFalse() {
    	allowFinish=false; allowOneEye=false;allowEyeIncomplet=false; allowQuit=false; isFileDeleted=false;
    }
    
    public void registerPart(String titre, String contenu , String[] toReplace, String[] replaceWith, boolean patientReg) {
    	closeWord();
    	
    	 try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
        Document fundus = new Document();
        if(patientReg) {
        	InputStream fundusTemplate = ExMain.class.getResourceAsStream("sources/fundus.docx");
            fundus.loadFromStream(fundusTemplate, FileFormat.Docx_2013);
        }else {
        	fundus.loadFromFile(filename);
        }
        
        
        if(!titre.equals("")) {
        	Section sec=fundus.getSections().get(0);
            TextRange t=sec.addParagraph().appendText(titre);
            t.getCharacterFormat().setBold(true);
            
            Paragraph p=sec.addParagraph();
            p.appendText(contenu);
            
            p.getFormat().setBeforeSpacing(0f);
            p.getFormat().setAfterSpacing(0f);
            
        }
        
        
        for(int j = 0; j < toReplace.length; ++j) {
           fundus.replace(toReplace[j], replaceWith[j], true, true);
        }

        fundus.saveToFile(filename);
        
        numerotation++;
    }
    
    
    
    public static void closeWord() {
	    try {
	        
	        ProcessBuilder pb = new ProcessBuilder("taskkill", "/F", "/IM", "WINWORD.EXE");
	        pb.start();
	        
	    } catch (Exception ex) {
	        System.err.println("Erreur lors de la fermeture d'Excel : " + ex.getMessage());
	    }
	}
    
    public void getConclusion() {
    	if(!isFileDeleted) {
    		main.showExamConclusion();
    	}
    }
    
    public void populateTreeView(TreeView<String> tree, String path ) {
    	System.out.println("populate called");
        tree.setVisible(true);
        TreeItem<String>root= new TreeItem<String>();
        root.getChildren().clear();
        
        File f = new File(path);
        if (f.isDirectory()) {
           TreeItem<String> lecteur = new TreeItem<String>(f.getName(), new ImageView(this.folder));
           File[] enfants = f.listFiles();
           Arrays.sort(enfants, Comparator.comparingLong(File::lastModified).reversed());
           if (enfants == null) {
        	   root.getChildren().add(lecteur);
           } else {
              File[] var7 = enfants;
              int var6 = enfants.length;

              for(int var5 = 0; var5 < var6; ++var5) {
                 File fil = var7[var5];
                 TreeItem<String> petit = new TreeItem<String>(fil.getName());
                 if (fil.isDirectory()) {
                    lecteur.setGraphic(new ImageView(this.folder));
                    lecteur.getChildren().add(main.getAppHomeController().findEnfants(fil, petit));
                 } else {
                    lecteur.setGraphic(new ImageView(this.fileGraphic));
                    lecteur.getChildren().add(petit);
                 }
              }

              lecteur.setExpanded(true);
              root.getChildren().add(lecteur);
           }
        }

        root.setExpanded(true);
        tree.setRoot(root);
        tree.setShowRoot(false);
    }
    
    public void showIndicator() {
        ProgressIndicator pi = new ProgressIndicator();
        this.indicatorBox.getChildren().add(pi);
        this.indicatorBox.setAlignment(Pos.CENTER);
        pi.setMinSize(100.0D, 100.0D);
        borderP.setDisable(true);
        generalPane.getChildren().add(indicatorBox);
     }

     public void removeIndicator(String message) {
        this.indicatorBox.getChildren().clear();
        generalPane.getChildren().remove(this.indicatorBox);
        borderP.setDisable(false);
        Tools.showEphemereText(message);
        this.main.setIsIntervalSet(false);
        this.main.setIsOperativePeriodeSet(false);
        this.populateTreeView(files, main.getCreancesPath()+"/PROTOCOLS");
     }
     
     public  void showGallery(List<String> imagePaths, String lesion, String tooltip) {
         Stage stage = new Stage();
         stage.initModality(Modality.APPLICATION_MODAL);
         
         stage.setTitle("Images pour "+lesion +"(" + imagePaths.size() + ")");

         // 1. Le conteneur d'images (FlowPane)
         // Les images s'alignent horizontalement et passent à la ligne
         FlowPane flowPane = new FlowPane();
         flowPane.setHgap(10); // Espace horizontal entre les images
         flowPane.setVgap(10); // Espace vertical
         flowPane.setPrefWrapLength(600); // Largeur avant de passer à la ligne

         // 2. Boucle pour ajouter chaque image
         if(imagePaths.size()!=1 || !imagePaths.get(0).isBlank()) {
        	 if(!tooltip.isBlank()) {
					Label lab= new Label(lesion+" - "+tooltip);
					lab.setPadding(new Insets(10.0));
					lab.setWrapText(true);
					lab.setPrefWidth(580);
					lab.setMaxWidth(580);
					flowPane.getChildren().add(lab);
				}
         }
         
         for (String path : imagePaths) {
			try {
				if(!path.isBlank()) {
					Image img = new Image(getClass().getResourceAsStream("../fundusImage/"+path));
					ImageView iv = new ImageView(img);
		            
		             // On définit une largeur standard pour toutes (ex: 150px)
		             iv.setFitWidth(250);
		             iv.setPreserveRatio(true);
		             iv.setSmooth(true);
		             
		             flowPane.getChildren().add(iv);
				}else {
					Label lab= new Label("Aucune image n'a été trouvée pour "+lesion);
					lab.setPadding(new Insets(10.0));
					flowPane.getChildren().add(lab);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
         }

         // 3. ScrollPane au cas où il y aurait beaucoup d'images
         ScrollPane scrollPane = new ScrollPane(flowPane);
         scrollPane.setFitToWidth(true);
         scrollPane.setPrefSize(600, 400);

         Scene scene = new Scene(scrollPane);
         stage.setScene(scene);
         stage.show();
     }
     
     public List<String> chercherFichiers(String dossierPath, String nomRecherche) throws IOException {
    	 
    	    List<String> resultats = new ArrayList<>();
    	    Path folder = Paths.get(dossierPath);
    	    

    	    // Le "glob" permet d'utiliser des jokers comme *
    	    // Exemple : "*rapport*" trouvera tous les fichiers contenant "rapport"
    	    if(!nomRecherche.isBlank()) {
    	    	try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, "*" + nomRecherche + "*")) {
        	        for (Path entry : stream) {
        	            resultats.add(entry.getFileName().toString());
        	        }
        	    }
    	    }else {
    	    	resultats.add("");
    	    }
    	    
    	    return resultats;
     }
     
     public String chooseCheckPhoto(String checkText) {
    	 String[] checkS= {"Defects of RFNL", "Undermining of vessels", "Baring of vessels", "Nasal displacement", "Bayoneting sign", "Laminar dot sign", "Beta zone", "Alpha zone", "Disc hemorrhage", "ISNT Rule violation", "Notching", "Neovascularisation on the disc","Cystoid spaces", "Serous Retinal Detachment", "Pigment Epithelial Detachment", "Hard drusens", "Soft drusens", "Cuticular drusens", "Hard Exudates", "Geographic atrophy", "Pigment clumping", "Macular Nevus", "Subretinal Hemorrhage", "Full thickness macular hole", "Lamellar Hole", "Arterial narrowing", "Venous dilation", "Gunn sign", "Salus sign", "Bonnet sign", "Abnormal tortuosity", "Sheathing", "Pavingstone", "White-without-pressure", "Lattice", "Vitreoretinal tuft", "Retinoschisis", "Tear", "Atrophic hole", "Retinal detachment", "Peripheral neovascularization","Microaneurysms", "Cotton Wool Spots", "Dot and Blot hemorrhage", "Flame-Shaped Hemorrhages", "Neovascularisation else where", "Boat-shaped/Preretinal hemorrhage", "Venous beading / looping", "IRMA", "Emboli", "Infectious / inflammatory lesions", "Choroid nevus"};
    	 String[] checkP= {"rfnldefect","","baring","nasalshift","bayoneting","dotsign","alphabetazone","alphabetazone","dischemorrhage","","notching","discneovas","","serousdet","ped","harddrusen","softdrusen","cutdrusen","hardex","geoatrophy","pigclump","macnaevus","subrethem","ftmh","lamh","artnarrow","vendil","gunn","salus","","turtuo","sheathing","pavingstone", "wwop", "lattice","tuft","retinoschisis","tear","atrophichole","retdet","perneov","microa","cottonwool","dotblot","flamehem","perneov","preret","venloop","irma","emboli","inflamatori","naevus"};
    	 
    	 String photo="";
    	 if(checkS.length==checkP.length) {
    		return checkP[Arrays.asList(checkS).indexOf(checkText)];
    	 }
    	 
    	 return photo;
     }
     
     
}
