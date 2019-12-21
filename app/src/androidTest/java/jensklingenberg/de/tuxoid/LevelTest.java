package jensklingenberg.de.tuxoid;

import jensklingenberg.de.tuxoid.model.element.Element;

public class LevelTest {



      static Element[][][] tst(){
           int[][][] levelE= {{{48, 0, 0}}};

          //levelEo[ebene][i][n] = Element.Companion.elementFactory(ElementType.BACKGROUND, ebene, i, n);                    }

          Element[][][] level = new Element[levelE.length][levelE[0].length][levelE[0][0].length];


          for (int z = 0; z < levelE.length; z++) {
              for (int y = 0; y < levelE[z].length; y++) {
                  for (int x = 0; x < levelE[z][y].length; x++) {
                      level[0][y][x]=Element.elementFactory(levelE[z][y][x],z,y,x);
                  }
              }
          }





          return level;
          }


      }




