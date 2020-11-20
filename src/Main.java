import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException{
        executa (8,8,true,9,3,3);
        executa (16,4,true,9,4,2);
        executa (8,8,false, 12,0,3);
        executa(16,4,false,13,0,2);

    }

    public static void executa(int linhas, int palavras, boolean tipo, int tag, int linha, int palavra) throws FileNotFoundException{
        int selectionByte = 1;
        boolean converto = true;
        //cria cache Cache(linhas,palavras,direto=true || associativo=false)
        Cache cache = new Cache(linhas,palavras,tipo);
        //seta scanner pro arquivo que contem os hexas de entrada
        File arq = new File("memory_access.txt");
        Scanner in = new Scanner(arq);
        int  palavrasLidas=0;

        //enquanto houver entradas (hexas), adiciona eles na cache e printa o estado da mesma
        while(in.hasNext()){
            String entrie = in.next();
            //Endereco(hexa, bitsTag, bitsLinha, bitsPalavra, bitsSelecao, converterBinario=true)
            Endereco a = new Endereco(entrie,tag,linha,palavra,selectionByte,converto);
            //System.out.println("Execução: "+ palavrasLidas +"   Endereço recebido: "+entrie);
            palavrasLidas++;
            cache.add(a);
        }

        //mostra o historico de hits e misses, fecha o scanner e mostra a porcentagem de acerto
        cache.showHistorico();
        in.close();
        double x = cache.percentAcertos()*100;
        System.out.printf("%.2f",x);
        System.out.println("% de acerto na cache.");

    }

}
