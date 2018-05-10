package com.github.fedeoasi.music;

public class Batteria {

    public Batteria(Player p, int numBattute, int stile) {
        if (stile == 1)
            for (int i = 0; i < numBattute; i++) {
                //charleston
                for (int j = 0; j < 8; j++)
                    p.creaNota2(9, 42, 48 * i + 6 * j, 48 * i + 6 * (j + 1));
                //cassa
                p.creaNota2(9, 35, 48 * i, 48 * i + 6);
                p.creaNota2(9, 35, 48 * i + 24, 48 * i + 30);
                p.creaNota2(9, 35, 48 * i + 30, 48 * i + 36);
                //rullante
                p.creaNota2(9, 38, 48 * i + 12, 48 * i + 18);
                p.creaNota2(9, 38, 48 * i + 36, 48 * i + 42);
            }
        else if (stile == 2) {
            for (int i = 0; i < numBattute; i++) {
                for (int j = 0; j < 4; j++)
                    p.creaNota2(9, 42, 48 * i + 12 * j, 48 * i + 12 * (j + 1));
            }
        } else if (stile == 3) {
            for (int i = 0; i < numBattute; i++) {
                //ride
                for (int j = 0; j < 8; j++)
                    p.creaNota2(9, 51, 48 * i + 6 * j, 48 * i + 6 * (j + 1));
                //cassa
                p.creaNota2(9, 35, 48 * i, 48 * i + 6);
                p.creaNota2(9, 35, 48 * i + 18, 48 * i + 24);
                p.creaNota2(9, 35, 48 * i + 30, 48 * i + 36);
                //rullante
                p.creaNota2(9, 40, 48 * i + 12, 48 * i + 18);
                p.creaNota2(9, 40, 48 * i + 36, 48 * i + 42);
            }
        } else if (stile == 4) {
            for (int i = 0; i < numBattute; i++) {
                //ride
                for (int j = 0; j < 8; j++)
                    p.creaNota2(9, 53, 48 * i + 6 * j, 48 * i + 6 * (j + 1));

                for (int j = 0; j < 2; j++) {
                    //cassa
                    p.creaNota2(9, 35, 48 * i + 24 * j, 48 * i + 6 + 24 * j);
                    p.creaNota2(9, 35, 48 * i + 6 + 24 * j, 48 * i + 12 + 24 * j);
                    //rullante
                    p.creaNota2(9, 40, 48 * i + 12 + 24 * j, 48 * i + 18 + 24 * j);
                }
                //rullante
                if (i % 4 == 3)
                    for (int j = 0; j < 2; j++)
                        p.creaNota2(9, 40, 48 * i + 42 + 3 * j, 48 * i + 45 + 3 * j);

            }
        } else if (stile == 5) {
            //charleston
            for (int i = 0; i < numBattute; i++) {
                //charlie
                for (int j = 0; j < 12; j++)
                    p.creaNota2(9, 42, 48 * i + 4 * j, 48 * i + 4 * (j + 2));

                for (int j = 0; j < 2; j++) {
                    //cassa
                    p.creaNota2(9, 35, 48 * i + 24 * j, 48 * i + 6 + 24 * j);
                    p.creaNota2(9, 35, 48 * i + 6 + 24 * j, 48 * i + 12 + 24 * j);
                    //rullante
                    p.creaNota2(9, 38, 48 * i + 12 + 24 * j, 48 * i + 18 + 24 * j);
                }

            }
        } else if (stile == 6) {
            for (int i = 0; i < numBattute; i++) {
                //ride
                for (int j = 0; j < 4; j++)
                    p.creaNota2(9, 53, 48 * i + 12 * j, 48 * i + 12 * (j) + 4);
                p.creaNota2(9, 53, 48 * i + 44, 48 * i + 48);
            }
        } else if (stile == 7) {
            for (int i = 0; i < numBattute; i++) {
                //ride
                for (int j = 0; j < 4; j++)
                    p.creaNota2(9, 51, 48 * i + 12 * j, 48 * i + 12 * (j) + 4);
                if (i % 2 == 0)
                    p.creaNota2(9, 51, 48 * i + 44, 48 * i + 48);
                if (i % 3 == 1)
                    p.creaNota2(9, 51, 48 * i + 8, 48 * i + 12);
            }
        }
    }
}
