package squizbox;

public class Option
{
    String option;
    boolean isCorrect;
    String questionID;
    String optionID;

    public Option(String option, String questionID) {
        this.option = option;
        this.isCorrect = false;
        this.questionID = questionID;
        this.optionID = "opt-" + System.currentTimeMillis();
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        this.isCorrect = correct;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getOptionID() {
        return optionID;
    }

    public void setOptionID(String optionID) {
        this.optionID = optionID;
    }

    public static Option createOption(String questionId, int sNum)
    {
        System.out.println("Enter the option " + (sNum + 1));
        String givenOption = Quiz.squizScanner.nextLine();
        Option currentOption = new Option(givenOption, questionId);
        return currentOption;
    }
}
