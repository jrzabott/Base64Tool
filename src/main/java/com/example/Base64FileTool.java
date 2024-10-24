package com.example;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Enum representing the mode of operation for the tool: ENCODE or DECODE.
 */
enum Mode {
    ENCODE,
    DECODE;

    /**
     * Parses the mode from a string input.
     *
     * @param modeStr The input string for the mode.
     * @return The corresponding Mode enum value.
     */
    public static Mode fromString(String modeStr) {
        return switch (modeStr.toLowerCase()) {
            case "encode" -> ENCODE;
            case "decode" -> DECODE;
            default -> throw new IllegalArgumentException("Invalid mode: " + modeStr);
        };
    }
}

/**
 * A utility class for Base64 encoding and decoding files with a progress display.
 */
public class Base64FileTool {

    /**
     * Encodes the content of a file to a Base64 string.
     *
     * @param filePath Path to the file to be encoded.
     * @return Base64 encoded string.
     * @throws IOException If there is an error reading the file.
     */
    public static String encodeFileToBase64(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    /**
     * Decodes a Base64 string and writes the result to a file.
     *
     * @param base64String   Base64 string to be decoded.
     * @param outputFilePath Path to write the decoded file.
     * @throws IOException If there is an error writing the file.
     */
    public static void decodeBase64ToFile(String base64String, String outputFilePath) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
            fos.write(decodedBytes);
        }
    }

    /**
     * Runs the encoding or decoding in a separate thread and displays progress.
     *
     * @param mode            The mode of operation (encode or decode).
     * @param inputFilePath   Path to the input file.
     * @param outputFilePath  Path to the output file.
     */
    private static void runWithProgress(Mode mode, String inputFilePath, String outputFilePath) {
        new Thread(() -> {
            try {
                long startTime = System.currentTimeMillis();
                if (mode == Mode.ENCODE) {
                    System.out.println("Encoding file: " + inputFilePath);
                    String encodedString = encodeFileToBase64(inputFilePath);
                    writeFile(outputFilePath, encodedString);
                } else if (mode == Mode.DECODE) {
                    System.out.println("Decoding Base64 file: " + inputFilePath);
                    String base64String = readFile(inputFilePath);
                    decodeBase64ToFile(base64String, outputFilePath);
                }
                long endTime = System.currentTimeMillis();
                System.out.println("Process completed in " + (endTime - startTime) / 1000 + " seconds.");
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace(); // TODO - enhance log for the future
            }
        }).start();
    }

    /**
     * Displays the usage instructions.
     */
    private static void displayUsage() {
        System.out.println("Usage:");
        System.out.println("For encoding: java -jar Base64FileTool.jar encode <input_file_path> <output_base64_file>");
        System.out.println("For decoding: java -jar Base64FileTool.jar decode <input_base64_file> <output_file_path>");
    }

    /**
     * Writes the content to a file.
     *
     * @param filePath Path to the output file.
     * @param content  Content to be written.
     * @throws IOException If there is an error writing the file.
     */
    private static void writeFile(String filePath, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
            System.out.println("File written successfully to: " + filePath);
        }
    }

    /**
     * Reads the content from a file.
     *
     * @param filePath Path to the input file.
     * @return Content read from the file.
     * @throws IOException If there is an error reading the file.
     */
    private static String readFile(String filePath) throws IOException {
        StringBuilder base64Content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                base64Content.append(line);
            }
        }
        return base64Content.toString();
    }

    /**
     * Main method to run the tool based on command line arguments.
     *
     * @param args Command line arguments (mode, input file, output file).
     */
    public static void main(String[] args) {
        if (args.length < 3) {
            displayUsage();
            return;
        }

        try {
            Mode mode = Mode.fromString(args[0]);
            String inputFilePath = args[1];
            String outputFilePath = args[2];


            runWithProgress(mode, inputFilePath, outputFilePath);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            displayUsage();
        }
    }
}
