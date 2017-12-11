package br.ufs.dcomp.ExemploHTTP;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Exemplo de requisição GET do HTTP com impressão do cabeçalho da resposta na saída padrão e
 * escrita do conteúdo em arquivo.
 * Obs: Exemplo em mais baixo nível com o uso de sockets TCP e sem o uso de wrappers nos canais de entrada e saída.
 * 
 * @author Tarcisio Rocha (Prof. DCOMP/UFS)
 */
public class HTTPClient2
{
    public static void main( String[] args ) throws Exception
    {
        Socket sock = new Socket("www.ic.unicamp.br", 80);
        
        InputStream in = sock.getInputStream();
        OutputStream out = sock.getOutputStream();

        String CRLF = "\r\n";
        
        String msg = "GET /sites/default/files/logo_unicamp_novo_0.png HTTP/1.1" + CRLF +
                     "Host: www.ic.unicamp.br" + CRLF +
                     CRLF;
                     
        out.write(msg.getBytes());
        
        /*=======================================================================
         * Leitura de todas as linhas do cabeçalho da resposta HTTP
         */
        String line;
        do {
            char c; 
            line = ""; 
            // Leitura de uma única linha do cabeçalho de resposta HTTP
            do {
                c = (char) in.read(); 
                line += c;
            } while (c != '\n');
            System.out.print(line);
        } while (line.trim().length() > 0); // Para quando a linha lida é de comprimento 0 indicando o fim do cabeçalho

        /*=======================================================================
         * Leitura do conteúdo da resposta HTTP e escrita em arquivo
         */    

        // Arquivo onde o conteúdo da resposta será escrito
        File file = new File("/home/ubuntu/workspace/sistemas-distribuidos/java/ExemploHTTPJava/logo_unicamp_novo_0.png");
        FileOutputStream fos = new FileOutputStream(file);
        
        System.out.println("Downloading...");
        byte[] buf = new byte[1000];
        int len;
        while ((len = in.read(buf)) > 0){
            fos.write(buf, 0, len);
            fos.flush();
            System.out.println(len);
        }
        fos.close();
        System.out.println("Download complete!");
    } 
        
}
