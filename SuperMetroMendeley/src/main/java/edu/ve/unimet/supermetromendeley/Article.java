package edu.ve.unimet.supermetromendeley;

/**
 * Representa un artículo de investigación con título, autores,
 * cuerpo del resumen y palabras clave asociadas.
 * 
 * @author biancazullo
 */
public class Article {
    public String title;
    public List<String> authors;
    public String body;
    public List<String> keywords;
    
    public Article() {
        this.title = "";
        this.authors = new List<String>();
        this.keywords = new List<String>();
        this.body = "";
    }
    
    public int getKeywordOccurrences(String keyword) {
        int count = 0;
        int lastIndex = 0;
        int keywordLen = keyword.length();
        while ((lastIndex = body.indexOf(keyword, lastIndex)) != -1) {
            count++;
            lastIndex += keywordLen;
        }
        return count;
    }
}
