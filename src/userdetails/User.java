package userdetails;

import squizbox.Quiz;
import squizbox.Squizbox;
import java.util.Base64;
import java.util.ArrayList;

public class User extends Person{
    private String userID;
    protected boolean canModify;
    protected boolean isBlocked;
    protected boolean isDeleted;
    public ArrayList<Squizbox> quizList = new ArrayList<>();

    public ArrayList<Squizbox> getQuizList() {
        return quizList;
    }

    public void setQuizList(ArrayList<Squizbox> quizList) {
        this.quizList = quizList;
    }

    public User(String name, String email, String password) {
        super(name, email, password);
        this.userID = "UserID-" + uniqueId;
        this.canModify = false;
        this.isBlocked = false;
        this.isDeleted = false;
    }

    public boolean isCanModify() {
        return canModify;
    }

    public void setCanModify(boolean canModify) {
        this.canModify = canModify;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void showUserID()
    {
        System.out.println(this.userID);
    }

    public void showUserMenu()
    {
        System.out.println("===================================");
        System.out.println("             Quizzes               ");
        System.out.println("===================================");
        System.out.println("1. Participate in Default Quiz");
        System.out.println("2. Participate in User-Created Quiz");
        System.out.println("3. Create a Quiz (if permission granted)");
        System.out.println("4. Show my Quizzes");
        System.out.println("5. Remove Created Quiz");
        System.out.println("0. Exit");
        System.out.println("===================================");
        System.out.println("Enter your choice");
    }

    public Squizbox makeQuiz()
    {
        return Quiz.createQuiz(this.getUserID());
    }

    public int showUsercreatedQuizMenu()
    {
        if (this.quizList.size() > 0) {
            for (int i = 0; i < this.quizList.size(); i++) {
                System.out.println((i + 1) + ")" + quizList.get(i).getBoxName());
            }
            System.out.println("0) Back to menu");
            System.out.println();
        }
        else {
            System.out.println("You have not created any quizzes");
        }
        int size = this.quizList.size();
        return size;
    }

    public static String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public static String decryptPassword(String encryptedPassword) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        return new String(decodedBytes);
    }

    public static void displayUserDetails(ArrayList<User> userList) {
        if (userList.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        System.out.println("===========================================================================");
        System.out.printf("%-5s | %-15s | %-20s | %-25s | %-10s%n", "No.", "User ID", "Name", "Email", "Status");
        System.out.println("===========================================================================");

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            System.out.printf("%-5d | %-15s | %-20s | %-25s | %-10s%n",
                    (i + 1), user.getUserID(), user.getName(), user.getEmail(), !user.isBlocked() ? "Active" : "Blocked");
        }
        System.out.println("===========================================================================");
    }


    public static void displayUserAccess(ArrayList<User> userList) {
        if (userList.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        System.out.println("==============================================");
        System.out.printf("%-5s | %-15s | %-20s | %-25s%n","No.", "User ID", "Name", "Quiz-creatable");
        System.out.println("==============================================");

        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            System.out.printf("%-5d | %-15s | %-20s | %-25s%n",
                    (i + 1), user.getUserID(), user.getName(), user.isCanModify() ? "Yes" : "No");
        }
        System.out.println("==============================================");
    }

}
