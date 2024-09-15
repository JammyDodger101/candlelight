package net.jammydodger101.candlelight.util;

import net.jammydodger101.candlelight.Candlelight;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonCandlelightDataHandler {
    // store candlelight data in a json file (similar to how player data is stored idk maybe idk)
    // store candle locations (can use a workaround but this is more fun) and store trapped status
    // be able to store and retrieve data

    // an array of players to get index
    // array of locations
    // array of trappeds

    public static String LOCATION_FILE = "mods/locations.txt";
    public static String SEPERATOR = "&";
    public static List<String> readDataList = new ArrayList<>();
    public static List<String> writtenDataList = new ArrayList<>();
    public static List<CandlelightData> candlelightDataList = new ArrayList<>();
    public static List<String[]> dataArrayList = new ArrayList<>();

    public static List<Boolean> trappedBoolList = new ArrayList<>();
    public static List<BlockPos> candleLocationList = new ArrayList<>();

    public static Integer nameToIndex(String playerName) {
        return switch (playerName.toLowerCase()) {
            case "jammydodger101" -> 0;
            case "pompomdexter" -> 1;
            case "citramin" -> 2;
            case "crayzink" -> 3;
            case "longpotter" -> 4;
            case "crocksmarter" -> 5;
            case "a_random_cat" -> 6;
            case "leantheliquid" -> 7;
            case "not_jk" -> 8;
            case "callmemustard_" -> 9;
            case "ashpffwho" -> 10;
            case "georgehminer" -> 11;
            case "maytack" -> 12;
            default -> -1;
        };
    }

    public static void fillLists(int count) {
        for (int i = 0; i < count; i++) {
            readDataList.add(null);
            writtenDataList.add(null);
            candlelightDataList.add(null);
            dataArrayList.add(null);
            trappedBoolList.add(null);
            candleLocationList.add(null);
        }
    }

    public static void createDataAndWrite(String playerName, @Nullable Boolean trapped, @Nullable BlockPos location) {
        int index = nameToIndex(playerName);
        CandlelightData data = new CandlelightData(playerName, index, trapped, location);

        writtenDataList.set(index, dataToString(data));
        candlelightDataList.set(index, data);

        writeToFile(data);
    }

    public static String dataToString(CandlelightData data) {
        String returnString = "";

        String name = data.getName();
        String index = String.valueOf(data.getIndex());
        String trapped = String.valueOf(data.isTrapped());



        BlockPos locationPos = data.getLocation();
        String location;
        if (locationPos != null) {
            location = data.getLocation().toShortString();
        } else {
            location = "null";
        }

        //returnString = "\""+name+"\": {\"index\": "+index+", \"trapped\": "+trapped+", \"location\": \""+location+"\"}";
        returnString = name+SEPERATOR+index+SEPERATOR+trapped+SEPERATOR+location;

        Candlelight.LOGGER.info("return string equals " + returnString);
        return returnString;
    }

    public static String listToString() {
        StringBuilder finalString = new StringBuilder();
        for (String jsonElement : writtenDataList) {
            finalString.append(jsonElement);
            if (writtenDataList.indexOf(jsonElement) != writtenDataList.size()-1) {
                finalString.append("|");
            }
        }


        return finalString.toString();
    }

    public static void writeToFile(CandlelightData data) {
        try (Writer writer = new FileWriter(LOCATION_FILE)) {
            //gson.toJson(data, data.getClass(), writer);
            //readInFileContent();
            writer.write(listToString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void readInFileContent() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(LOCATION_FILE))) {
            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
            String everything = sb.toString();
            Candlelight.LOGGER.info("everything    " + everything);
            fillDataList(everything);
            updateHandler();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fillDataList(String fileString) {
        String[] tempList = fileString.split("\\|");
        readDataList = List.of(tempList);
        Candlelight.LOGGER.info(String.valueOf(readDataList) + "data list here");
        int count = 0;
        for (String data : tempList) {
            if (count >= Candlelight.MEMBER_NUMBER) {
                break;
            }
            String[] splitData = data.split("&");
            if (splitData.length > 1) {
                Candlelight.LOGGER.info(Arrays.toString(splitData) + "split data list");
                CandlelightData tempData = new CandlelightData(splitData[0], Integer.parseInt(splitData[1]), Boolean.valueOf(splitData[2]), CandleLocationConverter.StringToBlockPos(splitData[3]));
                candlelightDataList.set(count, tempData);
                //candlelightDataList.add(tempData);
                dataArrayList.set(count, splitData);
            } else {
                candlelightDataList.set(count, null);
                dataArrayList.set(count, null);
            }
            count++;
        }
    }

    private static void updateHandler() {
        for (CandlelightData data : candlelightDataList) {
            if (data != null) {
                trappedBoolList.set(data.getIndex(), data.isTrapped());
                candleLocationList.set(data.getIndex(), data.getLocation());
            }
        }
        PlayerCandleHandler.trappedPlayerBools = trappedBoolList;
        PlayerCandleHandler.candleCoordinates = candleLocationList;
    }
}
