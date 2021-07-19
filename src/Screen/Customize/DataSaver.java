package Screen.Customize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataSaver {
    public static Data readData(String pathName) {
        Data data = new Data();
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(pathName));
            data = (Data) input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void saveData(Data data, String pathName) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(pathName));
            output.writeObject(data);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
