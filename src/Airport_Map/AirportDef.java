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

import java.util.Comparator;

/**
 *
 * @author 19fitzp
 */
public class AirportDef {

    private final double LATITUDE;
    private final double LONGITUDE;
    private final String AIRPORT_NAME;
    private final String AIRPORT_CODE; //IATA Code

    /**
     * Assigns necessary tags to data from CSV
     *
     * @param latitude
     * @param longitude
     * @param name
     * @param code
     */
    public AirportDef(String name, double longitude, double latitude, String code) {
        this.LATITUDE = latitude;
        this.LONGITUDE = longitude;
        this.AIRPORT_NAME = name;
        this.AIRPORT_CODE = code;
    }

    /**
     * Get latitude from other class
     *
     * @return latitude
     */
    public double getLat() {
        return LATITUDE;
    }

    /**
     * Get longitude from other class
     *
     * @return longitude
     */
    public double getLong() {
        return LONGITUDE;
    }

    /**
     * Get name from other class
     *
     * @return name
     */
    public String getName() {
        return AIRPORT_NAME;
    }

    /**
     * Get code from other class
     *
     * @return code
     */
    public String getCode() {
        return AIRPORT_CODE;
    }

    public static Comparator<AirportDef> CodeComparator = (AirportDef airport1, AirportDef airport2) -> {
        String adultGender1 = airport1.getCode().toUpperCase();
        String adultGender2 = airport2.getCode().toUpperCase();

        //ascending order
        return adultGender1.compareTo(adultGender2);
    };

}
