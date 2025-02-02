package userdetails;

import squizbox.Option;
import squizbox.Question;
import squizbox.Quiz;
import squizbox.Squizbox;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Jdbc {
    private static final String URL = "jdbc:mysql://localhost:3306/Quiz";
    private static final String userName = "root";
    private static final String password = "Vidhuran@zoho2025";

    static  Connection connection = null;

    private static Connection getConnection() throws SQLException {
        if(connection == null){
            connection = DriverManager.getConnection(URL,userName,password);
        }
        return connection;
    }


    public static boolean insertUser(User givenUser) {
        String insertUserQuery = "insert into Users (User_ID, Name, Email, Password, canModify, isBlocked, isDeleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(insertUserQuery);

            prepStmt.setString(1, givenUser.getUserID());
            prepStmt.setString(2, givenUser.getName());
            prepStmt.setString(3, givenUser.getEmail());
            prepStmt.setString(4, givenUser.getPassword());
            prepStmt.setBoolean(5, givenUser.isCanModify());
            prepStmt.setBoolean(6, givenUser.isBlocked());
            prepStmt.setBoolean(7, givenUser.isDeleted());
            int isDone = prepStmt.executeUpdate();
            return isDone > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean insertQuiz(Squizbox givenQuiz) {
        String insertQuizQuery = "insert into Quizzes (Quiz_ID, Name, isQuiz, User_ID) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(insertQuizQuery);

            prepStmt.setString(1, givenQuiz.getsQuizBoxID());
            prepStmt.setString(2, givenQuiz.getBoxName());
            prepStmt.setBoolean(3, givenQuiz.isQuiz());
            prepStmt.setString(4, givenQuiz.getUserID());

            int isDone = prepStmt.executeUpdate();
            if (isDone > 0) {
                for (int i = 0; i < givenQuiz.getQuestions().size(); i++) {
                    insertQuestion(givenQuiz.getQuestions().get(i));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean insertQuestion(Question givenQuestion) {
        String insertQuestionQuery = "insert into Questions (Question_ID, Description, Quiz_ID) VALUES (?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(insertQuestionQuery);

            prepStmt.setString(1, givenQuestion.getQuestionID());
            prepStmt.setString(2, givenQuestion.getQuestion());
            prepStmt.setString(3, givenQuestion.getsQuizBoxID());

            int isDone = prepStmt.executeUpdate();
            if (isDone > 0) {
                for (int i = 0; i < givenQuestion.getOptions().size(); i++) {
                    insertOption(givenQuestion.getOptions().get(i));
                }
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean insertOption(Option givenOption) {
        String insertOptionQuery = "insert into Options (Option_ID, Description, isCorrect, Question_ID) VALUES (?, ?, ?, ?)";
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(insertOptionQuery);

            prepStmt.setString(1, givenOption.getOptionID());
            prepStmt.setString(2, givenOption.getOption());
            prepStmt.setBoolean(3, givenOption.isCorrect());
            prepStmt.setString(4, givenOption.getQuestionID());
            int isDone = prepStmt.executeUpdate();
            return isDone > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUser(String emailId) {
        String getUserQuery = "SELECT * FROM Users WHERE Email = ?";
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getUserQuery);
            prepStmt.setString(1, emailId);

            ResultSet rSet = prepStmt.executeQuery();

            if (rSet.next()) {
                String userId = rSet.getString("User_ID");
                String name = rSet.getString("Name");
                String email = rSet.getString("Email");
                String password = rSet.getString("Password");
                boolean canModify = rSet.getBoolean("canModify");
                boolean isBlocked = rSet.getBoolean("isBlocked");
                boolean isDeleted = rSet.getBoolean("isDeleted");
//                ArrayList<Squizbox> quizList = getQuizByUserId(userId);
                user = new User(name, email, password);
                user.setUserID(userId);
                user.setCanModify(canModify);
                user.setBlocked(isBlocked);
                user.setDeleted(isDeleted);
//                user.setQuizList(quizList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public static ArrayList<User> getAllUsers() {
        String getAllUsersQuery = "select * from Users";
        ArrayList<User> userList = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getAllUsersQuery);

            ResultSet rSet = prepStmt.executeQuery();

            while (rSet.next()) {
                User user = null;
                String userId = rSet.getString("User_ID");
                String name = rSet.getString("Name");
                String email = rSet.getString("Email");
                String password = rSet.getString("Password");
                boolean canModify = rSet.getBoolean("canModify");
                boolean isBlocked = rSet.getBoolean("isBlocked");
                boolean isDeleted = rSet.getBoolean("isDeleted");
//                ArrayList<Squizbox> quizList = getQuizByUserId(userId);
                user = new User(name, email, password);
                user.setUserID(userId);
                user.setCanModify(canModify);
                user.setBlocked(isBlocked);
                user.setDeleted(isDeleted);
//                user.setQuizList(quizList);
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public static Squizbox getQuizByQuizId(String quizId) {
        String getQuizQuery = "select * from Quizzes where Quiz_ID = ?";
        Squizbox quiz = null;
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getQuizQuery);

            prepStmt.setString(1, quizId);
            ResultSet rSet = prepStmt.executeQuery();

            if (rSet.next()) {
                String quizID = rSet.getString("Quiz_ID");
                String name = rSet.getString("Name");
                boolean isQuiz = rSet.getBoolean("isQuiz");
                String userId = rSet.getString("User_ID");
                if (isAvailableQuiz(quizID)) {
                    ArrayList<Question> questionList = getQuestions(quizID);
                    quiz = new Squizbox(name, isQuiz, userId);
                    quiz.setsQuizBoxID(quizID);
                    quiz.setQuestions(questionList);
                }
                else {
                    System.out.println("No quiz found. Check the Quiz_ID again");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quiz;
    }

    public static ArrayList<Squizbox> getQuizByUserId(String userId) {
        String getQuizQuery = "select * from Quizzes where User_ID = ?";
        ArrayList<Squizbox> quizList = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getQuizQuery);

            prepStmt.setString(1, userId);
            ResultSet rSet = prepStmt.executeQuery();

            while (rSet.next()) {
                String quizID = rSet.getString("Quiz_ID");
                String name = rSet.getString("Name");
                boolean isQuiz = rSet.getBoolean("isQuiz");
                String userID = rSet.getString("User_ID");
                if (isQuiz) {
                    ArrayList<Question> questionList = getQuestions(quizID);
                    Squizbox quiz = new Squizbox(name, isQuiz, userID);
                    quiz.setsQuizBoxID(quizID);
                    quiz.setQuestions(questionList);
                    quizList.add(quiz);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizList;
    }

    public static ArrayList<Question> getQuestions(String quizId) {
        String getQuestionQuery = "select * from Questions where Quiz_ID = ?";
        ArrayList<Question> questionList = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getQuestionQuery);

            prepStmt.setString(1, quizId);
            ResultSet rSet = prepStmt.executeQuery();

            while (rSet.next()) {
                String questionId = rSet.getString("Question_ID");
                String description = rSet.getString("Description");
                String quizID = rSet.getString("Quiz_Id");
                ArrayList<Option> optionList = getOptions(questionId);
                Question question = new Question(description, quizID);
                question.setQuestionID(questionId);
                questionList.add(question);
                question.setOptions(optionList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questionList;
    }

    public static ArrayList<Option> getOptions(String questionId) {
        String getOptionQuery = "select * from Options where Question_ID = ?";
        ArrayList<Option> optionList = new ArrayList<>();

        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getOptionQuery);

            prepStmt.setString(1, questionId);
            ResultSet rSet = prepStmt.executeQuery();

            while (rSet.next()) {
                String optionId = rSet.getString("Option_ID");
                String description = rSet.getString("Description");
                boolean isCorrect = rSet.getBoolean("isCorrect");
                String questionID = rSet.getString("Question_ID");
                Option option = new Option(description, questionID);
                option.setOptionID(optionId);
                option.setCorrect(isCorrect);
                optionList.add(option);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return optionList;
    }

    private static boolean deleteQuiz(String quizId) {
        String deleteQuizQuery = "update Quizzes set isQuiz = ? where Quiz_ID = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(deleteQuizQuery);
            prepStmt.setBoolean(1, false);
            prepStmt.setString(2, quizId);

            int isDone = prepStmt.executeUpdate();
            return isDone > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isAvailableQuiz(String quizId) {
        String isAvailableQuery = "select isQuiz from Quizzes where Quiz_ID = ?";
        boolean isAvailable = true;
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(isAvailableQuery);
            prepStmt.setString(1, quizId);
            ResultSet rSet = prepStmt.executeQuery();
            if (rSet.next()) {
                isAvailable = rSet.getBoolean("isQuiz");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isAvailable;
    }

    public static void removeQuiz(String quizId) {
        boolean isAvailable = isAvailableQuiz(quizId);
        if (isAvailable) {
            deleteQuiz(quizId);
            System.out.println("The given Quiz is successfully deleted");
        }
        else System.out.println("There is no Quiz is found with the given ID");
    }

    public static void checkLogin() {}

    public static boolean findUser(String givenEmail) {
        String getUserQuery = "SELECT Email FROM Users WHERE Email = ?";
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getUserQuery);
            prepStmt.setString(1, givenEmail);

            ResultSet rSet = prepStmt.executeQuery();

            if (rSet.next()) {
                String email = rSet.getString("Email");
                if (givenEmail.equals(email)) return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean findUserbyUserId(String userId) {
        String getUserQuery = "SELECT User_ID FROM Users WHERE User_ID = ?";
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getUserQuery);
            prepStmt.setString(1, userId);

            ResultSet rSet = prepStmt.executeQuery();

            if (rSet.next()) {
                String getteduserID = rSet.getString("User_ID");
                if (userId.equals(getteduserID)) return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean verifyUser(String givenEmail, String givenPassword) {
        String getUserQuery = "SELECT Email, Password FROM Users WHERE Email = ?";
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getUserQuery);
            prepStmt.setString(1, givenEmail);

            ResultSet rSet = prepStmt.executeQuery();

            if (rSet.next()) {
                String email = rSet.getString("Email");
                String password = rSet.getString("Password");
                if (givenEmail.equals(email) && User.decryptPassword(password).equals(givenPassword)) return true;
                else return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void setQuizModifyStatus(String userId, boolean canModify) {
        String modifyQuizQuery = "update Users set canModify = ? where User_ID = ?";

        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(modifyQuizQuery);
            prepStmt.setBoolean(1, canModify);
            prepStmt.setString(2, userId);
            int isDone = prepStmt.executeUpdate();
            if (isDone > 0) {
                System.out.println("The Quiz Modification status is successfully changed");
            }
            else {
                System.out.println("Something went wrong, Try Again");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setisBlockedStatus(String userId, boolean isBlocked) {
        String modifyQuizQuery = "update Users set isBlocked = ? where User_ID = ?";

        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(modifyQuizQuery);
            prepStmt.setBoolean(1, isBlocked);
            prepStmt.setString(2, userId);
            int isDone = prepStmt.executeUpdate();
            if (isDone > 0) {
                System.out.println("The block status status is successfully changed");
            }
            else {
                System.out.println("Something went wrong, Try Again");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setisDeletedStatus(String userId, boolean isDeleted) {
        String modifyQuizQuery = "update Users set isDeleted = ? where User_ID = ?";

        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(modifyQuizQuery);
            prepStmt.setBoolean(1, isDeleted);
            prepStmt.setString(2, userId);
            int isDone = prepStmt.executeUpdate();
            if (isDone > 0) {
                System.out.println("The account is successfully deleted");
            }
            else {
                System.out.println("Something went wrong, Try Again");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isBlockedUser(String givenEmail) {
        String getUserQuery = "SELECT isBlocked FROM Users WHERE Email = ?";
        User user = null;
        try {
            Connection connection = getConnection();
            PreparedStatement prepStmt = connection.prepareStatement(getUserQuery);
            prepStmt.setString(1, givenEmail);

            ResultSet rSet = prepStmt.executeQuery();

            if (rSet.next()) {
                boolean isBlocked = rSet.getBoolean("isBlocked");
                if (isBlocked) return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
