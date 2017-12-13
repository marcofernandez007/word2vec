package com.mycompany.app;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import java.util.Collection;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class Word2VecClient {
    public static void main(String[] args) {

        Word2Vec vec = WordVectorSerializer.readWord2VecModel(args[0]);

        int portNumber = 5000;
        try (
          ServerSocket ss = new ServerSocket(portNumber);
          Socket cs = ss.accept();
          BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
          PrintWriter out = new PrintWriter(cs.getOutputStream(), true);                   
          
        ) {
          String word;
          while ((word = in.readLine()) != null) {
            Collection<String> lst = vec.wordsNearest(word, 10);
            out.println(lst); 
          }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
