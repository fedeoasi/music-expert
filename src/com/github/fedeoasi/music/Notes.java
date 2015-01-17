package com.github.fedeoasi.music;

public class Notes {
    public String[] noteNaturali = {"A", "B", "C", "D", "E", "F", "G"};
    public String[] noted = {"A","A#","B","B#","C#","D","D#","E","E#","F#","G","G#"};
    public String[] noteb = {"A","Bb","Cb","C","Db","D","Eb","Fb","F","Gb","G","Ab"};
    public String[] noted1 = {"A","A#","B","C","C#","D","D#","E","F","F#","G","G#"};
    public String[] noteb1 = {"A","Bb","B","C","Db","D","Eb","E","F","Gb","G","Ab"};
    public String[] noted2 = {"G##","A#","A##","B#","C#","C##","D#","D##","E#","F#","F##","G#"};
    public String[] noteb2 = {"Bbb","Bb","Cb","Dbb","Db","Ebb","Eb","Fb","Gbb","Gb","Abb","Ab"};

    public boolean isNaturale(String nota){
        for(int i=0; i<noteNaturali.length; i++)
            if(noteNaturali[i].equals(nota))
                return true;
        return false;
    }

    public String next(String nota){
        for(int i=0; i<noteNaturali.length; i++)
            if(noteNaturali[i].equals(nota))
                if(i+1<7)
                return noteNaturali[i+1];
                else return noteNaturali[(i+1)%7];
        char[] c = {nota.charAt(0)};
        return next(new String(c));
    }

    public int controllo(String nota,String[] note){
        for(int i=0; i<note.length; i++)
            if(note[i].equals(nota))
                return i;
        return -1;
    }

    public int distanza(String nota1, String nota2) {
        int n1=-1,n2=-1;
        //System.out.println(nota1 + "  " + nota2);
            n1=controllo(nota1,noted1);
            if(n1==-1){
                n1=controllo(nota1,noteb1);
            }
            if(n1==-1){
                n1=controllo(nota1,noted);
            }
            if(n1==-1){
                n1=controllo(nota1,noteb);
            }
            if(n1==-1){
                n1 = controllo(nota1,noted2);
            }
            if(n1==-1){
                n1 = controllo(nota1,noteb2);
            }
            if(n1==-1)
                {
                System.out.print(nota1 + "  " + nota2 + " ");
                System.out.println("Errore nota1");
                return -1;
                }

            n2=controllo(nota2,noted1);
            if(n2==-1){
                n2=controllo(nota2,noteb1);
            }
            if(n2==-1){
                n2=controllo(nota2,noted);
            }
            if(n2==-1){
                n2=controllo(nota2,noteb);
            }
            if(n2==-1){
                n2 = controllo(nota2,noted2);
            }
            if(n2==-1){
                n2 = controllo(nota2,noteb2);
            }
            if(n2==-1)
                {
                System.out.print(nota1 + "  " + nota2 + " ");
                System.out.println("Errore nota2");
                return -1;
                }

        if(n2>=n1)
            return n2-n1;
        else return 12+n2-n1;
    }

    public boolean esiste(String nota){
        return true;
    }

    public int getIndiceNaturale(String nota){
        int indice=-1;
        indice = controllo(nota,noteNaturali);
        if(indice==-1)
        {
        System.out.println("bu!Errore nota "+ nota);
        return -1;
        }
    return indice;
    }

    public int getIndice(String nota){
        int indice=-1;
        indice = controllo(nota,noted1);
            if(indice==-1){
                indice = controllo(nota,noteb1);
            }
            if(indice==-1){
                indice = controllo(nota,noted);
            }
            if(indice==-1){
                indice = controllo(nota,noteb);
            }
            if(indice==-1){
                indice = controllo(nota,noted2);
            }
            if(indice==-1){
                indice = controllo(nota,noteb2);
            }
            if(indice==-1)
                {
                System.out.println("Errore nota");
                return -1;
                }
            return indice;
    }

    public String noteb(int i) {
        if(i<12) return noteb[i];
        else return noteb[i%12];
    }

    public String noted(int i) {
        if(i<12) return noted[i];
        else return noted[i%12];
    }

