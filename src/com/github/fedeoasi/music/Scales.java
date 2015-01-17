package com.github.fedeoasi.music;

public class Scales {
    public int[] scalaMaggiore = {0, 2, 2, 1, 2, 2, 2, 1};
    public int[] scalaMinNat = {0, 2, 1, 2, 2, 1, 2, 2};
    public int[] scalaMinArm = {0, 2, 1, 2, 2, 1, 3, 1};
    public int[] scalaMinMel = {0, 2, 1, 2, 2, 2, 2, 1};
    public int[] pentaMaggiore = {0, 2, 2, 3, 2, 3};
    public int[] pentaMinore = {0, 3, 2, 2, 3, 2};
    public int[] esatonale = {0, 2, 2, 2, 2, 2};
    public int[] tonoSemitono = {0, 2, 1, 2, 1, 2, 1, 2, 1};
    public int[] semitonoTono = {0, 1, 2, 1, 2, 1, 2, 1, 2};
    private Notes n;

    public Scales() {
        n = new Notes();
    }

    public String[] scala(String nota, int[] ts) {
        String[] scala = new String[7];
        if (!n.exists(nota)) return null;
        scala[0] = nota;
        for (int i = 1; i < 7; i++) {
            int distn = n.distance(scala[i - 1], n.nextNatural(scala[i - 1]));
            if (distn == ts[i])
                scala[i] = n.nextNatural(scala[i - 1]);
            else if (distn > ts[i])
                scala[i] = n.noteb(n.getIndex(scala[i - 1]) + ts[i]);
            else if (distn < ts[i])
                scala[i] = n.noted(n.getIndex(scala[i - 1]) + ts[i]);

            char[] n1 = {scala[i - 1].charAt(0)};
            char[] n2 = {scala[i].charAt(0)};
            String nota1 = new String(n1);
            String nota2 = new String(n2);
            if (nota2.equals(nota1)) {
                scala[i] = n.noteb2(n.getIndex(scala[i - 1]) + ts[i]);
            } else if (nota2.equals(n.nextNatural(n.nextNatural(nota1)))) {
                scala[i] = n.noted2(n.getIndex(scala[i - 1]) + ts[i]);
            }
        }
        return scala;
    }

    public String[] scalaMaggiore(String nota) {
        return scala(nota, scalaMaggiore);
    }

    public String[] scalaMinNat(String nota) {
        return scala(nota, scalaMinNat);
    }

    public String[] scalaMinArm(String nota) {
        return scala(nota, scalaMinArm);
    }

    public String[] scalaMinMel(String nota) {
        return scala(nota, scalaMinMel);
    }

    public int getNumAlterazioni(String[] scala) {
        if (scala == null) return -1;
        int num = 0;
        for (int i = 0; i < scala.length; i++)
            if (!n.isNatural(scala[i]))
                num++;
        return num;
    }

    public void printScala(String[] scala) {
        for (int i = 0; i < scala.length; i++)
            System.out.print(scala[i] + "  ");
        System.out.print(getNumAlterazioni(scala));
        System.out.println();
    }
}
