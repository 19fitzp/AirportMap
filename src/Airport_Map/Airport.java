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

import java.io.IOException;

import java.io.File;

import java.awt.EventQueue;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Dimension;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.util.Arrays;

public class Airport extends JFrame implements ActionListener {

    private static AirportDef[] airports;

    private static AirportObj coord;

    private static String airportName[];
    private static double latitude[];
    private static double longitude[];
    private static String localCode[];

    private final BackGroundPanel BACKGROUND_PANEL;

    private final BufferedImage BACKGROUND;

    private int x1, x2, y1, y2;

    private static JFrame mainFrame;

    private Panel calc;

    private TextField input1; //first IATA code
    private TextField input2; //second IATA code
    private TextField output; //distance

    private Airport() throws IOException {
        this.BACKGROUND_PANEL = new BackGroundPanel();
        this.BACKGROUND = ImageIO.read(new File("USMap.png")); //background image
        init();
        initMap();
    }

    private void init() {
        requestFocus(); //sets focus to frame

        setPreferredSize(new Dimension(492, 333));  //sets size of frame to map size

        setLayout(new BorderLayout()); //grid based on pre-determined page locations

        add(BACKGROUND_PANEL, BorderLayout.CENTER); //graphics panel

        calc = new Panel();

        input1 = new TextField(15); //field for first airport

        calc.add(input1);

        input1.setText("Airport 1");
        input1.addFocusListener(new FocusListenerImpl(true));

        input2 = new TextField(15); //field for second airport

        calc.add(input2);

        input2.setText("Airport 2");
        input2.addFocusListener(new FocusListenerImpl(false));

        Button distance = new Button("Calc Distance"); //creates button to calc distance
        calc.add(distance);

        output = new TextField(10); //field for distance output
        output.setEditable(false);
        calc.add(output);

        output.setText("0");

        distance.addActionListener(this); //tells the button to perform the action detailed within "this" class

        input1.setText("Airport 1");

        add(calc, BorderLayout.PAGE_END); //adds calc panel to bottom of frame

        pack(); //maintains size of graphics objects

        setTitle("Airport Distance Calculator");

        distance.requestFocusInWindow(); //sets default focus to button

        setLocationRelativeTo(null); //centers on screen

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

        setResizable(false);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1) / 2;
        double dLon = Math.toRadians(lon2 - lon1) / 2;

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        return 6378.1 * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(dLat), 2) + Math.pow(Math.sin(dLon), 2) * Math.cos(lat1) * Math.cos(lat2)));
    }

    private void distance(String airport1, String airport2) {
        //variables for distance
        double firstLat = 0;
        double secondLat = 0;
        double firstLong = 0;
        double secondLong = 0;
        double distanceKM;
        double distanceMI;

        //resets coord values for drawing in points on map
        x1 = 0;
        x2 = 0;
        y1 = 0;
        y2 = 0;

        String firstAirportName = airport1; //string to select which airport's data is needed

        for (AirportDef airport : airports) //scans through array of airports
        {
            String checkName = airport.getCode(); //gets name of current airport

            if (checkName.equals(firstAirportName)) //checks if current airport's IATA code is the same as the desired IATA code
            {
                //sets lat and long, then uses coord obj to get x/y coords for point on map
                firstLat = airport.getLong();
                firstLong = airport.getLat();
                x1 = (int) coord.correctX(airport);
                y1 = (int) coord.correctY(airport);
            }
        }

        String secondAirportName = airport2; //string to select which airport's data is needed

        for (AirportDef airport : airports) //scans through array of airports
        {
            String checkName = airport.getCode(); //gets name of current airport

            if (checkName.equals(secondAirportName)) //checks if current airport's IATA code is the same as the desired IATA code
            {
                //sets lat and long, then uses coord obj to get x/y coords for point on map
                secondLat = airport.getLong();
                secondLong = airport.getLat();
                x2 = (int) coord.correctX(airport);
                y2 = (int) coord.correctY(airport);
            }
        }
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);

        distanceKM = haversine(firstLat, firstLong, secondLat, secondLong); //uses haversine formula to get distance in kilometers
        distanceMI = distanceKM * 0.621371192; //converts distance to miles
        if (x1 == 0 || y1 == 0) {
            output.setText("?Airport1"); //errors if airport1 has a coord of 0
        } else if (x2 == 0 || y2 == 0) {
            output.setText("?Airport2"); //errors if airport2 has a coord of 0
        } else {
            output.setText(String.format("%.2f", distanceMI) + " miles"); //sets output text to distance in miles
        }
    }

    /*
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
*/
    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            try {
                mainFrame = new Airport();
            } catch (IOException ie) {
                System.err.print(ie);
            } finally {
                mainFrame.setVisible(true);
            }
        });
    }

    /**
     * Initializes map components
     */
    private static void initMap() {
        coord = null;
        try {
            coord = new AirportObj(); //creates coord obj, class that handles indiv. airport objects
        } catch (IOException e) {
            System.err.println("No file");
        }

        airports = new AirportDef[22401]; //creates empty array for airports

        for (int j = 0; j < 22401; j++) //scans through array of airports
        {
            airports[j] = coord.createAirports(j); //creates each airport using coord obj
            //System.out.println(j);
        }

        Arrays.sort(airports, AirportDef.CodeComparator);
    }

    @Override
    public void actionPerformed(ActionEvent ae
    ) {
        distance(input1.getText(), input2.getText());
        BACKGROUND_PANEL.repaint();
    }

    private class BackGroundPanel extends Panel {

        private final int pointRad = 2;

        /**
         * Constructor for background object
         */
        private BackGroundPanel() {
            super();
        }

        /**
         * Draws components onto frame
         *
         * @param g
         */
        @Override
        public void paint(Graphics g) {
            g.drawImage(BACKGROUND, 0, 0, null);
            g.setColor(Color.red);
            g.fillOval(x1, y1, pointRad, pointRad);
            g.fillOval(x2, y2, pointRad, pointRad);
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(x1, y1, x2, y2);
        }

    }

    /**
     * Checks for focus on entry field
     */
    private class FocusListenerImpl implements FocusListener {

        private final boolean FIELD; //true is field 1, false is field 2

        public FocusListenerImpl(boolean f) {
            this.FIELD = f;
        }

        /**
         * On click, clears entry box
         *
         * @param e
         */
        @Override
        public void focusGained(FocusEvent e) {
            if (FIELD) {
                input1.setText("");
            } else {
                input2.setText("");
            }
        }

        /**
         * Does nothing, necessary override
         *
         * @param e
         */
        @Override
        public void focusLost(FocusEvent e) {
            if (FIELD) {
                if (input1.getText().isEmpty()) {
                    input1.setText("Airport 1");
                }
            } else {
                if (input2.getText().isEmpty()) {
                    input2.setText("Airport 2");
                }
            }
        }
    }
}


/*
    Pull up data
    Map
    Sort Strings
    Sort Ints
    Search Strings
    Search Ints
 */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

