package app.view;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.controlsfx.control.CheckComboBox;

import com.spire.doc.Document;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.Paragraph;

import app.ExMain;
import app.tools.Tools;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ExamenConclusionController {
	@FXML
	private CheckComboBox<String> conclusion;
	
	@FXML
	private TextArea recommendation;

	@FXML
	private Button saveBtn;
	
	@FXML
	private TextField examinateur;
	
	private Stage stage;
	
	private ExMain main; 
	
	public void setStage(Stage stg) {
		stage=stg;
	}
	
	public void setMain(ExMain m) {
		main=m;
	}
	
	public void initialize() {
		Node[] nodes= {conclusion, recommendation, examinateur, saveBtn};
		Tools.addNavigation(nodes);
		String[] diagnostics = {
			    "Fond d'oeil d'aspect normal",
			    "DMLA sèche",
			    "Suspicion de glaucome",
			    "Rétinopathie diabétique légère",
			    "Membrane épirétinienne",
			    "Naevus choroïdien",
			    "Myopie forte",
			    "Rétinopathie diabétique modérée",
			    "Rétinopathie hypertensive légère",
			    "DMLA humide",
			    "Œdème maculaire",
			    "Occlusion de branche de la veine centrale",
			    "Rétinopathie hypertensive modérée",
			    "Déchirure ou trou rétinien",
			    "Chorioretinite séreuse centrale",
			    "Hémorragie du vitré",
			    "Rétinopathie diabétique sévère",
			    "Rétinopathie hypertensive sévère",
			    "Occlusion de branche de l'artère centrale",
			    "Décollement de la rétine",
			    "Occlusions de la veine centrale",
			    "Occlusion de l'artère centrale",
			    "Œdème papillaire",
			    "Atrophie optique",
			    "Rétinite pigmentaire",
			    "Mélanome",
			    "Rétinite infectieuse"
			};
		conclusion.getItems().addAll(diagnostics);
		
		String[] recommandations = {
			    "Contrôle de routine tous les 1 à 2 ans.",
			    "Vitamines/antioxydants et auto-surveillance (grille d'Amsler).",
			    "Examens complémentaires (OCT, champ visuel) et suivi de la tension oculaire.",
			    "Équilibre de la glycémie et contrôle annuel du fond d'œil.",
			    "Surveillance si stable ; chirurgie si la vision se déforme.",
			    "Photographie de contrôle annuelle pour vérifier la stabilité.",
			    "Examen annuel avec dilatation pour surveiller la périphérie.",
			    "Contrôle semestriel et stabilisation stricte du diabète.",
			    "Suivi de la tension artérielle avec le médecin traitant.",
			    "Urgence thérapeutique : injections intra-vitréennes rapides.",
			    "Traitement de la cause et injections intra-vitréennes.",
			    "Bilan cardio-vasculaire et surveillance d'un œdème maculaire.",
			    "Ajustement impératif du traitement antihypertenseur.",
			    "Laser Argon immédiat pour prévenir le décollement.",
			    "Repos, gestion du stress et arrêt total des corticoïdes.",
			    "Repos assis et échographie pour exclure une déchirure.",
			    "Surveillance rapprochée et laser (PPR) à envisager.",
			    "Prise en charge urgente de la tension (risque d'AVC).",
			    "Bilan cardiaque et carotidien complet en urgence.",
			    "Chirurgie urgente en milieu hospitalier spécialisé.",
			    "Bilan cardio-vasculaire et traitement des complications.",
			    "Urgence vitale immédiate (bilan étiologique et traitement).",
			    "Imagerie cérébrale urgente (exclure une HTIC).",
			    "Bilan étiologique (neuro, vasculaire) pour stabilisation.",
			    "Protection UV et suivi génétique en centre spécialisé.",
			    "Avis spécialisé en oncologie oculaire (traitement dédié).",
			    "Traitement anti-infectieux d'urgence par voie générale."
			};
		
		conclusion.getCheckModel().getCheckedItems().addListener((ListChangeListener<String>) change -> {
			String recomm="";
		    while (change.next()) {
				List<Integer> indexes=conclusion.getCheckModel().getCheckedIndices();
				for(Integer i: indexes) {
					recomm+=(recomm.isBlank())?recommandations[i]:"\n"+recommandations[i];
				}
				
				recommendation.setText(recomm);
		    }
		});

		
		examinateur.setText("Dr Desire C.");
		
		
	}
	
	public void focusBtn() {
		saveBtn.requestFocus();
	}
	
	
	public void saveConclusion() {
		if(!recommendation.getText().isBlank()) {
			FundusController.closeWord();
			String filename=main.getFundusController().getFilename();
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Document fundus= new Document();
			fundus.loadFromFile(filename);
			fundus.getSections().get(0).addParagraph().appendText("CONCLUSION").getCharacterFormat().setBold(true);
			fundus.getSections().get(0).addParagraph().appendText("@conclusion@");
			fundus.getSections().get(0).addParagraph().appendText("RECOMMENDATIONS").getCharacterFormat().setBold(true);
			fundus.getSections().get(0).addParagraph().appendText("@recommendation@");
			
			Paragraph dr=fundus.getSections().get(0).addParagraph();
			dr.appendText("@dr@");
			dr.getFormat().setHorizontalAlignment(HorizontalAlignment.Right);
			
					// RECOMMENDATIONS\r\n @recommendation@ \r\n \r\n @dr@");
			List<String> digs=conclusion.getCheckModel().getCheckedItems();
			String diagns="";
			for(String str: digs) {
				diagns+=(diagns.isBlank())?str:"\n"+str;
			}
			fundus.replace("@conclusion@", diagns, true, true);
			fundus.replace("@recommendation@", recommendation.getText().trim(), true, true);
			fundus.replace("@dr@", examinateur.getText().trim(), true, true);
			
			fundus.saveToFile(filename);
			
			stage.close();
			
		}
	}


}
