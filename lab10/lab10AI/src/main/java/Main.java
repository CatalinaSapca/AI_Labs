import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.StandardScaler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    int k;
    BufferedImage[] inputPhotos = new BufferedImage[250];
    BufferedImage[] outputPhotos = new BufferedImage[250];

    BufferedImage[] trainingInput = new BufferedImage[250];
    BufferedImage[] trainingOutput = new BufferedImage[250];
    BufferedImage[] testingInput = new BufferedImage[250];
    BufferedImage[] testingOutput = new BufferedImage[250];

    ArrayList<ArrayList<Long>> confusionMatrix = new ArrayList<ArrayList<Long>>();

    private void splitData(double procTest) {
        Random rand = new Random();

        int a = -1, b = -1;
        for (int i = 0; i < k + 1; i++) {
            if (rand.nextDouble() < procTest) {
                a++;
                testingInput[a] = inputPhotos[i];
                testingOutput[a] = outputPhotos[i];
            } else {
                b++;
                trainingInput[b] = inputPhotos[i];
                trainingOutput[b] = outputPhotos[i];
            }
        }
    }

    public void readData() throws IOException {
        k = -1;

        File dir = new File("nnn");
        for (final File imgFile : Objects.requireNonNull(dir.listFiles())) {
            // assuming the directory contains only images
            BufferedImage image = ImageIO.read(imgFile);
            BufferedImage outputImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(image, 0, 0, null);
            k++;
            inputPhotos[k] = outputImage;
        }

        k = -1;
        dir = new File("sss");
        for (final File imgFile : Objects.requireNonNull(dir.listFiles())) {
            BufferedImage image = ImageIO.read(imgFile);
            BufferedImage outputImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            outputImage.getGraphics().drawImage(image, 0, 0, null);
            k++;
            outputPhotos[k] = outputImage;
        }
    }

    void evalFunction(ArrayList<String> realData, ArrayList<String> computedData, ArrayList<String> labels) {
        ArrayList<Long> precision = new ArrayList<>();
        ArrayList<Long> recall = new ArrayList<>();

        for (int i=0;i< labels.size();i++){
            precision.add(0L);
            for (int j=0;j< labels.size();j++){
                recall.add(0L);
                detConfusionMatrix(realData, computedData, labels);
            }
        }

        long predictedPossibility,actualPossibility;
        for (int i=0;i< labels.size();i++){
            predictedPossibility=0;
            for (int j=0;j< labels.size();j++){
               predictedPossibility = predictedPossibility + confusionMatrix.get(j).get(i);
               precision.add(i,confusionMatrix.get(i).get(i)/predictedPossibility);
            }
        }
        for (int i=0;i< labels.size();i++){
            actualPossibility=0;
            for (int j=0;j< labels.size();j++){
                actualPossibility = actualPossibility + confusionMatrix.get(j).get(i);
                recall.add(i,confusionMatrix.get(i).get(i)/actualPossibility);
            }
        }
    }

    void detConfusionMatrix(ArrayList<String> realLabels, ArrayList<String> computedLabels, ArrayList<String> labels){
        for(int i = 0;i<labels.size();i++); {
            ArrayList<Long> a = new ArrayList<>();
            for(int j = 0;j<labels.size();j++)
            {
                a.add(0L);
                confusionMatrix.add(a);
            }
        }
        for(int i = 0;i<computedLabels.size();i++) {
            long actual = 0;
            long predicted = 0;
            for(int j = 0;j<labels.size();j++)
            {
                if(labels.get(j).equals(realLabels.get(i)))
                    actual = j;
                if(labels.get(j).equals(computedLabels.get(i)))
                    predicted = j;
                confusionMatrix.get((int) actual).add((int) predicted,confusionMatrix.get((int) actual).get((int) predicted)+1);
            }
        }
    }

    public void main(String[] args) throws IOException {
        readData();
        splitData(0.2);
    }
}
