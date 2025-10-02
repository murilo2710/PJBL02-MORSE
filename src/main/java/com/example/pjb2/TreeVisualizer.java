package com.example.pjb2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TreeVisualizer extends Application {

    private static MorseBST tree; // Árvore a ser visualizada

    /**
     * Método estático para receber a árvore do Main
     */
    public static void setTree(MorseBST bst) {
        tree = bst;
    }

    @Override
    public void start(Stage primaryStage) {
        // Validar se a árvore foi passada
        if (tree == null || tree.isEmpty()) {
            System.out.println("ERRO: Árvore vazia ou não inicializada!");
            primaryStage.close();
            return;
        }

        primaryStage.setTitle("Visualizador de Árvore Morse");

        // Calcular dimensões baseadas na altura da árvore
        int height = getHeight(tree.getRoot());
        int canvasHeight = 100 + height * 100;
        int canvasWidth = Math.max(1200, (int) Math.pow(2, height) * 40);

        Canvas canvas = new Canvas(canvasWidth, canvasHeight);
        drawTree(canvas, tree.getRoot());

        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, canvasWidth, canvasHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Calcular altura da árvore (RECURSIVO)
     */
    private int getHeight(MorseNode node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    /**
     * Desenhar árvore completa no Canvas
     */
    private void drawTree(Canvas canvas, MorseNode root) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFont(Font.font("Arial", 14));

        // Iniciar desenho recursivo
        drawNode(gc, root, canvas.getWidth() / 2, 40, canvas.getWidth() / 4);
    }

    /**
     * Desenhar um nó e suas subárvores (RECURSIVO)
     */
    private void drawNode(GraphicsContext gc, MorseNode node, double x, double y, double xOffset) {
        if (node == null) return;

        // Desenhar conexões primeiro (para ficarem atrás dos círculos)

        // Linha para filho esquerdo (ponto)
        if (node.left != null) {
            double newX = x - xOffset;
            double newY = y + 100;
            gc.setStroke(Color.BLUE);
            gc.strokeLine(x, y + 20, newX, newY - 20);

            // Desenhar label "." na linha
            gc.setFill(Color.BLUE);
            gc.fillText(".", x - xOffset/2 - 5, y + 50);
        }

        // Linha para filho direito (traço)
        if (node.right != null) {
            double newX = x + xOffset;
            double newY = y + 100;
            gc.setStroke(Color.RED);
            gc.strokeLine(x, y + 20, newX, newY - 20);

            // Desenhar label "-" na linha
            gc.setFill(Color.RED);
            gc.fillText("-", x + xOffset/2 - 5, y + 50);
        }

        // Desenhar círculo do nó
        gc.setFill(Color.WHITE);
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 20, y - 20, 40, 40);

        // Desenhar letra dentro do círculo
        gc.setFill(Color.BLACK);
        String text = (node.letter == ' ' || node.letter == '\0') ? "" : String.valueOf(node.letter);

        // Centralizar texto
        double textWidth = text.length() * 7;
        gc.fillText(text, x - textWidth/2, y + 5);

        // Chamar recursivamente para subárvores
        if (node.left != null) {
            drawNode(gc, node.left, x - xOffset, y + 100, xOffset / 2);
        }
        if (node.right != null) {
            drawNode(gc, node.right, x + xOffset, y + 100, xOffset / 2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}