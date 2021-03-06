import java.util.ArrayList;
public class Cache{
    private Endereco[][] memoria;
    private int linhas;
    private int palavras;
    private boolean direto;
    private ArrayList<String> historico;
    private int hits;
    private int count;
    private int linhasUsadas;

    public Cache(int l, int p, boolean d){
        linhas=l;
        palavras=p;
        memoria = new Endereco[linhas][palavras];
        direto=d;
        historico = new ArrayList<String>();
        hits=0;
        count=0;
        linhasUsadas=0;
    }
    public void add(Endereco a){
        if(direto){
            int linha = converteBinInt(a.linha());
            int palavra = converteBinInt(a.palavra());
            if(memoria[linha][palavra]!=null && memoria[linha][palavra].end.equalsIgnoreCase(a.end)){
                historico.add(a + " HIT");
                hits++;
            }
            else{
                historico.add(a + " MISS");
                for(int i=0;i<palavras;i++){
                    String bin = a.tag()+a.linha()+palavraGerada(i)+a.selec();
                    memoria[linha][i]=new Endereco(bin,a.tagT,a.linhaT,a.palavraT,a.selecT,false);
                }
            }
        }
        else { //é associativo
            if(hitEndereco(a)){
                historico.add(a + " HIT");
                hits++;
            }
            else{
                historico.add(a + " MISS");
                if(linhas>linhasUsadas){
                    for(int i=0;i<palavras;i++){
                        String bin = a.tag()+a.linha()+palavraGerada(i)+a.selec();
                        memoria[count][i]=new Endereco(bin,a.tagT,a.linhaT,a.palavraT,a.selecT,false);
                    }
                    count++;
                    linhasUsadas++;
                    if(linhasUsadas==linhas)count=1;
                }
                else{
                    if(count==linhas)count=0;
                    for(int i=0;i<palavras;i++){
                        String bin = a.tag()+a.linha()+palavraGerada(i)+a.selec();
                        memoria[count][i]=new Endereco(bin,a.tagT,a.linhaT,a.palavraT,a.selecT,false);
                    }
                    count++;
                }
            }
        }
        this.print();
    }
    public boolean hitEndereco(Endereco a){
        for(int i = 0;i<linhas;i++){
            for (int j=0;j<palavras;j++){
                if(memoria[i][j]!=null && memoria[i][j].end.equalsIgnoreCase(a.end)) return true;
            }
        }
        return false;
    }
    public String palavraGerada(int n){
        if (palavras==8){
            if (n==0) return "000";
            else if (n==1) return "001";
            else if (n==2) return "010";
            else if (n==3) return "011";
            else if (n==4) return "100";
            else if (n==5) return "101";
            else if (n==6) return "110";
            else if (n==7) return "111";
            else System.out.println("1deu ruim vei nao ta generico");
        }
        /*
        if(palavras==2){
            if(n==0){
                return "0";
            }
            else if(n==1){
                return "1";
            }
            else{
                System.out.println("1deu ruim vei nao ta generico");
            }
        }
        */

        else if(palavras==4){
            if(n==0){
                return "00";
            }
            else if(n==1){
                return "01";
            }
            else if(n==2){
                return "10";
            }
            else if(n==3){
                return "11";
            }
            else{
                System.out.println("2deu ruim vei nao ta generico");
            }
        }
        else{
            System.out.println("3deu ruim vei nao ta generico");
        }
        return "";
    }
    public static int converteBinInt(String l){
        int res = 0;
        int tam = l.length()-1;
        for(int i=0;i<l.length();i++){
            res+= Integer.parseInt(""+l.charAt(i))*Math.pow(2,tam--);
        }
        return res;
    }
    public void print(){
        for(int i = 0;i<linhas;i++){
            String linha = "Linha: "+i+" ";
            for(int j = 0;j<palavras;j++){
                linha+=memoria[i][j]+" ";
            }
            //System.out.println(linha);
        }
    }
    public void showHistorico(){
        System.out.println(historico);
        System.out.println("Hits: " + hits);
    }
    public double percentAcertos(){
        return hits/Double.parseDouble(""+historico.size());
    }
}