package br.ufs.dcomp.ExemploHTTP;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Exemplo de requisição GET do HTTP com obtenção do cabeçalho da resposta somente.
 * Obs: Este exemplo usa uma abordagem de mais baixo nível com o uso de sockets TCP e sem o
 * uso de wrappers nos canais de entrada e saída.
 * 
 * @author Tarcisio Rocha (Prof. DCOMP/UFS)
 */
public class HTTPClient1
{
    public static void main( String[] args ) throws Exception
    {
        Socket sock = new Socket("www.ic.unicamp.br", 80);
        
        InputStream in = sock.getInputStream();
        OutputStream out = sock.getOutputStream();

        String CRLF = "\r\n";
        
        String msg = "GET / HTTP/1.1" + CRLF +
                     "Host: www.ic.unicamp.br" + CRLF +
                     CRLF;

        // Envio da requisição HTTP             
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
                c = (char) in.read(); // Como o cabeçalho do HTTP é ASCII ele pode ser mapeado para char byte a byte
                line += c;
            } while (c != '\n');
            System.out.print(line);
        } while (line.trim().length() > 0); // Finaliza quando a linha lida é de comprimento 0, indicando o fim do cabeçalho
        //} while (true); // Forma incorreta (dirty), porém rápida, de exibir cabeçalho e conteúdo
    } 
}
