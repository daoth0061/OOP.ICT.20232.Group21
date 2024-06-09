package screen;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import model.Graph;
import model.Node;
import search.Dijkstra;
import search.Search;

public class TraversalController {

    @FXML
    private ListView<String> waitingListView;

    @FXML
    private ListView<String> visitedListView;

    @FXML
    private ListView<String> searchPathListView;

    private String selectedOption;

    @FXML
    private Label waitingLabel;

    @FXML
    private Label visitedLabel;

    @FXML
    private Label searchPathLabel;

    @FXML
    private ChoiceBox<String> choiceBox; // Khớp với ID trong file FXML

    @FXML
    private Button nextButton; // Thêm biến cho nút Next

    private Graph graph;
    private Search search;
    private static Node Curnode;

    private GraphGenController graphGenController; // Thêm một tham chiếu đến GraphGenController

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    // Thêm setter cho graphGenController
    public void setGraphGenController(GraphGenController graphGenController) {
        this.graphGenController = graphGenController;
    }

    @FXML
    public void initialize() {
        // Khởi tạo giao diện nếu cần thiết
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Dijkstra",
                        "BellmanFord"
                );
        choiceBox.setItems(options);
        choiceBox.setOnAction(event -> handleChoiceBoxAction());
        nextButton.setOnAction(event -> handleNextButtonAction()); // Gắn sự kiện cho nút Next
    }

    private void handleChoiceBoxAction() {
        selectedOption = choiceBox.getValue();
    }

    private void handleNextButtonAction() {
        if ("Dijkstra".equals(selectedOption)) {
            search = new Dijkstra(graph,graph.getNodes().get(0),graph.getNodes().get(4));
            if (Curnode == null) {
                return;
            }
            this.drawCurrentNode(String.valueOf(Curnode.getValue()));
        }
    }



    // Thêm hàm setCurrentNode để tô màu node hiện tại
    public void drawCurrentNode(String currentNode) {
        graphGenController.drawColoredNode(currentNode, 8.0, Color.BLUE);
    }

    public void setCurnode(Node Curnode){
        this.Curnode = Curnode;
    }
}
