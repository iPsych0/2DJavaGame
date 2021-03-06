package dev.ipsych0.maprefactoring;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {

    private File file;
    private String correctMapValues;

    public FileParser(File file) {
        this.file = file;
        this.correctMapValues = loadCorrectMapValues();
        parse();
    }

    /**
     * Loads in the correct tileset values from an input file
     * @return A full body containing all the right tileset xml tags to use per map
     */
    private String loadCorrectMapValues() {
        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            // Open stream to the correct value file
            is = new FileInputStream("../correctTilesetElements.txt");
            reader = new BufferedReader(new InputStreamReader(is));

            // Read the file by line
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            if(sb.toString().isEmpty()){
                throw new Exception();
            }

            // Return the whole contents
            return sb.toString();

        } catch (Exception e) {
            System.exit(1);
        }
        return null;
    }

    private void parse() {
        InputStream is = null;
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            // Open stream to the world tmx file
            is = new FileInputStream(file);
            reader = new BufferedReader(new InputStreamReader(is));

            // Read all lines
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String inputString = sb.toString();

            // Close the input streams
            is.close();
            reader.close();

            // Regex matching for any tileset tag in the world tmx file
            Pattern p = Pattern.compile("(\\s<tileset firstgid=\"\\d+\"\\ssource=\"[a-zA-Z_0-9\\-]+.tsx\"/>)\n");
            Matcher m = p.matcher(inputString);

            // For every match found by the regex, append the tag to create a body of incorrect values
            String incorrectValues = "";
            while (m.find()) {
                incorrectValues += m.group();
            }

            // Replace all incorrect values with the map values from the file containing the right input
            inputString = inputString.replaceFirst(incorrectValues, correctMapValues);

            // Override the world file with the correct values
            FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(inputString.getBytes());
            fileOut.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
