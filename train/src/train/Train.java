
package train;
import java.io.FileNotFoundException;
import java.io.FileReader; 
import java.io.FileWriter;
import java.io.IOException;  
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.json.simple.JSONArray;  
import org.json.simple.JSONObject;  
import org.json.simple.parser.JSONParser;  
import org.json.simple.parser.ParseException;  
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONValue;
import java.util.Scanner;
//import static java.util.Date.parse;
/**
 *
 * @author Hichem
 */
public class Train {
    /**
     * @param args the command line arguments
     * @throws org.json.simple.parser.ParseException
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException  {
        System.out.println("Please type the correct file path of your input JSON File  example  c:/directory/namefilein.ext ");
        Scanner scanner = new Scanner(System. in);
        String input = scanner. nextLine();
        System.out.println("Please put the file path of your ouput JSON File example   c:/directory/namefileout.ext");
        String output = scanner. nextLine();
        pay2(input,output);
    }
    
    
    private static void pay2(String filepathin, String filepathout) throws FileNotFoundException, IOException, ParseException 
    {
      JSONArray a=readjson2(filepathin);
      //System.out.println(a.size());
      Map customer = new LinkedHashMap();
      JSONArray customerSummaries = new JSONArray();    
      int i=0; 

while(i<a.size()-1){
    
    Map customer2 = new LinkedHashMap();
    
    JSONObject cust = (JSONObject) a.get(i); 
    Long id1=(Long) cust.get("customerId");
    
    //System.out.println(id1);
    Long unixTimestamp ;//= (Long) cust.get("unixTimestamp");
    Long customerId = (Long) cust.get("customerId");
    String station = (String) cust.get("station");
    JSONObject cust2 = (JSONObject) a.get(i+1);
    Long id2=(Long) cust2.get("customerId");

   long totalCostInCents=240;
   //String stationStart=station;
   //String stationEnd=station;
   long zoneFrom=0;
   long zoneTo=0; 
   long costInCents=99999; /// big number to make test later
     
     customer2.put("customerId", customerId);
     //customer2.put("totalCostInCents", totalCostInCents);
     Map trip = new LinkedHashMap();
     JSONArray trips = new JSONArray();
   
//long s=0;
totalCostInCents=0;
    while(id1==id2 && i<a.size()-1){ 
        
      trip = new LinkedHashMap();
    // System.out.println(i);
     cust = (JSONObject) a.get(i); 
     id1=(Long) cust.get("customerId");
     cust2 = (JSONObject) a.get(i+1);
     id2=(Long) cust2.get("customerId");
 
    //customerId = (Long) cust.get("customerId"); 
    unixTimestamp = (Long) cust.get("unixTimestamp");
    String stationStart = (String) cust.get("station");
    String stationEnd = (String) cust2.get("station");
    
        if(i%2==0){  
         if (stationStart.contains("A")||stationStart.contains("B")){zoneFrom=1;}
         if (stationStart.contains("C")||stationStart.contains("D")||stationStart.contains("E")){zoneFrom=2;}
         if (stationStart.contains("C")||stationStart.contains("E")||stationStart.contains("F")){zoneFrom=3;}
         if (stationStart.contains("F")||stationStart.contains("G")||stationStart.contains("H")||stationStart.contains("I")){zoneFrom=4;}
         
         if (stationEnd.contains("A")||stationEnd.contains("B")){zoneTo=1;}
         if (stationEnd.contains("C")||stationEnd.contains("D")||stationEnd.contains("E")){zoneTo=2;}
         if (stationEnd.contains("C")||stationEnd.contains("E")||stationEnd.contains("F")){zoneTo=3;}
         if (stationEnd.contains("F")||stationEnd.contains("G")||stationEnd.contains("H")||stationEnd.contains("I")){zoneTo=4;}
         
         if (stationEnd.contains("C")&&zoneFrom==1){zoneTo=2; }
         if (stationEnd.contains("C")&&zoneFrom==4){zoneTo=3; }
         if (stationEnd.contains("E")&&zoneFrom==1){zoneTo=2; }
         if (stationEnd.contains("E")&&zoneFrom==4){zoneTo=3; }
         if ((stationEnd.contains("F"))&&(zoneFrom==1||zoneFrom==2)){zoneTo=3; }
         if (stationEnd.contains("F")&&zoneFrom==4){zoneTo=3; }
         if (stationEnd.contains("F")&&zoneFrom==3){zoneTo=4; }

         
        
         if (stationStart.contains("C")&&zoneTo==1){zoneFrom=2; }
         if (stationStart.contains("C")&&zoneTo==4){zoneFrom=3; }
         if (stationStart.contains("E")&&zoneTo==1){zoneFrom=2; }
         if (stationStart.contains("E")&&zoneTo==4){zoneFrom=3; }
         if ((stationStart.contains("F"))&&(zoneTo==1||zoneTo==2)){zoneFrom=3; }
         if (stationStart.contains("F")&&zoneTo==4){zoneFrom=3; }
         if (stationStart.contains("F")&&zoneTo==3){zoneFrom=3; }
         if (stationEnd.contains("F")&&(zoneTo==4)){zoneFrom=4; }

         
         
         //if (stationEnd.contains("F")&&zoneFrom==4||zoneFrom==3){zoneTo=3; }
         
         if((zoneFrom==1||zoneFrom==2)&&(zoneTo==1||zoneTo==2)){costInCents=240;}
         if((zoneFrom==3||zoneFrom==4)&&(zoneTo==3||zoneTo==4)){costInCents=200;}
         if(((zoneFrom==1||zoneFrom==2)&&zoneTo==3)||(zoneFrom==3)&&(zoneTo==1||zoneTo==2)){costInCents=280;}
         if(((zoneFrom==1||zoneFrom==2)&&zoneTo==4)||(zoneFrom==4)&&(zoneTo==1||zoneTo==2)){costInCents=300;}
         
         totalCostInCents+=costInCents;
            
            }
    customer2.put("totalCostInCents", totalCostInCents);
    //System.out.println(unixTimestamp);
	       trip.put("stationStart", stationStart);
               trip.put("stationEnd", stationEnd);
               trip.put("startedJourneyAt",unixTimestamp);
               trip.put("costInCents", costInCents);
               trip.put("zoneFrom", zoneFrom);
               trip.put("zoneTo", zoneTo); 
               
             if(i%2==0){ trips.add(trip);} 
       i++; 
    
    }
    //trips.add(trip);
    customer2.put("Trips",trips);
    customerSummaries.add(customer2);

    //System.out.println();
  }
	       customer.put("customerSummaries", customerSummaries);
		// Write to stdout
		System.out.println(JSONValue.toJSONString(customer));
        try ( // Write to file
            FileWriter f = new FileWriter(filepathout)) {
            JSONValue.writeJSONString(customer, f);
          //System.out.println(f.getEncoding());
            f.flush();
            f.close();
        }
    
    }

   
    
    @SuppressWarnings({"empty-statement", "empty-statement"})
    private static JSONArray readjson2(String filepath) throws FileNotFoundException, IOException, ParseException 
    {
        
          JSONParser parser = new JSONParser();
        JSONObject taps = (JSONObject) parser.parse(new FileReader(filepath));
 
        //try {  
             //System.out.println("taps");
          JSONArray a = (JSONArray) taps.get("taps");
          JSONArray sortedJsonArray = new JSONArray();
          List<JSONObject> list = new ArrayList<JSONObject>();
            for (Object o : a){
             JSONObject person = (JSONObject) o;
                    //Long unixTimestamp = (Long) person.get("unixTimestamp");
                    //Long customerId = (Long) person.get("customerId");
                    //String station = (String) person.get("station");
                    list.add(person);
            }
  
      Collections.sort( list, new Comparator<JSONObject>() {
        //You can change "Name" with "ID" if you want to sort by ID
        private static final String KEY_NAME = "customerId";

        @Override
        public int compare(JSONObject a, JSONObject b) {
            Long valA = null ;//= new Long();
            Long valB = null;// = new Long();

            try {
                valA = (Long) a.get(KEY_NAME);
                valB = (Long) b.get(KEY_NAME);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
          return valA.compareTo(valB);      //do something
        }
    });
      
 a.clear();
       
  for (Object o : list)
  {
    JSONObject person = (JSONObject) o;
    a.add(person);
  }

    
    return a;
    }
      

}
