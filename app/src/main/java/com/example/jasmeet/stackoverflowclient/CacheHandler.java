package com.example.jasmeet.stackoverflowclient;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Jasmeet on 4/9/2017.
 * <p>
 * Format for Cache files storing JSONStrings - CX<FileNumber><Query><PageNumber></></></>
 * <p>
 * Where File Number is an integer value between 0-9, where lower number indicates that the file was accessed recently
 * PageNumber is the last character of the name and can also store 0-9
 * <p>
 * e.g. For three cache files storing json strings that are responses to query 'Android', the file names would be
 * CX0android3
 * CX1android2
 * CX2android1
 * <p>
 * The ArrayList cacheFiles, stores all the files in the Cache directory, including files not implicitly created by the
 * application that do not store json Strings.
 * <p>
 * The matrix fileNumbers[][] is used to store information about the cache files in the directory that store json strings.
 * Each row of the matrix stores information about one file.
 * The first column of each row stores the index in the cacheFiles ArrayList that corresponds to the file, and the
 * second column stores the priority or recent-use number of the file (The '0' in CX0android3).
 */

public class CacheHandler {

    private static final String TAG = "CacheHandler";
    private static final int MAXFILES = 10;

    private Context context;

    private File cacheDirectory;
    private ArrayList<File> cacheFiles;
    private int filesCount;
    private int[][] fileNumbers;

    public CacheHandler(Context ctx) {

        this.context = ctx;
        this.cacheDirectory = context.getCacheDir();
        this.fileNumbers = new int[MAXFILES][2];
        this.getCacheFiles();
    }

    public void writeJsonStringToCache(String query, String jsonStr, int pageNumber) {

        String fileName = "CX0" + query.toLowerCase() + Integer.toString(pageNumber);

        try {

            if (filesCount > 0)
                updateFileNumbers(fileName);

            File file = new File(cacheDirectory, fileName);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(jsonStr.getBytes());
            fos.close();
            this.getCacheFiles();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "FNFE: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOE: " + e.getMessage());
        }
    }

    public String readJsonStringFromCache(String query, int pageNumber) {
        String fileName = "CX0" + query.toLowerCase() + Integer.toString(pageNumber);
        int location;

        try {
            if (filesCount > 0)
                location = doesFileExist(fileName);
            else
                return null;

            if (location == -1)
                return null;
            else
                fileName = "CX" + Integer.toString(location) + query.toLowerCase() + Integer.toString(pageNumber);

            final FileInputStream inputStream = new FileInputStream(new File(cacheDirectory, fileName));
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            final StringBuilder stringBuilder = new StringBuilder();

            boolean done = false;

            while (!done) {
                final String line = reader.readLine();
                done = (line == null);

                if (line != null) {
                    stringBuilder.append(line);
                }
            }

            reader.close();
            inputStream.close();

            return stringBuilder.toString();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "FNFE: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOE: " + e.getMessage());
        }
        return null;
    }

    private void getCacheFiles() {

        filesCount = 0;

        if (cacheFiles != null) {
            cacheFiles.clear();
        }

        cacheFiles = new ArrayList<>(Arrays.asList(cacheDirectory.listFiles()));

        for (int i = 0, j = 0; i < cacheFiles.size(); i++) {

            String currentFilePath = cacheFiles.get(i).toString();
            String currentFileName = currentFilePath.substring(currentFilePath.lastIndexOf("/") + 1);

            if (currentFileName.substring(0, 2).compareTo("CX") == 0) {
                fileNumbers[j][0] = i;                                                                      //index in cacheFiles
                fileNumbers[j][1] = Character.getNumericValue(currentFileName.charAt(2));      //FileNumber or priority number in JSONCacheFiles
                filesCount++;

                j++;
            }
        }

        sortFileNumbers();
    }

    private void sortFileNumbers() {

        int temp[][] = new int[1][2];

        for (int i = 0; i < filesCount - 1; i++) {
            for (int j = i; j < filesCount; j++) {
                if (fileNumbers[i][1] > fileNumbers[j][1]) {
                    temp[0][0] = fileNumbers[i][0];
                    temp[0][1] = fileNumbers[i][1];
                    fileNumbers[i][0] = fileNumbers[j][0];
                    fileNumbers[i][1] = fileNumbers[j][1];
                    fileNumbers[j][0] = temp[0][0];
                    fileNumbers[j][1] = temp[0][1];
                }
            }
        }
    }

    private void updateFileNumbers(String fileName) {

        int limit = doesFileExist(fileName);

        if (limit == -1) {
            limit = filesCount;
        } else {
            cacheFiles.get(fileNumbers[limit][0]).delete();
        }

        for (int i = limit - 1; i >= 0; i--) {

            File currentFile = cacheFiles.get(fileNumbers[i][0]);
            String currentFileName = currentFile.toString().substring(currentFile.toString().lastIndexOf("/") + 1);

            if (i == MAXFILES - 1) {
                currentFile.delete();
                continue;
            }

            fileNumbers[i + 1][0] = fileNumbers[i][0];
            fileNumbers[i + 1][1] = fileNumbers[i][1];

            File newFile = new File(cacheDirectory, "CX" + Integer.toString(fileNumbers[i + 1][1] + 1) + currentFileName.substring(3));

            currentFile.renameTo(newFile);
            fileNumbers[i + 1][1] += 1;
        }
    }

    private int doesFileExist(String fileName) {

        for (int i = 0; i < filesCount; i++) {

            String currentFilePath = cacheFiles.get(fileNumbers[i][0]).toString();
            String currentFileName = currentFilePath.substring(currentFilePath.lastIndexOf("/") + 1);

            if (currentFileName.substring(3).compareTo(fileName.substring(3)) == 0) {
                return fileNumbers[i][1];
            }
        }
        return -1;
    }


}

