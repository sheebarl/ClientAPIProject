package se.jensen.caw21;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;

public class ApiBlogClient {

    private String apiAddress;
    HttpURLConnection connection;

    public  ApiBlogClient(String apiAddress){
        this.apiAddress=apiAddress;
    }

    public ArrayList<String> getStringArray(String target){
        JsonObject jObject=new JsonObject();
        ArrayList<String> myArray=new ArrayList<>();
        return myArray;
    }

    public Blog[] getListOfBlog() {

        Blog[] blogs = {};
        String target = "/list";
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            int status = connection.getResponseCode();
            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            String jsonStr = responseContent.toString();
            ObjectMapper mapper = new ObjectMapper();
            blogs = mapper.readValue(jsonStr, Blog[].class);
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        finally {
            connection.disconnect();
        }
        return blogs;
    }


    public Blog getListOfBlogById(int id) {

        Blog blogs=null ;
        System.out.println(id);
        String target = "/view/" + id;

        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();


        try {
            URL url = new URL(apiAddress + target);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("accept", "application/json");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                //connection.setDoOutput(true);
                    int status = connection.getResponseCode();
            //System.out.println(status);

            if (status >= 300 ) {
                if(connection.getErrorStream()!=null) {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();
                }
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);


                }
                reader.close();
                String jsonStr = responseContent.toString();
                ObjectMapper mapper = new ObjectMapper();
                blogs = mapper.readValue(jsonStr, Blog.class);
            }

        } catch (Exception e) {
            //System.out.println("Id does not exist");
            e.printStackTrace();
            System.out.println("Exception :" + e);
        }
        finally {
            connection.disconnect();
        }

        return blogs;
    }



    public boolean clearBlog() {
        String target = "/clearBlog";
        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();
            if (status < 300) {
                success = true;
            }
            else{
                System.out.println("Status code : " + status);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }


    public boolean clearBlogById(int id) {
        String target = "/delete/" + id;
        boolean success = false;

        try {
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            int status = connection.getResponseCode();
            if (status < 300) {
                success = true;
            }
            else{
                System.out.println("Status code : " + status);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }

        return success;
    }

    public boolean addNewBlog(Blog newBlog){
        String target="/create";
        boolean success=false;

        try{
            URL url = new URL(apiAddress + target);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            try(OutputStream os=connection.getOutputStream()) {
                byte[] input = newBlog.toJson().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
                //@Override
                //public void write(int b) throws IOException {

                //}

            int status = connection.getResponseCode();
            if (status < 300) {
                success = true;
            }
            else{
                System.out.println("Status code:" + status);
            }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }
        return success;
          }

   // public boolean changeBlogById(Blog updateBlog,int id){
   public Blog changeBlogById(Blog updateBlog,int id){
        String target="/update/" + id;
        boolean success=false;
        //BufferedReader reader;
        //String line;
        //StringBuilder responseContent=new StringBuilder();

        try {
            URL url = new URL(apiAddress + target);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            //connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

                try (OutputStream os = connection.getOutputStream()) {
                    if(connection.getErrorStream()!=null) {
                        byte[] input = updateBlog.toJson().getBytes(StandardCharsets.UTF_8);
                        os.write(input, 0, input.length);
                    }

            }

                    int status = connection.getResponseCode();

           /* if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);

                }
                reader.close();
            }
            String jsonStr = responseContent.toString();
            ObjectMapper mapper = new ObjectMapper();
            updateBlog = mapper.readValue(jsonStr, Blog.class);
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        finally {
            connection.disconnect();
        }*/
                    if (status < 300) {
                        success = true;
                    }

            //System.out.println(responseContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception: " + e);
        } finally {
            connection.disconnect();
        }
        return updateBlog;

    }
}


