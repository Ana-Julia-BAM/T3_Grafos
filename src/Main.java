import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Caminho do arquivo conforme solicitado no T3
        String caminhoArquivo = "dados/entrada_eulerizada.txt";
        
        try {
            File file = new File(caminhoArquivo);
            if (!file.exists()) {
                System.out.println("Erro: Arquivo " + caminhoArquivo + " não encontrado.");
                return;
            }

            Scanner sc = new Scanner(file);
            int V = sc.nextInt();
            int E = sc.nextInt();

            // 1. CONSTRUÇÃO DO DÍGRAFO PONDERADO
            // Digraph armazena a estrutura; edgesList armazena os pesos
            Digraph G = new Digraph(V);
            List<DirectedEdge> edgesList = new ArrayList<>();

            for (int i = 0; i < E; i++) {
                int v = sc.nextInt();
                int w = sc.nextInt();
                double weight = sc.nextDouble();
                G.addEdge(v, w);
                edgesList.add(new DirectedEdge(v, w, weight));
            }
            sc.close();

            // INFORMAR A CONSTRUÇÃO (Impressão da Lista de Adjacência)
            System.out.println("=== Estrutura do Dígrafo Construído ===");
            System.out.println(G);

            // 2. INFORMAR OS GRAUS DE ENTRADA E SAÍDA
            System.out.println("=== Análise dos Vértices (Graus) ===");
            boolean estaBalanceado = true;
            for (int v = 0; v < V; v++) {
                int in = G.indegree(v);
                int out = G.outdegree(v);
                System.out.printf("Vértice %d: Entrada = %d, Saída = %d\n", v, in, out);
                
                // 3. VERIFICAR SE O GRAFO ESTÁ BALANCEADO
                if (in != out) {
                    estaBalanceado = false;
                }
            }

            if (!estaBalanceado) {
                System.out.println("\nAVISO: O grafo não está balanceado. O método de Hierholzer exige in-degree == out-degree.");
                return;
            }
            System.out.println("\nConfirmação: Grafo está balanceado.");

            // 4. EXECUTAR O MÉTODO DE HIERHOLZER
            DirectedEulerianCycle euler = new DirectedEulerianCycle(G);

            if (euler.hasEulerianCycle()) {
                System.out.println("\n=== Circuito Euleriano Encontrado ===");
                
                double custoTotal = 0;
                Integer ultimoVertice = null;
                List<Integer> percursoParaVisualizacao = new ArrayList<>();

                // 5. IMPRIMIR O CIRCUITO E CALCULAR O CUSTO TOTAL
                for (int v : euler.cycle()) {
                    percursoParaVisualizacao.add(v);
                    if (ultimoVertice != null) {
                        System.out.print(" -> ");
                        // Busca o peso na nossa construção original
                        for (int i = 0; i < edgesList.size(); i++) {
                            DirectedEdge e = edgesList.get(i);
                            if (e.from() == ultimoVertice && e.to() == v) {
                                custoTotal += e.weight();
                                edgesList.remove(i); // Remove para não repetir o mesmo peso em arestas paralelas
                                break;
                            }
                        }
                    }
                    System.out.print(v);
                    ultimoVertice = v;
                }

                System.out.println("\n\n-------------------------------------------");
                System.out.printf("CUSTO TOTAL DO CIRCUITO: %.2f\n", custoTotal);
                System.out.println("-------------------------------------------");

                // GERAR GRAPHVIZ (Para criar imagem/PDF)
                gerarGraphviz(V, caminhoArquivo, percursoParaVisualizacao, "grafo_unifor.dot");
                System.out.println("\nArquivo 'grafo_unifor.dot' gerado.");

            } else {
                System.out.println("\nO grafo é balanceado mas não possui ciclo euleriano (verifique a conectividade).");
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gera um arquivo .dot para visualização no Graphviz
     */
    public static void gerarGraphviz(int V, String originalFile, List<Integer> percurso, String outputDot) {
        try (PrintWriter out = new PrintWriter(outputDot)) {
            out.println("digraph G {");
            out.println("    rankdir=LR;");
            out.println("    node [shape=circle, fontname=\"Arial\"];");
            out.println("    edge [fontname=\"Arial\", fontsize=10];");

            // Relê o arquivo para pegar as arestas e desenhar
            Scanner sc = new Scanner(new File(originalFile));
            sc.nextInt(); sc.nextInt(); // Pula V e E
            while (sc.hasNextInt()) {
                int v = sc.nextInt();
                int w = sc.nextInt();
                double weight = sc.nextDouble();
                out.printf("    %d -> %d [label=\"%.1f\"];\n", v, w, weight);
            }
            sc.close();

            out.println("    label=\"\\nCircuito: " + percurso.toString() + "\";");
            out.println("    labelloc=b;");
            out.println("}");
        } catch (Exception e) {
            System.out.println("Erro ao gerar Graphviz: " + e.getMessage());
        }
    }
}