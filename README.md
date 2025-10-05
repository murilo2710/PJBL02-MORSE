# 📡 Tradutor Morse - JavaFX

Um aplicativo de tradução de código Morse desenvolvido em Java com JavaFX, utilizando uma estrutura de árvore binária de busca (BST) para armazenamento e conversão dinâmica entre texto e código Morse.

## 📋 Sobre o Projeto

Este projeto implementa um tradutor de código Morse completo com interface gráfica, onde os usuários podem:
- Inserir manualmente letras e seus respectivos códigos Morse
- Codificar texto em código Morse
- Decodificar código Morse em texto
- Visualizar a estrutura da árvore binária graficamente

### 🎯 Características Principais

- **Árvore Dinâmica**: A árvore BST é construída dinamicamente conforme o usuário insere as letras
- **Visualização Gráfica**: Interface visual da árvore onde pontos (.) vão para a esquerda e traços (-) para a direita
- **Validação de Entrada**: Sistema robusto de validação para prevenir erros
- **Interface Intuitiva**: Design simples e funcional usando JavaFX

### 📦 Componentes

#### 1. **MorseNode.java**
Representa um nó na árvore binária:
- `char letter`: Letra armazenada no nó
- `MorseNode left`: Referência para o filho esquerdo (ponto)
- `MorseNode right`: Referência para o filho direito (traço)

#### 2. **MorseBST.java**
Implementa a árvore binária de busca com:
- **Inserção recursiva**: Adiciona letras seguindo o código Morse
- **Busca recursiva**: Encontra letras pelo código Morse
- **Codificação**: Converte texto para código Morse
- **Decodificação**: Converte código Morse para texto
- **Validações**: Verifica se entrada é válida

#### 3. **MorseApp.java**
Interface gráfica principal com:
- Campo de entrada de texto
- Botões para inserção, codificação, decodificação
- Visualizador da árvore em janela separada
- Área de resultado

#### 4. **TreeVisualizer.java**
Visualizador alternativo da árvore com canvas ajustável

## 🚀 Como Usar

### Pré-requisitos

- Java JDK 11 ou superior
- JavaFX SDK configurado
- IDE com suporte a JavaFX (IntelliJ IDEA, Eclipse, NetBeans)

### Executando o Projeto

1. Clone o repositório
2. Configure o JavaFX no seu IDE
3. Execute a classe `MorseApp.java`

### Passos de Utilização

1. **Inserir Letras**:
   - Clique em "Inserir letra manualmente"
   - Digite a letra (A-Z ou espaço)
   - Digite o código Morse correspondente (usando `.` e `-`)

2. **Codificar Texto**:
   - Digite o texto no campo de entrada
   - Clique em "Codificar (Texto → Morse)"
   - O código Morse aparecerá na área de resultado

3. **Decodificar Morse**:
   - Digite o código Morse no campo de entrada (separe letras com espaço)
   - Clique em "Decodificar (Morse → Texto)"
   - O texto traduzido aparecerá na área de resultado

4. **Visualizar Árvore**:
   - Clique em "Visualizar Árvore"
   - Uma nova janela mostrará a estrutura da árvore
   - Linhas azuis representam pontos (.)
   - Linhas vermelhas representam traços (-)

## 📝 Exemplo de Uso

### Código Morse Internacional (Exemplos)

| Letra | Código Morse |
|-------|--------------|
| A     | .-           |
| B     | -...         |
| C     | -.-.         |
| D     | -..          |
| E     | .            |
| S     | ...          |
| O     | ---          |

### Exemplo de Codificação

**Entrada**: `SOS`  
**Saída**: `... --- ...`

### Exemplo de Decodificação

**Entrada**: `... --- ...`  
**Saída**: `SOS`

## 🔧 Tratamento de Erros

O sistema inclui validações para:
- ✗ Árvore vazia (quando não há letras inseridas)
- ✗ Texto com caracteres inválidos
- ✗ Código Morse com símbolos incorretos
- ✗ Letras não encontradas na árvore
- ✗ Códigos Morse não mapeados

## 🎨 Interface Gráfica

### Janela Principal
- Campo de entrada multipropósito
- 4 botões de ação
- Área de resultado com feedback visual
- Dimensões: 900x400 pixels

### Janela de Visualização
- Canvas responsivo (ajusta ao tamanho da janela)
- Representação visual da árvore
- Cores diferenciadas para pontos e traços
- Dimensões iniciais: 1200x600 pixels

## 🛠️ Tecnologias Utilizadas

- **Java 11+**: Linguagem de programação
- **JavaFX**: Framework para interface gráfica
- **Estrutura de Dados**: Árvore Binária de Busca (BST)
- **Padrão de Projeto**: Recursividade para operações na árvore

## 📚 Conceitos Aplicados

- Estruturas de dados (Árvore Binária)
- Algoritmos recursivos
- Programação orientada a objetos
- Interface gráfica com JavaFX
- Tratamento de exceções
- Validação de entrada de dados
