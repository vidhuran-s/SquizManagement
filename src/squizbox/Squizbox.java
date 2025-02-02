package squizbox;
import java.util.ArrayList;

public class Squizbox
{
    String boxName;
    String sQuizBoxID;
    boolean isQuiz;
    String userID;
    ArrayList<Question> questions = new ArrayList<>();

    public Squizbox(String boxName, boolean isQuiz, String userID) {
        this.boxName = boxName;
        this.isQuiz = isQuiz;
        this.sQuizBoxID = "sQuizBoxID-" + System.currentTimeMillis();
        this.userID = userID;
    }

    public Squizbox(String boxName, boolean isQuiz) {
        this.boxName = boxName;
        this.isQuiz = isQuiz;
        this.sQuizBoxID = "sQuizBoxID-" + System.currentTimeMillis();
        this.userID = "Default-Quiz-" + System.currentTimeMillis();
    }

    public String getBoxName() {
        return boxName;
    }

    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    public void setsQuizBoxID(String sQuizBoxID) {
        this.sQuizBoxID = sQuizBoxID;
    }

    public boolean isQuiz() {
        return isQuiz;
    }

    public void setQuiz(boolean quiz) {
        isQuiz = quiz;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getsQuizBoxID() {
        return sQuizBoxID;
    }

    public void addQuestions(Question givenQuestion)
    {
        questions.add(givenQuestion);
    }

    public void showQuestions()
    {
        for (int i = 0; i < questions.size(); i++) {
            Question currentQuestion = questions.get(i);
            System.out.println((i+1) + ")" + currentQuestion.getQuestion());
            currentQuestion.showOptions();
        }
    }
}