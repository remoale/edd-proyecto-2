package edu.ve.unimet.supermetromendeley;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Administra los artículos:
 * - carga desde archivo
 * - almacenamiento en HashTable
 * - índices por autor y palabra clave con árboles AVL.
 *
 * @author remo
 * @since Nov 23, 2025
 */
public class ArticleManager {

    private HashTable<Article> articles = new HashTable<>();
    private AvlTree authorsTree = new AvlTree();
    private AvlTree keywordsTree = new AvlTree();
    private HashTable<List<Article>> authorIndex = new HashTable<>();
    private HashTable<List<Article>> keywordIndex = new HashTable<>();

    public ArticleManager() {
        // precargar investigaciones
    }

    public Article getArticle(String title) {
        return articles.get(title);
    }

    public HashTable<Article> getArticlesTable() {
        return articles;
    }

    /**
     * Carga un artículo desde archivo y lo agrega al sistema.
     * 
     * @param file archivo de texto con el formato del enunciado
     * @return Article creado, o null si el archivo está vacío
     *         o si el título ya existía.
     * @throws FileNotFoundException si no se encuentra el archivo
     */
    public Article addArticleFromFile(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextLine()) {
                return null;
            }

            Article art = new Article();
            art.title = scanner.nextLine();

            // Validar que no exista el mismo resumen
            if (articles.get(art.title) != null) {
                return null;
            }

            int loading = 0; // 0: autores, 1: cuerpo, 2: palabras clave

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isBlank()) {
                    continue;
                }

                if (line.equals("Autores")) {
                    loading = 0;
                    continue;
                } else if (line.equals("Resumen")) {
                    loading = 1;
                    continue;
                } else if (line.startsWith("Palabras claves:")) {
                    loading = 2;
                }

                switch (loading) {
                    case 0 -> {
                        if (line.endsWith(" ")) {
                            line = line.substring(0, line.length() - 1);
                        }
                        art.authors.insert(line);
                    }
                    case 1 -> {
                        art.body += line;
                    }
                    case 2 -> {
                        line = line.substring(17);
                        String[] keywords = line.split(", ");

                        for (String keyword : keywords) {
                            if (keyword.endsWith(".")) {
                                keyword = keyword.substring(0, keyword.length() - 1);
                            }
                            art.keywords.insert(keyword);
                        }
                    }
                }
            }

            articles.put(art.title, art);

            updateAuthorIndex(art);
            updateKeywordIndex(art);

            return art;
        }
    }

    /**
     * Construye el texto de "Palabras claves: ..." con las frecuencias
     * para un artículo.
     */
    public String buildKeywordSummary(Article art) {
        if (art == null || art.keywords == null) {
            return "Palabras claves:";
        }

        String keywordsText = "Palabras claves: ";

        List<String>.Node<String> keywordNode = art.keywords.getFirstNode();

        while (keywordNode != null) {
            String keyword = keywordNode.value;
            int freq = art.getKeywordOccurrences(keyword);
            keywordsText += keyword + " (" + freq + " veces), ";
            keywordNode = keywordNode.next;
        }

        if (keywordsText.length() > "Palabras claves: ".length()) {
            keywordsText = keywordsText.substring(0, keywordsText.length() - 2);
        }

        return keywordsText;
    }
    
    public List<Article> searchByKeyword(String keyword) {
        List<Article> result = keywordIndex.get(keyword);
        if (result == null) {
            return new List<>();
        }
        return result;
    }

    public List<Article> searchByAuthor(String authorName) {
        List<Article> result = authorIndex.get(authorName);
        if (result == null) {
            return new List<>();
        }
        return result;
    }

    public List<String> listAllKeywordsInOrder() {
        List<String> result = new List<>();
        keywordsTree.toInorderList(result);
        return result;
    }
    
    private void updateAuthorIndex(Article art) {
        List<String>.Node<String> authorNode = art.authors.getFirstNode();

        while (authorNode != null) {
            String authorName = authorNode.value;

            authorsTree.insert(authorName);

            List<Article> bucket = authorIndex.get(authorName);
            if (bucket == null) {
                bucket = new List<>();
                authorIndex.put(authorName, bucket);
            }
            bucket.insert(art);

            authorNode = authorNode.next;
        }
    }

    private void updateKeywordIndex(Article art) {
        List<String>.Node<String> keywordNode = art.keywords.getFirstNode();

        while (keywordNode != null) {
            String keyword = keywordNode.value;

            keywordsTree.insert(keyword);

            List<Article> bucket = keywordIndex.get(keyword);
            if (bucket == null) {
                bucket = new List<>();
                keywordIndex.put(keyword, bucket);
            }
            bucket.insert(art);

            keywordNode = keywordNode.next;
        }
    }
}
