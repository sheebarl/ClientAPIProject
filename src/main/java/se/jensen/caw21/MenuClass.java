package se.jensen.caw21;

import java.util.Scanner;

public class MenuClass {
        ApiBlogClient myApiBlogClient;

        public MenuClass(){

            myApiBlogClient=new ApiBlogClient("http://127.0.0.1:8080/api/v1/blog");

        }
        public void start(){
            boolean option=true;
            while(option){
                System.out.println();
                System.out.println("________________________________");
                System.out.println("********* Select the Menu *******");
                System.out.println("_________________________________");
                System.out.println("1.Get the list of blog");
                System.out.println("2.Get the specific blog");
                System.out.println("3.Edit the blog");
                System.out.println("4.Clear the blog list");
                System.out.println("5.Clear the blog by Id");
                System.out.println("6.Add a new blog");
                System.out.println("7.Quit the program");
                System.out.println("__________________________________");
                System.out.println();

                int choice=getUserInt();

                switch(choice){
                    case 1:
                        viewListOfBlog();
                        break;
                    case 2:
                        viewBlogById();
                        break;
                    case 3:
                        changeBlogById();
                        break;
                    case 4:
                        clearListOfBlog();
                        break;
                    case 5:
                        clearListOfBlogById();
                        break;
                    case 6:
                        addNewBlog();
                        break;
                    case 7:
                        System.out.println("Thanks for Visiting.Welcome Again");
                        option=false;
                }
            }

        }

        public void viewListOfBlog(){
            Blog[] blogs=myApiBlogClient.getListOfBlog();

            System.out.println("***List of Blog***");
            System.out.println("________________________");

            if(blogs. length > 0){
                for (int i=0; i < blogs.length; i++){

                    int id= blogs[i].id;
                    String title=blogs[i].title;
                    String content=blogs[i].content;
                    String date=blogs[i].date;

                    System.out.println("Id :" + id);
                    System.out.println("Title : " + title);
                    System.out.println("Content:" + content);
                    System.out.println("Date:" + date);

                }
            }
            else{
                System.out.println("No blogs in the list");
            }
        }

        public void viewBlogById(){
            System.out.print("Enter the id : ");
            int blogId=getUserInt();
            Blog blogs=myApiBlogClient.getListOfBlogById(blogId);
            if(blogs!=null) {
                int id = blogs.getId();
                //System.out.println(id);
                if (blogId == id) {
                    String title = blogs.title;
                    String content = blogs.content;
                    //System.out.printf("-> %s %s \n","Title : " , title + " " + "Content : " + content);
                    System.out.println("Title : " + title);
                    System.out.println("Content : " + content);
                }
            }
                else{
                    System.out.println("Id " + blogId + " does not found");
                }
        }

        public void changeBlogById(){
            System.out.print("Enter the id : ");
            int blogId=getUserInt();
            Blog updateBlog=myApiBlogClient.getListOfBlogById(blogId);
            Blog blogs=myApiBlogClient.changeBlogById(updateBlog,blogId);
            if(updateBlog!=null){
            //for(int i=0;i<blogs.length;i++){
                int id= updateBlog.getId();
                if(blogId==id) {
                    blogs = myApiBlogClient.changeBlogById(updateBlog, blogId);
                    System.out.println("What  you want to update?");
                    System.out.println("1.Title of the Post");
                    System.out.println("2.Content of the Post ");
                    System.out.println("3.Date");
                    int choice = getUserInt();
                    if (choice == 1) {
                        System.out.println("Enter the title to be updated");
                        String title = getUserString();
                        updateBlog.setTitle(title);
                        blogs = myApiBlogClient.changeBlogById(updateBlog, blogId);
                        //updateBlog.toJson();
                        //blogs.setTitle(title);
                        //System.out.printf("-> %s  \n",title);
                        System.out.println("Title : " + title);
                    } else if (choice == 2) {
                        System.out.println("Enter the content to be updated");
                        String content = getUserString();
                        updateBlog.setContent(content);
                        blogs = myApiBlogClient.changeBlogById(updateBlog, blogId);
                        //blogs.setContent(content);
                        //System.out.printf("-> %s  \n",content);
                        System.out.println("Content : " + content);
                    } else if (choice == 3) {
                        System.out.println("Enter the date to be updated");
                        String date = getUserString();
                        updateBlog.setDate(date);
                        blogs = myApiBlogClient.changeBlogById(updateBlog, blogId);
                        //System.out.printf("-> %s  \n",date);
                        System.out.println("Date : " + date);
                    }
                }
                        //String title=blogs.setTitle();

                }
                else{
                    System.out.println("Id "+ blogId + " does not found");
                }

        }

        public void clearListOfBlog(){
            if(myApiBlogClient.clearBlog()){
                System.out.println("List of blogs are cleared");
            }
            else{
                System.out.println("Issue in clearing list og blogs");

            }
        }

    public void clearListOfBlogById() {
        System.out.print("Enter the id");
        int blogId = getUserInt();
        Blog myBlog = myApiBlogClient.getListOfBlogById(blogId);
        //Blog blogs = myApiBlogClient.clearBlogById(blogId);
        if(myBlog!=null) {
            int id = myBlog.getId();

            if (blogId == id) {
                myApiBlogClient.clearBlogById(blogId);
                System.out.println("Successfully deleted");
            }
        }
        else{
            System.out.println("Id "+ blogId + " does not found");
        }
    }

        public void addNewBlog(){
            System.out.println("Enter the title for the blog");
            String title=getUserString();
            System.out.println("Enter the content ");
            String content=getUserString();
            System.out.println("Enter the date");
            String date=getUserString();
            Blog newBlog=new Blog(title,content,date);
            //System.out.println("Title :" + newBlog.getTitle());
            boolean success=myApiBlogClient.addNewBlog(newBlog);

            if(success){
                System.out.println("New Blog added");
            }
            else{
                System.out.println("Issue adding new Blog");
            }
        }

        public String getUserString(){
            Scanner sc=new Scanner(System.in);
            String myString;
            while(true) {
                try {
                    System.out.print(">");
                    myString = sc.nextLine();
                    break;
                }catch (Exception e){
                    System.out.println("Incorrect data");
                }

            }
            return myString;
        }

        public int getUserInt(){
            Scanner sc=new Scanner(System.in);
            int myInt;
            while(true){
                try{
                    System.out.println(">");
                    myInt=sc.nextInt();
                    break;
                }catch (Exception e){
                    System.out.println("Incorrect data");
                }
            }
            return myInt;
        }
}


