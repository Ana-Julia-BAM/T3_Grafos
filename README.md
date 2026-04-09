# Projeto de Análise de Grafos - Circuito Euleriano

## 📋 Descrição do Projeto

Este projeto implementa um analisador de grafos direcionados (dígrafos) que encontra circuitos eulerianos utilizando o **Algoritmo de Hierholzer**. O programa lê dados de entrada de um arquivo, constrói um dígrafo ponderado, verifica suas propriedades e calcula o custo total do circuito euleriano encontrado.

## 🎯 Objetivos

- Construir e analisar dígrafos ponderados
- Verificar se o grafo está balanceado (condição necessária para circuito euleriano)
- Implementar o algoritmo de Hierholzer para encontrar circuitos eulerianos
- Calcular o custo total do circuito percorrido
- Gerar visualização gráfica do grafo usando Graphviz

## 📁 Estrutura do Projeto

```
T3/
├── src/                          # Código fonte Java
│   ├── Main.java                # Classe principal com lógica principal
│   ├── Digraph.java             # Implementação de dígrafo
│   ├── DirectedEdge.java        # Arestas direcionadas com pesos
│   ├── DirectedEulerianCycle.java # Algoritmo de Hierholzer
│   ├── BreadthFirstPaths.java   # Busca em largura
│   ├── DigraphGenerator.java    # Gerador de dígrafos
│   ├── Bag.java                 # Estrutura de dados Bag
│   ├── Queue.java               # Fila
│   ├── Stack.java               # Pilha
│   ├── SET.java                 # Conjunto
│   ├── In.java                  # Leitura de entrada
│   ├── StdIn.java               # Entrada padrão
│   ├── StdOut.java              # Saída padrão
│   └── StdRandom.java           # Geração de números aleatórios
├── bin/                         # Arquivos compilados (.class)
├── lib/                         # Bibliotecas externas (se houver)
├── dados/                       # Arquivos de entrada
│   ├── entrada_eulerizada.txt   # Dados do grafo eulerizado
│   └── entrada_original.txt     # Dados originais (backup)
├── grafo_unifor.dot            # Arquivo Graphviz gerado
├── resultado.pdf                # Visualização em PDF
├── executar.bat                 # Script de execução
└── README.md                    # Esta documentação
```

## 🔧 Pré-requisitos

### Sistema Operacional
- Windows 10/11 (compatível com scripts .bat)
- Linux/Mac (com ajustes no script)

### Java Development Kit (JDK)
- JDK 8 ou superior
- Variável de ambiente `JAVA_HOME` configurada

### Graphviz (para geração de PDF)
- Instalar Graphviz: `choco install graphviz` (Windows) ou `sudo apt install graphviz` (Linux)
- Comando `dot` deve estar no PATH

## 📊 Formato dos Dados de Entrada

O arquivo de entrada deve seguir o formato:

```
V E
v1 w1 peso1
v2 w2 peso2
...
vE wE pesoE
```

Onde:
- `V`: Número de vértices (0 a V-1)
- `E`: Número de arestas
- `vi wi pesoi`: Aresta direcionada do vértice `vi` para `wi` com peso `pesoi`

### Exemplo (`dados/entrada_eulerizada.txt`):
```
6 15
0 2 20
0 1 10
4 0 12
1 4 10
1 3 50
2 4 33
2 3 20
5 2 22
3 4 5
3 5 12
4 5 1
4 0 12
5 2 22
2 4 33
4 1 10
```

## 🚀 Como Executar

### Método 1: Script Automático (Recomendado)
1. Abra o Prompt de Comando na pasta raiz do projeto
2. Execute: `executar.bat`
3. O script irá:
   - Limpar arquivos antigos (.class, .dot, .pdf)
   - Compilar todos os arquivos Java
   - Executar o programa
   - Gerar o PDF automaticamente

### Método 2: Execução Manual
```bash
# 1. Limpar arquivos antigos
del /Q bin\*.class
del grafo_unifor.dot
del resultado.pdf

# 2. Compilar
javac -d bin -cp bin;lib src\*.java

# 3. Executar
java -cp bin;lib Main

# 4. Gerar PDF (opcional)
dot -Tpdf grafo_unifor.dot -o resultado.pdf
```

## 📈 Algoritmo Implementado

### 1. Construção do Dígrafo
- Lê arquivo de entrada
- Cria estrutura de adjacência usando `Digraph`
- Armazena pesos das arestas em `List<DirectedEdge>`

