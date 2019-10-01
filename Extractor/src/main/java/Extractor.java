import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.HashMap;

public class Extractor {

    private static void getAndInsert(HashMap<String,String> map, String key,Object r){
        JSONObject job = (JSONObject) r;
        map.put(job.get(key).toString(),"");
    }

    public static void main(String[] args) {
        if(args.length!=3){
            System.out.println("Use: java Extractor tag file destination_path");
            return;
        }
        HashMap<String,String> map = new HashMap<>();
        try(FileReader fileReader = new FileReader(args[1])){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ;
            JSONObject obj;
            while((line = bufferedReader.readLine()) != null) {
                obj = (JSONObject) new JSONParser().parse(line);
                getAndInsert(map,args[0],obj);
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found");
            return;
        }
        catch (IOException | ParseException e){
            e.printStackTrace();
        }
        FileOutputStream fos;
        try {
            File outfile = new File(args[2] + "keys_" + args[1]+"_"+(int)(Math.random()*10000) + ".txt");
            fos = new FileOutputStream(outfile);
            for(String k:map.keySet()){
                fos.write( (k+"\n").getBytes());
            }
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
    }
}
