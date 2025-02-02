package userdetails;

import squizbox.Option;
import squizbox.Question;
import squizbox.Squizbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class  UserAuthencation
{
    static File storeFile = new File("/home/vidhu-zstk391/Desktop/Squiz-Management/Userdetails/user_information.csv");
    static File storeQuiz = new File("/home/vidhu-zstk391/Desktop/Squiz-Management/Userdetails/quizzes.csv");
    static File storeQuestions = new File("/home/vidhu-zstk391/Desktop/Squiz-Management/Userdetails/questions.csv");
    static File storeOptions = new File("/home/vidhu-zstk391/Desktop/Squiz-Management/Userdetails/options.csv");
    static FileWriter writeFile = null;
    static FileWriter writeQuiz = null;
    static FileWriter writeQuestion = null;
    static FileWriter writeOption = null;
    static Scanner reader;
//    static Scanner readQuiz;
//    static Scanner readQuestion;
//    static Scanner readOption;

    static {
        try {
            reader = new Scanner(storeFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static User registerUserDetails(String name, String email, String password) throws IOException {
        User currentUser = null;
        try {
            currentUser = new User(name, email, password);
            writeFile = new FileWriter(storeFile, true);
            writeFile.write(currentUser.getName() + "," + currentUser.getEmail() + "," + User.encryptPassword(currentUser.getPassword()) + "," + currentUser.isCanModify() + "," +currentUser.getUserID() + "\n");
            System.out.println("File returned successfully");
            System.out.println("Welcome " + currentUser.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (writeFile != null) writeFile.close();
        }
        return currentUser;
    }

    public static void verifyUser(String email, String password) throws IOException
    {
        try  {
            reader = new Scanner(storeFile);
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showUserDetails()
    {
        try  {
            reader = new Scanner(storeFile);
            while (reader.hasNextLine()) {
//                System.out.println(reader.nextLine());
                String line = reader.nextLine();
                String[] userInfoList = line.split(",");
                if (userInfoList.length == 3) {
                    System.out.println("Name: " + userInfoList[0]);
                    System.out.println("Email: " + userInfoList[1]);
                    System.out.println("Password: " + userInfoList[2]);
                    System.out.println();
                }
                else {
                    System.out.println("Invalid Data");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkLogin(String email, String givenPassword)
    {
        try {
            reader = new Scanner(storeFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] userInfoList = line.split(",");
                String emailId = userInfoList[1];
                String password = userInfoList[2];

                if (emailId.equals(email) && User.decryptPassword(password).equals(givenPassword)) {
                    return true;
                } else if (emailId.contains(email) && !emailId.equals(email)) {
                    System.out.println("Your account has been deleted\n");
                    return false;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User userCreate(String email, String givenPassword)
    {
        User currentUser = null;
        try {
            reader = new Scanner(storeFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] userInfoList = line.split(",");
                String name = userInfoList[0];
                String emailId = userInfoList[1];
                String password = userInfoList[2];
                String modifyStatus = userInfoList[3];
                String userID = userInfoList[4];

                if (emailId.equals(email) && User.decryptPassword(password).equals(givenPassword)) {
                    currentUser = new User(name, emailId, password);
                    currentUser.setUserID(userID);
                    currentUser.setCanModify(Boolean.parseBoolean(modifyStatus));
                    System.out.println("\nWelcome " + currentUser.getName());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return currentUser;
    }

    public static void quizStore(Squizbox givenQuiz) throws IOException {
        try {
            writeQuiz = new FileWriter(storeQuiz, true);
            writeQuiz.write(givenQuiz.getBoxName() + "," + givenQuiz.getsQuizBoxID() + "," + givenQuiz.isQuiz() + "," + givenQuiz.getUserID() + "\n");
            ArrayList<Question> questionList = givenQuiz.getQuestions();
            for (int i = 0; i < questionList.size(); i++) {
                Question currentQuestion = questionList.get(i);
//                questionStore(currentQuestion);
                Jdbc.insertQuestion(currentQuestion);
            }
            System.out.println("Quiz Stored");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (writeQuiz != null) writeQuiz.close();
        }
    }


    public static void questionStore(Question givenQuestion) throws IOException {
        try {
            writeQuestion = new FileWriter(storeQuestions, true);
            writeQuestion.write(givenQuestion.getQuestion() + "," + givenQuestion.getQuestionID() + "," + givenQuestion.getsQuizBoxID() + "\n");
            ArrayList<Option> optionList = givenQuestion.getOptions();
            for (int i = 0; i < optionList.size(); i++) {
                Option currentOption = optionList.get(i);
//                optionStore(currentOption);
                Jdbc.insertOption(currentOption);
            }
            System.out.println("Question Stored");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (writeQuestion != null) writeQuestion.close();
        }

    }

    public static void optionStore(Option givenOption) throws IOException {
        try {
            writeOption = new FileWriter(storeOptions, true);
            writeOption.write(givenOption.getOption() + "," + givenOption.isCorrect() + "," + givenOption.getQuestionID() + "," + givenOption.getOptionID() + "\n");
            System.out.println("Option Stored");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (writeOption != null) writeOption.close();
        }
    }

    public static ArrayList<Squizbox> readingQuiz(String userId) throws FileNotFoundException {
        ArrayList<Squizbox> quizList = new ArrayList<>();
        Scanner readQuiz = new Scanner(storeQuiz);
        try {
            while (readQuiz.hasNextLine()) {
                String line = readQuiz.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[3].equals(userId)) {
                    String boxName = parts[0];
                    String sQuizBoxID = parts[1];
                    boolean isQuiz = Boolean.parseBoolean(parts[2]);
                    Squizbox currentQuiz = new Squizbox(boxName, isQuiz, userId);
                    currentQuiz.setsQuizBoxID(sQuizBoxID);
                    currentQuiz.setQuestions(readingQuestion(sQuizBoxID));
                    quizList.add(currentQuiz);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return quizList;
    }

    public static ArrayList<Question> readingQuestion(String givenQuizId) throws FileNotFoundException {
        ArrayList<Question> questionList = new ArrayList<>();
        Scanner readQuestion = new Scanner(storeQuestions);
        try {
            while (readQuestion.hasNextLine()) {
                String line = readQuestion.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[2].equals(givenQuizId)) {
                    String questionWords = parts[0];
                    String questionID = parts[1];
                    Question currentQuestion = new Question(questionWords, givenQuizId);
                    currentQuestion.setQuestionID(questionID);
                    currentQuestion.setOptions(readingOption(questionID));
                    questionList.add(currentQuestion);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return questionList;
    }

    public static ArrayList<Option> readingOption(String givenQuestionId)
    {
        ArrayList<Option> optionList = new ArrayList<>();
        try {
            Scanner readOption = new Scanner(storeOptions);
            readOption.reset();
            while (readOption.hasNextLine()) {
                String line = readOption.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 4 && parts[2].equals(givenQuestionId)) {
                    String optionWords = parts[0];
                    boolean isCorrect = Boolean.parseBoolean(parts[1]);
                    String optionId = parts[3];
                    Option currentOption = new Option(optionWords, givenQuestionId);
                    currentOption.setOptionID(optionId);
                    currentOption.setCorrect(isCorrect);
                    optionList.add(currentOption);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return optionList;
    }

    public static void removeQuizBox(String currentUserId, String oldQuizID) throws IOException {
        ArrayList<String> lines = new ArrayList<>();

        Scanner readQuiz = new Scanner(storeQuiz);
        try {
            while (readQuiz.hasNextLine()) {
                String line = readQuiz.nextLine();
                String[] parts = line.split(",");


                if (parts.length == 4 && parts[3].equals(currentUserId) && parts[1].equals(oldQuizID)) {
                    String randomID = String.valueOf(System.currentTimeMillis());
                    parts[3] = "UserID-" + randomID;
                }

                lines.add(String.join(",", parts));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            readQuiz.close();
        }

        FileWriter writer = new FileWriter(storeQuiz);
        for (String updatedLine : lines) {
            writer.write(updatedLine + "\n");
        }
        writer.close();

        System.out.println("QuizBox successfully removed");
    }

    public static Squizbox shareQuiz(String userId, String givenQuizId) throws FileNotFoundException {
        Scanner readQuiz = new Scanner(storeQuiz);
        try {
            while (readQuiz.hasNextLine()) {
                String line = readQuiz.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[1].equals(givenQuizId)) {
                    String boxName = parts[0];
                    String sQuizBoxID = parts[1];
                    boolean isQuiz = Boolean.parseBoolean(parts[2]);
                    Squizbox currentQuiz = new Squizbox(boxName, isQuiz, userId);
                    currentQuiz.setsQuizBoxID(sQuizBoxID);
                    currentQuiz.setQuestions(readingQuestion(sQuizBoxID));
                    return currentQuiz;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Quiz successfully loaded");
        return null;
    }

    public static ArrayList<User> readAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try {
            reader = new Scanner(storeFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] userInfoList = line.split(",");

                if (userInfoList.length == 5 && !userInfoList[1].contains(".0b19d63c8e")) {
                    String name = userInfoList[0];
                    String emailId = userInfoList[1];
                    String password = userInfoList[2];
                    String modifyStatus = userInfoList[3];
                    String userID = userInfoList[4];
                    User currentUser = new User(name, emailId, password);
                    currentUser.setUserID(userID);
                    currentUser.setCanModify(Boolean.parseBoolean(modifyStatus));
                    userList.add(currentUser);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static User findUserById(String userId) {
        User currentUser = null;
        try {
            reader = new Scanner(storeFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] userInfoList = line.split(",");

                if (userInfoList.length == 5) {
                    String name = userInfoList[0];
                    String emailId = userInfoList[1];
                    String password = userInfoList[2];
                    String modifyStatus = userInfoList[3];
                    String currentUserId = userInfoList[4];

                    if (currentUserId.equals(userId)) {
                        currentUser = new User(name, emailId, password);
                        currentUser.setUserID(currentUserId);
                        currentUser.setCanModify(Boolean.parseBoolean(modifyStatus));
//                        System.out.println("User found: " + currentUser.getName());
                        break;
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (currentUser == null) {
            System.out.println("No user found with the given User ID: " + userId);
        }

        return currentUser;
    }

    public static void updateModifyStatus(String userId, boolean givenStatus) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        boolean userFound = false;

        Scanner reader = new Scanner(storeFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] userInfoList = line.split(",");

            if (userInfoList.length == 5) {
                String currentUserId = userInfoList[4];

                if (currentUserId.equals(userId)) {
                    userInfoList[3] = String.valueOf(givenStatus);
                    userFound = true;
                    System.out.println("Updated Modify Status for User ID: " + userId);
                }
                lines.add(String.join(",", userInfoList));
            } else {
                lines.add(line);
            }
        }
        reader.close();

        if (userFound) {
            FileWriter writer = new FileWriter(storeFile, false);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();
//            System.out.println("File updated successfully.");
        } else {
            System.out.println("User with ID " + userId + " not found.");
        }
    }

    public static void removeUser(String userId) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        boolean userFound = false;

        Scanner reader = new Scanner(storeFile);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] userInfoList = line.split(",");

            if (userInfoList.length == 5) {
                String currentUserId = userInfoList[4];

                if (currentUserId.equals(userId)) {
                    userInfoList[1] += ".0b19d63c8e";
                    userFound = true;
                    System.out.println("User successfully removed");
                }
                lines.add(String.join(",", userInfoList));
            }
            else {
                lines.add(line);
            }
        }
        reader.close();

        if (userFound) {
            FileWriter writer = new FileWriter(storeFile, false);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();
//            System.out.println("File updated successfully.");
        } else {
            System.out.println("User with ID " + userId + " not found.");
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return Pattern.matches(passwordRegex, password);
    }
}
