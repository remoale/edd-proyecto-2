/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ve.unimet.supermetromendeley;

/**
 *
 * @author biancazullo
 */
public class Article {
    public String title;
    public List<String> authors;
    public String body;
    public List<String> keywords;
    
    public Article() {
        this.authors = new List();
        this.keywords = new List();
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
