import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class logFile {
    public static void main(String[] args) {
        try {
            BufferedReader br =new BufferedReader(new FileReader("src/main/resources/aem.log"));
            PrintWriter writer = new PrintWriter("output.txt");
            String line;
            int errorcount=0;
            Set<String> identifierIds=new HashSet<>();
            String details="at com.somebank.aem.platform.models.common.impl.";
            boolean flag=false;

            Map<String,Integer> stackTrace=new HashMap<>();
            Map<String,String> identifierToError=new HashMap<>();
            String currentIdentifier=null;


            while((line= br.readLine())!=null){
                if(line.contains("ERROR")){
                    errorcount++;
                    flag=true;

                    int firstBracket=line.indexOf('[');
                    int secondBracket=line.indexOf('[',firstBracket+1);
                    int endBracket=line.indexOf(']',secondBracket);

                    if(secondBracket!=-1 && endBracket!=-1){
                        currentIdentifier=line.substring(secondBracket+1,endBracket);
                        identifierIds.add(currentIdentifier);
                    }
                }
                if(flag && line.trim().startsWith(details)){
                    String traceLine=line.trim();
                    int bracket=traceLine.indexOf('[');
                    if(bracket != -1){
                        traceLine = traceLine.substring(0, bracket);
                    }
                    stackTrace.put(traceLine,stackTrace.getOrDefault(traceLine,0)+1);

                    if(currentIdentifier!=null){
                        identifierToError.put(currentIdentifier,traceLine);
                    }
                    flag=false;
                    currentIdentifier=null;
                }
            }
            br.close();
            writer.println(("Total Errors: "+errorcount));
            writer.println("Identifier: "+identifierIds+"\n");

            for(Map.Entry<String,Integer> entry: stackTrace.entrySet()){
                writer.println(entry.getKey()+" Frequency: "+entry.getValue());
            }
            writer.println("\n");

            List<Map.Entry<String,Integer>> list=new ArrayList<>(stackTrace.entrySet());

            Collections.sort(list,new LogfileComparator());

            if(!list.isEmpty()) {
                Map.Entry<String, Integer> top = list.get(0);
                writer.println("Most Frequent Error:");
                writer.println(top);
            }
            writer.println("\n");
            writer.println("Identifier Error Mapping:");
            for (Map.Entry<String, String> entry : identifierToError.entrySet()) {
                writer.println(entry.getKey() + "  →  " + entry.getValue());
            }
            writer.close();

        }catch (Exception e){
            System.out.println("Error: "+ e.getMessage());
        }
    }

}
