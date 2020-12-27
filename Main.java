import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONException;
import org.json.JSONObject;

//Main Class
public class Main{
    private File dir;
    Scanner scan;
    public Main(String path){
        dir = new File(path);
        scan = new Scanner(System.in);
        if(!dir.mkdir()){                      
            System.out.println("cannot create datastore");
        }
        else System.out.println("DataStore created at "+path);
    }
// Create Function
    public void create(String path){
        FileWriter file;
        String key,nv="";
        String[] name_value;
        JSONObject obj = new JSONObject();
        System.out.print("Enter the File name : ");key = scan.next();
        System.out.println("Enter the key and value in format key,value. Press e to end : ");
        while(true){
            nv = scan.next();
            if(nv.equals('e')) break;
            else {
                name_value = nv.split(",", 2);
                try {
                    obj.put(name_value[0], name_value[1]);
                    System.out.println(name_value[0] + "-" + name_value[1]);
                } catch(ArrayIndexOutOfBoundsException e){
                  break;
                } catch (JSONException e) {
                    System.out.print("Unable to load json");
                    e.printStackTrace();
                }
            }
        }
        try{
            file = new FileWriter(path+"/"+key+".txt");
            file.write(obj.toString());
            System.out.println("Json object : \n"+ obj);
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
//Read Function
    public void read(String dir_path, String key){
        String path = dir_path+"/"+key+".txt";
        String line = null;
        FileReader file;
        try{
            file = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(file);
            while((line = bufferedReader.readLine())!=null)
                System.out.println(line);
            bufferedReader.close();
            file.close();
        }catch(FileNotFoundException fe){
            System.out.print("File with key" +key+" doesn't exists");
            fe.printStackTrace();
        }catch(IOException e){
            System.out.print("Error reading file"+path);
            e.printStackTrace();
        }

    }
//Delete Function
    public void delete(String dir_path, String key){
        String path = dir_path + "/" + key + ".txt";

        try {
            Files.deleteIfExists(Paths.get(path));
            System.out.println("File with key " +key+" deleted successfully");
        } catch(NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch(DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch(IOException e) {
            System.out.println("Invalid permissions.");
        }
    }
// Main Function
    public static void main(String[] args) throws IOException{
        String def_path = "C:/Data Store";       
        String path = "";
        int opt = 0;                           
        String key;
        JSONObject obj = new JSONObject();
        Scanner scan = new Scanner(System.in);

        System.out.println("Specify the path of data store. For default path (C:/Data Store) give d as input :");
        path = scan.next();
        if(path.equals("d")) path = def_path;

        Main ds = new Main(path);

        while(opt!=4){
            System.out.println("Select the option for \n1.Create \n2.Read \n3.Delete \n4.Quit");
            opt = scan.nextInt();
            switch (opt) {
                case 1:
                    ds.create(path);
                    break;
                case 2:
                    System.out.print("Enter the key to read : ");
                    key = scan.next();
                    ds.read(path, key);
                    break;
                case 3:
                    System.out.print("Enter the key to delete file: ");
                    key = scan.next();
                    ds.delete(path, key);
                    break;
                default:
                    System.out.print("Incorrect Option");
                    break;
            }
        }

    }
}