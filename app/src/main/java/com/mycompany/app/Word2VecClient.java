package com.mycompany.app;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import java.util.Collection;
import java.util.Arrays;

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
          String type;
          while ((type = in.readLine()) != null) {
            String query = in.readLine();
            if (type.equals("word")) {
                Collection<String> lst = vec.wordsNearest(query, 10);
                out.println(lst); 
            }
            else if (type.equals("assoc")) {
                String tmp = query.split("::")[0];
                String a = tmp.split(":")[0];
                String b = tmp.split(":")[1];
                String x = query.split("::")[1];
                Collection<String> lst = vec.wordsNearest(Arrays.asList(b, x), Arrays.asList(a), 10);
                out.println(lst); 
            }
            else {
                out.println("Invalid command: " + type);
            }
          }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
