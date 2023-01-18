import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> database = new ArrayList<String>(8);

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Available commands as paths: /add?s=(your string) and /search?s=(your string)");
        } 
        else{
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    if(database.indexOf(parameters[1]) == -1){
                        database.add(parameters[1]);
                        System.out.println(parameters[1]);
                        return String.format("%s Added to the database", parameters[1]);
                    }
                    return String.format("%s Already in database", parameters[1]);
                }  
            }
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                int num = 0;
                String terms = "";
                for (int i = 0; i < database.size(); ++i){
                    if (database.get(i).contains(parameters[1])){
                        if(num == 0){
                            terms += database.get(i);
                            ++num;
                        }else{
                            terms = terms + ", " + database.get(i);
                            ++num;
                        }
                        
                    }
                }
                if(num == 0){
                    return String.format("No strings found");
                }
                return terms;
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
