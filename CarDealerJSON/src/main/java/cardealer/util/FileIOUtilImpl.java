package cardealer.util;

import java.io.*;

public class FileIOUtilImpl implements FileIOUtil {

    @Override
    public String readFile(String filepath) throws IOException {
        File file = new File(filepath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

        StringBuilder sb = new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
                sb.append(line);
        }
        return sb.toString();
    }
}