    public String noteb2(int i) {
        if(i<12) return noteb2[i];
        else return noteb2[i%12];
    }

    public String noted2(int i) {
        if(i<12) return noted2[i];
        else return noted2[i%12];
    }

    public String radice(String nota){
        char[] c = {nota.charAt(0)};
        return new String(c);
    }

    public static void main(String[] args){
        Notes n = new Notes();
        System.out.println(n.isNaturale("B"));
        System.out.println(n.isNaturale("A#"));
        System.out.println();
        System.out.println(n.next("B"));
        System.out.println(n.next("E"));
        System.out.println(n.next("C#"));
        System.out.println(n.next("E#"));
        System.out.println(n.next("Bb"));
        System.out.println();
        System.out.println(n.distanza("C","D"));
        System.out.println(n.distanza("Bb","Bb"));
        System.out.println(n.distanza("C#","Db"));
        System.out.println(n.distanza("Bb","Ab"));
        System.out.println(n.distanza("D","C"));

        Scales s = new Scales();

        System.out.println("Giro delle Quinte");
        s.printScala(s.scalaMaggiore("C"));
        s.printScala(s.scalaMaggiore("G"));
        s.printScala(s.scalaMaggiore("D"));
        s.printScala(s.scalaMaggiore("A"));
        s.printScala(s.scalaMaggiore("E"));
        s.printScala(s.scalaMaggiore("B"));
        s.printScala(s.scalaMaggiore("F#"));
        s.printScala(s.scalaMaggiore("C#"));

        System.out.println("Giro delle quarte");
        s.printScala(s.scalaMaggiore("C"));
        s.printScala(s.scalaMaggiore("F"));
        s.printScala(s.scalaMaggiore("Bb"));
        s.printScala(s.scalaMaggiore("Eb"));
        s.printScala(s.scalaMaggiore("Ab"));
        s.printScala(s.scalaMaggiore("Db"));
        s.printScala(s.scalaMaggiore("Gb"));
        s.printScala(s.scalaMaggiore("Cb"));

        System.out.println("Scale minori di la");
        s.printScala(s.scalaMinNat("A"));
        s.printScala(s.scalaMinArm("A"));
        s.printScala(s.scalaMinMel("A"));

        System.out.println("Scale minori di sol");
        s.printScala(s.scalaMinNat("G"));
        s.printScala(s.scalaMinArm("G"));
        s.printScala(s.scalaMinMel("G"));

        System.out.println("Scale minori di re");
        s.printScala(s.scalaMinNat("D"));
        s.printScala(s.scalaMinArm("D"));
        s.printScala(s.scalaMinMel("D"));

        new Player(s.scalaMaggiore);
        new Player(s.scalaMinNat);
        new Player(s.scalaMinArm);
        new Player(s.scalaMinMel);

        new Chord("C","");
        new Chord("C","M7");
        new Chord("D","M13");
        new Chord("G","M9");
        new Chord("A","");
        new Chord("A","m");
        new Chord("A","m6");
        new Chord("A","m13");

        System.out.println(n.distanza("F","A"));

        System.out.println(n.isNaturale("C"));
        System.out.println(n.isNaturale("C#"));
    }

    public int ottava(String nome, int altezza) {
        System.out.println(nome+ " "+ alterazione(nome)+ " "+ altezza);
        if(isNaturale(nome))
            return ((altezza-45)/12);
        if(alterazione(nome).equals("b"))
            return ((altezza-44)/12);
        if(alterazione(nome).equals("#"))
            return ((altezza-46)/12);
        if(alterazione(nome).equals("bb"))
            return ((altezza-43)/12);
        if(alterazione(nome).equals("##"))
            return ((altezza-47)/12);
        return 0;

    }

    public String alterazione(String nome){
        String alterazione = "";
        if(!isNaturale(nome))
        {
            char[] nomeNota = nome.toCharArray() ;
            if(nomeNota.length==2){
                char[] alt= new char[1];
                alt[0] = nomeNota[1];
                alterazione = new String(alt);
            }
            else if(nomeNota.length==3){
                    char[] alt= new char[2];
                    alt[0] = nomeNota[1];
                    alt[1] = nomeNota[2];
                    alterazione = new String(alt);
            }

    }
        return alterazione;
}
}