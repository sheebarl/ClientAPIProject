package se.jensen.caw21;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.JsonKey;

import java.io.StringWriter;
import java.io.Writer;

public class Blog implements Jsonable {

    public int id;
    public String title;
    public String content;
    public String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private Blog(){

    }

    public Blog(String title,String content){
        this.setTitle(title);
        this.setContent(content);

    }

    public Blog(int id,String title,String content){
        this.setId(id);
        this.setTitle(title);
        this.setContent(content);

    }

    public Blog(String title,String content,String date){
        this.setTitle(title);
        this.setContent(content);
        this.setDate(date);

    }
    public Blog(int id,String title,String content,String date){
        this.setId(id);
        this.setTitle(title);
        this.setContent(content);
        this.setDate(date);

    }

    enum keys implements JsonKey{
        ID("id"),
        TITLE("title"),
        CONTENT("content"),
        DATE("date");

        private final Object value;

        keys(final Object value)
        {
            this.value = value;

        }

        @Override
        public String getKey()
        {

            return this.name().toLowerCase();
        }

        @Override
        public Object getValue()
        {
            return this.value;
        }
    }
    public String toJson(){
        final StringWriter writable = new StringWriter();
        try{
            this.toJson(writable);
        }catch(final Exception e){
            /* See java.io.StringWriter. */
        }
        return writable.toString();
    }

    @Override
    public void toJson(final Writer writable) {
        try {
            final JsonObject json = new JsonObject();
            json.put(keys.ID.getKey(),this.getId());
            json.put(keys.TITLE.getKey(),this.getTitle());
            json.put(keys.CONTENT.getKey(),this.getContent());
            json.put(keys.DATE.getKey(),this.getDate());
            json.toJson(writable);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @Override
    public String toString(){
        return "JsonBlog [id=\"+ this.id + \",title=" + this.title + ", content=" + this.content + ",date=" + this.date + "]";
    }


}
