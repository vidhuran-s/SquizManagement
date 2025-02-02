package userdetails;

import java.io.File;
import java.io.IOException;

public class  UserDetails
{
    public static final String FILEPATH = "/home/vidhu-zstk391/Desktop/Squiz-Management/Userdetails/user_information.txt";

    public void createFile()
    {

        File file = new File(FILEPATH);
        try {
            if (file.createNewFile()) {
                System.out.println("New file Successfully Created");
            }
            else {
                System.out.println("The file is already existed");
            }
        } catch (IOException e) {
            System.out.println("Some error occurred while creating the file");
            e.printStackTrace();
        }
    }
}