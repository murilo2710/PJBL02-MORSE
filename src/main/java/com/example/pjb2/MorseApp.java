package com.example.pjb2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MorseApp extends Application {

    private MorseBST tree;

    @Override
    public void start(Stage primaryStage) {
        tree = new MorseBST(); // Árvore inicia vazia

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Entrada de texto
        TextField inputField = new TextField();
        inputField.setPromptText("Digite texto ou código Morse");

        // Área de resultado
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPromptText("Resultado aparecerá aqui...");
        outputArea.setPrefHeight(80);

        // Botões
        Button insertBtn = new Button("Inserir letra manualmente");
        Button encodeBtn = new Button("Codificar (Texto → Morse)");
        Button decodeBtn = new Button("Decodificar (Morse → Texto)");
        Button viewTreeBtn = new Button("Visualizar Árvore");

        // Ações dos botões
        insertBtn.setOnAction(e -> inserirManual(inputField, outputArea));
        encodeBtn.setOnAction(e -> {
            if (tree.isEmpty()) {
                outputArea.setText("✗ ERRO: Árvore vazia! Insira letras primeiro.");
                return;
            }
            String text = inputField.getText().toUpperCase();
            String result = tree.encode(text);
            outputArea.setText(result);
        });
        decodeBtn.setOnAction(e -> {
            if (tree.isEmpty()) {
                outputArea.setText("✗ ERRO: Árvore vazia! Insira letras primeiro.");
                return;
            }
            String morse = inputField.getText().trim();
            String result = tree.decode(morse);
            outputArea.setText(result);
        });
        viewTreeBtn.setOnAction(e -> {
            if (tree.isEmpty()) {
                outputArea.setText("✗ ERRO: Árvore vazia! Insira letras primeiro.");
                return;
            }
            abrirJanelaArvore();
        });

        // Layout dos botões
        HBox buttonBox = new HBox(10, insertBtn, encodeBtn, decodeBtn, viewTreeBtn);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        VBox vbox = new VBox(10, inputField, buttonBox, outputArea);
        root.setCenter(vbox);

        Scene scene = new Scene(root, 900, 400);
        primaryStage.setTitle("Tradutor Morse - JavaFX (Árvore Dinâmica)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ============================================
    // Inserção manual de letra
    // ============================================
    private void inserirManual(TextField inputField, TextArea outputArea) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Inserir letra");
        dialog.setHeaderText("Digite a letra a ser inserida (A-Z ou espaço):");
        dialog.setContentText("Letra:");

        dialog.showAndWait().ifPresent(letraInput -> {
            letraInput = letraInput.trim().toUpperCase();
            if (letraInput.length() != 1 || (!Character.isLetter(letraInput.charAt(0)) && letraInput.charAt(0) != ' ')) {
                outputArea.setText("✗ ERRO: Digite apenas uma letra ou espaço!");
                return;
            }
            char letra = letraInput.charAt(0);

            TextInputDialog morseDialog = new TextInputDialog();
            morseDialog.setTitle("Inserir código Morse");
            morseDialog.setHeaderText("Digite o código Morse para a letra '" + letra + "'");
            morseDialog.setContentText("Use apenas . e - :");

            morseDialog.showAndWait().ifPresent(morse -> {
                morse = morse.trim();
                if (!morse.matches("[.-]+")) {
                    outputArea.setText("✗ ERRO: Use apenas pontos (.) e traços (-)!");
                    return;
                }
                tree.insert(letra, morse);
                outputArea.setText("✓ Letra '" + letra + "' inserida com código: " + morse);
            });
        });
    }

    // ============================================
    // Abrir nova janela com a árvore
    // ============================================
    private void abrirJanelaArvore() {
        Stage treeStage = new Stage();
        treeStage.setTitle("Visualizador da Árvore Morse");

        Canvas canvas = new Canvas(1200, 600);
        canvas.widthProperty().bind(treeStage.widthProperty().subtract(20));
        canvas.heightProperty().bind(treeStage.heightProperty().subtract(40));

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> drawTree(canvas));
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> drawTree(canvas));

        drawTree(canvas);

        StackPane pane = new StackPane(canvas);
        Scene scene = new Scene(pane, 1200, 600);
        treeStage.setScene(scene);
        treeStage.show();
    }

    // ============================================
    // Desenhar árvore no Canvas
    // ============================================
    private void drawTree(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFont(Font.font("Arial", 14));

        double width = canvas.getWidth();
        double levelHeight = 80;

        drawNode(gc, tree.getRoot(), width / 2, 40, width / 4, levelHeight);
    }

    private void drawNode(GraphicsContext gc, MorseNode node, double x, double y, double xOffset, double levelHeight) {
        if (node == null) return;

        // Linha para filho esquerdo (ponto)
        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + levelHeight;
            gc.setStroke(Color.BLUE);
            gc.strokeLine(x, y + 20, newX, newY - 20);
            gc.setFill(Color.BLUE);
            gc.fillText(".", x - xOffset / 2 - 5, y + levelHeight / 2);
        }

        // Linha para filho direito (traço)
        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + levelHeight;
            gc.setStroke(Color.RED);
            gc.strokeLine(x, y + 20, newX, newY - 20);
            gc.setFill(Color.RED);
            gc.fillText("-", x + xOffset / 2 - 5, y + levelHeight / 2);
        }

        // Nó
        gc.setFill(Color.WHITE);
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 20, y - 20, 40, 40);

        // Letra
        gc.setFill(Color.BLACK);
        String text = (node.letter == ' ' || node.letter == '\0') ? "" : String.valueOf(node.letter);
        double textWidth = text.length() * 7;
        gc.fillText(text, x - textWidth / 2, y + 5);

        // Recursivo
        drawNode(gc, node.left, x - xOffset, y + levelHeight, xOffset / 2, levelHeight);
        drawNode(gc, node.right, x + xOffset, y + levelHeight, xOffset / 2, levelHeight);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
