package de.jensklingenberg.tuxoid.utils;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.jensklingenberg.tuxoid.interfaces.Removable;
import de.jensklingenberg.tuxoid.model.element.Element;
import de.jensklingenberg.tuxoid.model.element.ElementType;

/*Loads the Level from Textfile
 *
 */


public class LoadGame {
    private Element[][][] levelE;
    private Element[][][] levelEo;
    private int ebene;
    private LevelLoadListener loadListener;
    private AssetManager assetManager;


    public LoadGame(AssetManager assetManager) {
        this.assetManager = assetManager;
    }


    public void setListener(LevelLoadListener listener) {
        this.loadListener = listener;
    }

    public void createLevel(int aktLevel) throws IOException {
        createLevel(String.valueOf(aktLevel));
    }

    public void createLevel(String aktLevel) throws IOException {
        ebene = 0;
        String[] num = null;
        int colCount = 0; // Anzahl Felder/Spalten
        int[] rowCount = new int[5];

        InputStream in = assetManager.open("lvl/lvl" + aktLevel);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;

        while ((line = reader.readLine()) != null) {

            if (line.length() == 0) {
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

        while ((line2 = reader2.readLine()) != null) {

            if (line2.length() == 0) {
                ebene++;
                i = 0;
            } else {
                line2 = line2.replaceAll("\\s", "");
                num = line2.split(",");

                for (int n = 0; n < num.length; n++) {
                    Element ele = Element.elementFactory(Integer.parseInt(num[n]), ebene, i, n);

                    levelE[ebene][i][n] = ele;
                    levelEo[ebene][i][n] = ele;

                    if (levelEo[ebene][i][n] instanceof Removable) {
                        levelEo[ebene][i][n] = Element.elementFactory(ElementType.BACKGROUND, ebene, i, n);
                    }

                }

                i++;
            }

        }
        reader.close();

        if (loadListener != null) {
            loadListener.onLevelLoaded(levelE, levelEo);
        }

    }

    public Element[][][] getLevelE() {
        return levelE;
    }

    public Element[][][] getLevelEo() {
        return levelEo;
    }


}