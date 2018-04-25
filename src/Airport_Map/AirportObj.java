/* 
 * Copyright (C) 2018 Patrick Fitz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Airport_Map;

import java.util.List;
import java.io.IOException;
import java.io.FileReader;
import openCSV.CSVReader;

/**
 *
 * @author 19fitzp
 */
public class AirportObj {

    private AirportDef airportTemp;

    private double y;
    private double x;

    private final List<String[]> LIST;

    private final String importName;

    private String[][] dataArr;

    /**
     * Constructor for AirportObj handler
     * @throws java.io.IOException
     */
    public AirportObj() throws IOException {
        importName = "Airports.csv";
        CSVReader reader = new CSVReader(new FileReader(importName));

        LIST = reader.readAll(); //gets LIST of values 
        dataArr = new String[LIST.size()][]; //makes string LIST 
        dataArr = LIST.toArray(dataArr);  //makes array 

    }

    /**
     * Creates airport from CSV with GPS coordinates and airport name
     *
     * @param j
     * @return airport object
     * @throws IOException
     */
    AirportDef createAirports(int j) {
        airportTemp = new AirportDef(dataArr[j + 1][0], Double.parseDouble(dataArr[j + 1][1]), Double.parseDouble(dataArr[j + 1][2]), dataArr[j + 1][3]); //grabs data from indiv "cells" in csv
        return airportTemp; //passes airport object back
    }

    /**
     * Finds "x" position on image from latitude. This is image-specific
     *
     * @param j
     * @return x position
     */
    double correctX(AirportDef j) {
        x = ((120.0 - j.getLat())) * (387.0 / 50.0) + 64.0;
        return x;
    }

    /**
     * Finds "y" position on image from longitude. This is image-specific
     *
     * @param j
     * @return y position
     */
    double correctY(AirportDef j) {
        y = (50.5 - j.getLong()) * (244.0 / 25.0) + 44.0;
        return y;
    }
}
