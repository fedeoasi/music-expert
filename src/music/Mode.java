package music;

public class Mode {
    String nome;
    String[] note;
    String tonica;
    int[] distanze = new int[8];
    int[] generatrice;
    int partenza;

    public Mode(String tonica, String nome, int[] generatrice, int partenza)
    {
        this.tonica = tonica;
        this.nome = nome;
        this.generatrice = generatrice;
        this.partenza = partenza;
        int temp;
        int[] gen = new int[7];
        for(int i=0; i<7; i++)
            gen[i]=generatrice[i+1];
        distanze[0] = 0;
        //System.out.print(distanze[0]);
        for(int i=1; i<gen.length+1; i++){
            temp = (partenza-2+i)% 7;
            distanze[i] = gen[temp];
            //System.out.print("  " + distanze[i]);
        }
        System.out.println();

        Scale s = new Scale();
        note = s.scala(tonica, distanze);

        /*for(int i=0; i<note.length; i++)
            System.out.print(note[i] + "  ");
        System.out.println();
        */

    }

    public String getTonica() {
        return tonica;
    }

    public void setTonica(String tonica) {
        this.tonica = tonica;
    }

    public String getNome() {
        return nome;
    }

    public String[] getNote() {
        return note;
    }

    public int[] getDistanze() {
        return distanze;
    }

    public int[] getGeneratrice() {
        return generatrice;
    }

    public int getPartenza() {
        return partenza;
    }

}
