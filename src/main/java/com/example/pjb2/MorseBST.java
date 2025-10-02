package com.example.pjb2;

public class MorseBST {
    private MorseNode root;

    public MorseBST() {
        this.root = null; // inicia vazia
    }

    // ============================================
    // MÉTODOS DE VALIDAÇÃO (Tratamento de Erros)
    // ============================================

    public boolean isEmpty() {
        return root == null;
    }

    public boolean isValidText(String text) {
        if (text == null || text.trim().isEmpty()) return false;
        // Aceita apenas letras e espaços
        return text.toUpperCase().matches("[A-Z ]+");
    }

    public boolean isValidMorse(String morse) {
        if (morse == null || morse.trim().isEmpty()) return false;
        // Aceita apenas pontos, traços e espaços
        return morse.matches("[.\\- ]+");
    }

    // ============================================
    // INSERÇÃO (RECURSIVA)
    // ============================================

    public void insert(char letter, String morseCode) {
        root = insertRecursive(root, letter, morseCode, 0);
    }

    private MorseNode insertRecursive(MorseNode node, char letter, String code, int index) {
        // Criar nó vazio se necessário
        if (node == null) {
            node = new MorseNode(' ');
        }

        // Se chegou no final do código, inserir a letra
        if (index == code.length()) {
            node.letter = letter;
            return node;
        }

        // Navegar recursivamente: . para esquerda, - para direita
        char symbol = code.charAt(index);
        if (symbol == '.') {
            node.left = insertRecursive(node.left, letter, code, index + 1);
        } else if (symbol == '-') {
            node.right = insertRecursive(node.right, letter, code, index + 1);
        }

        return node;
    }

    // ============================================
    // BUSCA (RECURSIVA)
    // ============================================

    private char findLetter(MorseNode node, String code, int index) {
        // Não encontrado
        if (node == null) return '?';

        // Chegou no final do código
        if (index == code.length()) {
            return node.letter;
        }

        // Navegar recursivamente
        char c = code.charAt(index);
        if (c == '.') {
            return findLetter(node.left, code, index + 1);
        } else {
            return findLetter(node.right, code, index + 1);
        }
    }

    // ============================================
    // DECODIFICAÇÃO (MORSE → TEXTO)
    // ============================================

    public String decode(String morseMessage) {
        // Validações
        if (isEmpty()) {
            return "ERRO: Árvore vazia! Insira caracteres primeiro.";
        }
        if (!isValidMorse(morseMessage)) {
            return "ERRO: Código morse inválido! Use apenas . - e espaços";
        }

        StringBuilder decoded = new StringBuilder();
        String[] letters = morseMessage.trim().split(" ");

        for (String code : letters) {
            // Ignorar espaços vazios (espaços duplos representam espaço entre palavras)
            if (code.isEmpty()) {
                decoded.append(" ");
                continue;
            }

            char letra = findLetter(root, code, 0);

            if (letra == '?' || letra == ' ') {
                return "ERRO: Código morse não encontrado na árvore: '" + code + "'";
            }

            decoded.append(letra);
        }

        return decoded.toString();
    }

    // ============================================
    // CODIFICAÇÃO (TEXTO → MORSE)
    // ============================================

    public String encode(String text) {
        // Validações
        if (isEmpty()) {
            return "ERRO: Árvore vazia! Insira caracteres primeiro.";
        }
        if (!isValidText(text)) {
            return "ERRO: Texto inválido! Use apenas letras e espaços";
        }

        StringBuilder encoded = new StringBuilder();
        text = text.toUpperCase();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == ' ') {
                // Espaço entre palavras = espaço duplo em morse
                encoded.append("  ");
            } else {
                String code = findCode(root, c, "");

                if (code == null) {
                    return "ERRO: Letra não encontrada na árvore: '" + c + "'";
                }

                encoded.append(code);

                // Adicionar espaço entre letras (exceto na última)
                if (i < text.length() - 1 && text.charAt(i + 1) != ' ') {
                    encoded.append(" ");
                }
            }
        }

        return encoded.toString().trim();
    }

    private String findCode(MorseNode node, char target, String path) {
        // Não encontrado
        if (node == null) return null;

        // Encontrou a letra
        if (node.letter == target) return path;

        // Buscar na subárvore esquerda (adiciona .)
        String leftPath = findCode(node.left, target, path + ".");
        if (leftPath != null) return leftPath;

        // Buscar na subárvore direita (adiciona -)
        String rightPath = findCode(node.right, target, path + "-");
        return rightPath;
    }

    // ============================================
    // GETTER PARA VISUALIZAÇÃO
    // ============================================

    public MorseNode getRoot() {
        return root;
    }
}