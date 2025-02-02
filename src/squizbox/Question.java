package squizbox;
import java.util.ArrayList;

public class Question
{
    String question;
    String questionID;
    String sQuizBoxID;
    ArrayList<Option> options = new ArrayList<>();

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getsQuizBoxID() {
        return sQuizBoxID;
    }

    public void setsQuizBoxID(String sQuizBoxID) {
        this.sQuizBoxID = sQuizBoxID;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Question(String question, String sQuizBoxID) {
        this.question = question;
        this.questionID = "questionID-" + System.currentTimeMillis();
        this.sQuizBoxID = sQuizBoxID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void addOptions(String givenOption)
    {
        Option eachOption = new Option(givenOption, this.questionID);
        options.add(eachOption);
    }

    public void showOptions()
    {
        for (int i = 0; i < this.options.size(); i++) {
            System.out.println((i+1) + ")" + this.options.get(i).getOption());
        }
        System.out.println("Which one is the correct Option");
    }

    public boolean makeItCorrectOption(int num)
    {
        Option currentOption = this.options.get((num - 1));
        currentOption.setCorrect(true);
        boolean correct = currentOption.isCorrect();
        return correct;
    }

    public static Question createQuestion(String sQuizBoxId, int sNum)
    {
        System.out.println("Enter the question " + (sNum + 1));
        String qivenQuestion = Quiz.squizScanner.nextLine();
        Question currentQuestion = new Question(qivenQuestion, sQuizBoxId);
        ArrayList<Option> optionList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Option currentOption;
            currentOption = Option.createOption(currentQuestion.getQuestionID(), i);
            optionList.add(currentOption);
        }
        currentQuestion.setOptions(optionList);
        currentQuestion.showOptions();
        int correctOption = Quiz.squizScanner.nextInt();
        Quiz.squizScanner.nextLine();
        currentQuestion.makeItCorrectOption(correctOption);
        return currentQuestion;
    }
}
