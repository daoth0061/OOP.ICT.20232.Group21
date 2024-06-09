package screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainScreenController {

    @FXML
    private BorderPane rootLayout;

    @FXML
    private void initialize() {
        try {
            // Load GraphGen.fxml
            FXMLLoader graphLoader = new FXMLLoader(getClass().getResource("GraphGen.fxml"));
            AnchorPane graphPanel = graphLoader.load();
            GraphGenController graphGenController = graphLoader.getController();

            // Load Traversal.fxml
            FXMLLoader traversalLoader = new FXMLLoader(getClass().getResource("Traversal.fxml"));
            AnchorPane traversalPanel = traversalLoader.load();
            TraversalController traversalController = traversalLoader.getController();

            // Set GraphGen.fxml to the top of rootLayout
            rootLayout.setTop(graphPanel);

            // Set Traversal.fxml to the bottom of rootLayout
            rootLayout.setBottom(traversalPanel);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
