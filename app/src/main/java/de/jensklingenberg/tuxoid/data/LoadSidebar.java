package de.jensklingenberg.tuxoid.data;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.jensklingenberg.tuxoid.ui.MainActivity;
import de.jensklingenberg.tuxoid.model.element.Element;

public class LoadSidebar {

    //Lädt die Sidebar von assets/lvl/

    private Element[][][] sidebarElement;

    //ebene war vorher level
    private int ebene;

    AssetManager assetManager;

    private LoadSidebar(){

    }

    public LoadSidebar(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    // TODO Auto-generated constructor stub

    public Element[][][] getLevelE() {
        return sidebarElement;
    }

    public Element[][][] createLevel(int aktLevel) {

        ebene = 0;
        String[] num = null;
        String[] colCount = null; //Anzahl Felder/Spalten

        int[] rowCount = new int[5];

        try {
            InputStream in = assetManager.open("sidebar/lvl" + aktLevel);

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.length() == 0) {
                    ebene++;
                }

                rowCount[ebene]++;
                colCount = line.split(",");
            }

            reader.close();
            in.close();

            InputStream in2 = assetManager.open("sidebar/lvl" + aktLevel);
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
            String line2;

            sidebarElement = new Element[ebene + 1][rowCount[0]][colCount.length];

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

                        Element ele = Element.Companion.elementFactory(Integer.parseInt(num[n]), ebene, i, n);

                        sidebarElement[ebene][i][n] = ele;
                    }

                    i++;
                }
            }
            reader.close();
            return sidebarElement;
        } catch (FileNotFoundException e) {
            return sidebarElement;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return sidebarElement;
            // Wird immer durchlaufen, ist allerdings optional
        }
    }
}