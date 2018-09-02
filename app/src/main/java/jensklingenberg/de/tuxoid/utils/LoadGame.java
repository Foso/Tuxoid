package jensklingenberg.de.tuxoid.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jensklingenberg.de.tuxoid.model.Element.Element;
import jensklingenberg.de.tuxoid.model.Element.ElementType;

/*Loads the Level from Textfile
 *
 */



public class LoadGame {
    private Element[][][] levelE;
    private Element[][][] levelEo;
    private int ebene;
    private LevelLoadListener loadListener;
    private Context context;

    public LoadGame(Context context){
        this.context=context;
    }


    public void setListener(LevelLoadListener listener){
        this.loadListener=listener;
    }


    public void createLevel(int aktLevel) throws IOException {
        AssetManager assetManager = context.getAssets();
        ebene = 0;
        String[] num = null;
        int colCount = 0; // Anzahl Felder/Spalten
        int[] rowCount = new int[5];

        InputStream in = assetManager.open("lvl/lvl" + aktLevel);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while ((line = reader.readLine()) != null)
        {

            if (line.length() == 0)
            {
                ebene++;
            }

            rowCount[ebene]++;
            colCount = line.split(",").length;

        }

        reader.close();
        in.close();

        InputStream in2 = assetManager.open("lvl/lvl" + aktLevel);
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
        String line2;

        levelE = new Element[ebene + 1][rowCount[0]][colCount];
        levelEo = new Element[ebene + 1][rowCount[0]][colCount];

        int i = 0;
        ebene = 0;

        while ((line2 = reader2.readLine()) != null)
        {

            if (line2.length() == 0)
            {
                ebene++;
                i = 0;
            }
            else
            {
                line2 = line2.replaceAll("\\s", "");
                num = line2.split(",");

                for (int n = 0; n < num.length; n++)
                {
                    Element ele = Element.Companion.elementFactory(Integer.parseInt(num[n]), ebene,i, n);

                    levelE[ebene][i][n] = ele;
                    levelEo[ebene][i][n] = ele;

                    if (levelEo[ebene][i][n].isRemovable()) {
                        levelEo[ebene][i][n] = Element.elementFactory(ElementType.BACKGROUND, ebene, i, n);
                    }

                }

                i++;
            }

        }
        reader.close();

        if (loadListener != null) {
            loadListener.onLevelLoaded(levelE,levelEo);
        }

    }

    public Element[][][] getLevelE() {
        return levelE;
    }

    public Element[][][] getLevelEo() {
        return levelEo;
    }


}