### 2. Análise de Graus
- Calcula grau de entrada (`indegree`) e saída (`outdegree`) para cada vértice
- Verifica condição de balanceamento: `indegree(v) == outdegree(v)` para todo vértice

### 3. Algoritmo de Hierholzer
- Implementado na classe `DirectedEulerianCycle`
- Usa busca em profundidade não-recursiva
- Complexidade: O(E + V)
- Encontra circuito que visita cada aresta exatamente uma vez

### 4. Cálculo do Custo Total
- Percorre o circuito encontrado
- Soma os pesos das arestas visitadas
- Trata arestas paralelas corretamente

### 5. Geração de Visualização
- Cria arquivo `.dot` compatível com Graphviz
- Inclui pesos das arestas e circuito encontrado
- Comando para PDF: `dot -Tpdf grafo_unifor.dot -o resultado.pdf`

## 📋 Saída do Programa

### Exemplo de Execução:
```
=== Estrutura do Dígrafo Construído ===
6 vertices, 15 edges
0: 2 1
1: 4 3
2: 4 3
3: 4 5
4: 5 0 1
5: 2

=== Análise dos Vértices (Graus) ===
Vértice 0: Entrada = 2, Saída = 2
Vértice 1: Entrada = 2, Saída = 2
Vértice 2: Entrada = 3, Saída = 3
Vértice 3: Entrada = 2, Saída = 2
Vértice 4: Entrada = 4, Saída = 4
Vértice 5: Entrada = 2, Saída = 2

Confirmação: Grafo está balanceado.

=== Circuito Euleriano Encontrado ===
0 -> 1 -> 3 -> 5 -> 2 -> 4 -> 1 -> 4 -> 0 -> 2 -> 3 -> 4 -> 5 -> 2 -> 4 -> 0

-------------------------------------------
CUSTO TOTAL DO CIRCUITO: 272,00
-------------------------------------------

Arquivo 'grafo_unifor.dot' gerado.
Use: dot -Tpdf grafo_unifor.dot -o resultado.pdf
```

## 🛠️ Classes e Estruturas

### Classes Principais:
- **`Main`**: Ponto de entrada, coordena todo o fluxo
- **`Digraph`**: Representa dígrafo usando lista de adjacência
- **`DirectedEdge`**: Aresta direcionada com peso
- **`DirectedEulerianCycle`**: Implementa algoritmo de Hierholzer

### Estruturas de Dados:
- **`Bag`**: Coleção genérica para adjacências
- **`Stack`**: Pilha para DFS
- **`Queue`**: Fila para BFS
- **`SET`**: Conjunto para verificações

## 🔍 Validações e Tratamento de Erros

- **Arquivo não encontrado**: Verifica existência do arquivo de entrada
- **Grafo não balanceado**: Alerta se graus de entrada ≠ saída
- **Sem circuito euleriano**: Verifica conectividade do grafo
- **Erros de compilação**: Script valida sucesso da compilação
- **Erros de execução**: Captura exceções e mostra stack trace

## 📊 Complexidade

- **Tempo de Construção**: O(E)
- **Verificação de Balanceamento**: O(V)
- **Algoritmo de Hierholzer**: O(E + V)
- **Cálculo de Custo**: O(E²) (devido à busca linear de pesos)
- **Geração Graphviz**: O(E)

## 🎨 Visualização

O arquivo `grafo_unifor.dot` gerado pode ser convertido para PDF usando:
```bash
dot -Tpdf grafo_unifor.dot -o resultado.pdf
```

O grafo mostra:
- Vértices numerados (0 a V-1)
- Arestas direcionadas com pesos
- Circuito euleriano destacado na legenda

## 📝 Notas Técnicas

- **Arestas Paralelas**: Suportadas pelo algoritmo
- **Self-loops**: Permitidos na implementação
- **Pesos**: Suportam valores double
- **Vértices**: Numerados de 0 a V-1
- **Encoding**: UTF-8 para suporte a caracteres especiais

## 🤝 Contribuição

Para contribuir com o projeto:
1. Faça fork do repositório
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## 📄 Licença

Este projeto é parte do Trabalho 3 da disciplina de Grafos - Universidade de Fortaleza.

---

**Autor**: [Seu Nome]
**Disciplina**: Grafos
**Instituição**: Universidade de Fortaleza
**Data**: Abril 2026
