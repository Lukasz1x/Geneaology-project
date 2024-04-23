import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlantUMLRunner
{
    private static String plantUmlPath;

    public static void setPlantUmlPath(String path)
    {
        plantUmlPath=path;
    }

    public static void generateDiagram(String umlData, String filePath, String outputFileName)
    {
        File outputFile =new File(filePath + outputFileName+".txt");
        try
        {
            FileWriter fw = new FileWriter(outputFile);
            fw.write(umlData);
            fw.close();
            String command= "java -jar "+plantUmlPath +" -charset UTF-8 " +outputFile.getPath()
                    + " -o "+ filePath + " " + outputFileName;
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
