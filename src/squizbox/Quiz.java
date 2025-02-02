package squizbox;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Quiz {

	public static Scanner squizScanner = new Scanner(System.in);
	public static ArrayList<Integer> userOptions = new ArrayList<>();

	public static Squizbox historyQuiz()
	{
		Squizbox history = new Squizbox("History", true);
		Question his_01 = new Question("Who was the first President of the United States?", history.getsQuizBoxID());
		his_01.addOptions("Abraham Lincoln");
		his_01.addOptions("George Washington");
		his_01.addOptions("Thomas Jefferson");
		his_01.addOptions("John Adams");
		boolean correctAnswer = his_01.makeItCorrectOption(2);
		history.addQuestions(his_01);

		Question his_02 = new Question("In which year did World War II end?", history.getsQuizBoxID());
		his_02.addOptions("1941");
		his_02.addOptions("1945");
		his_02.addOptions("1950");
		his_02.addOptions("1939");
		correctAnswer = his_02.makeItCorrectOption(2);
		history.addQuestions(his_02);

		Question his_03 = new Question("Which civilization is known for building the pyramids?", history.getsQuizBoxID());
		his_03.addOptions("Mesopotamians");
		his_03.addOptions("Romans");
		his_03.addOptions("Egyptians");
		his_03.addOptions("Mayans");
		correctAnswer = his_03.makeItCorrectOption(3);
		history.addQuestions(his_03);

		Question his_04 = new Question("Who is known as the Father of the Indian Nation?", history.getsQuizBoxID());
		his_04.addOptions("Jawaharlal Nehru");
		his_04.addOptions("Mahatma Gandhi");
		his_04.addOptions("Subhas Chandra Bose");
		his_04.addOptions("Bhagat Singh");
		correctAnswer = his_04.makeItCorrectOption(2);
		history.addQuestions(his_04);

		Question his_05 = new Question("What was the name of the ship that carried the Pilgrims to America in 1620?", history.getsQuizBoxID());
		his_05.addOptions("Titanic");
		his_05.addOptions("Santa Maria");
		his_05.addOptions("Beagle");
		his_05.addOptions("Mayflower");
		correctAnswer = his_05.makeItCorrectOption(4);
		history.addQuestions(his_05);

		return history;
	}

	public static Squizbox computerScienceQuiz()
	{
		Squizbox computerScience = new Squizbox("Computer Science", true);

		Question cs_01 = new Question("Who is known as the father of Computer Science?", computerScience.getsQuizBoxID());
		cs_01.addOptions("Alan Turing");
		cs_01.addOptions("Charles Babbage");
		cs_01.addOptions("John von Neumann");
		cs_01.addOptions("Bill Gates");
		boolean correctAnswer = cs_01.makeItCorrectOption(2);
		computerScience.addQuestions(cs_01);

		Question cs_02 = new Question("Which programming language is known as the mother of all languages?", computerScience.getsQuizBoxID());
		cs_02.addOptions("C");
		cs_02.addOptions("Assembly");
		cs_02.addOptions("Python");
		cs_02.addOptions("COBOL");
		correctAnswer = cs_02.makeItCorrectOption(1);
		computerScience.addQuestions(cs_02);

		Question cs_03 = new Question("What does 'HTTP' stand for?", computerScience.getsQuizBoxID());
		cs_03.addOptions("Hyper Transfer Text Protocol");
		cs_03.addOptions("Hyper Text Transmission Protocol");
		cs_03.addOptions("Hyper Text Transfer Protocol");
		cs_03.addOptions("Hyper Transfer Transmission Protocol");
		correctAnswer = cs_03.makeItCorrectOption(3);
		computerScience.addQuestions(cs_03);

		Question cs_04 = new Question("Which company developed the Java programming language?", computerScience.getsQuizBoxID());
		cs_04.addOptions("Microsoft");
		cs_04.addOptions("Apple");
		cs_04.addOptions("Sun Microsystems");
		cs_04.addOptions("IBM");
		correctAnswer = cs_04.makeItCorrectOption(3);
		computerScience.addQuestions(cs_04);

		Question cs_05 = new Question("What is the binary representation of the decimal number 5?", computerScience.getsQuizBoxID());
		cs_05.addOptions("1010");
		cs_05.addOptions("101");
		cs_05.addOptions("100");
		cs_05.addOptions("110");
		correctAnswer = cs_05.makeItCorrectOption(2);
		computerScience.addQuestions(cs_05);

		return computerScience;
	}

	public static Squizbox biologyQuiz()
	{
		Squizbox biology = new Squizbox("Biology", true);

		Question bio_01 = new Question("What is the basic unit of life?", biology.getsQuizBoxID());
		bio_01.addOptions("Atom");
		bio_01.addOptions("Molecule");
		bio_01.addOptions("Cell");
		bio_01.addOptions("Organ");
		boolean correctAnswer = bio_01.makeItCorrectOption(3);
		biology.addQuestions(bio_01);

		Question bio_02 = new Question("Which organ is responsible for pumping blood in the human body?", biology.getsQuizBoxID());
		bio_02.addOptions("Lungs");
		bio_02.addOptions("Brain");
		bio_02.addOptions("Kidney");
		bio_02.addOptions("Heart");
		correctAnswer = bio_02.makeItCorrectOption(4);
		biology.addQuestions(bio_02);

		Question bio_03 = new Question("What is the process by which plants make their own food?", biology.getsQuizBoxID());
		bio_03.addOptions("Respiration");
		bio_03.addOptions("Photosynthesis");
		bio_03.addOptions("Transpiration");
		bio_03.addOptions("Fermentation");
		correctAnswer = bio_03.makeItCorrectOption(2);
		biology.addQuestions(bio_03);

		Question bio_04 = new Question("Which part of the cell contains the genetic material?", biology.getsQuizBoxID());
		bio_04.addOptions("Nucleus");
		bio_04.addOptions("Mitochondria");
		bio_04.addOptions("Ribosome");
		bio_04.addOptions("Cytoplasm");
		correctAnswer = bio_04.makeItCorrectOption(1);
		biology.addQuestions(bio_04);

		Question bio_05 = new Question("What is the term for animals that only eat plants?", biology.getsQuizBoxID());
		bio_05.addOptions("Omnivores");
		bio_05.addOptions("Carnivores");
		bio_05.addOptions("Herbivores");
		bio_05.addOptions("Detritivores");
		correctAnswer = bio_05.makeItCorrectOption(3);
		biology.addQuestions(bio_05);

		return biology;
	}

	public static Squizbox generalKnowledgeQuiz()
	{
		Squizbox generalKnowledge = new Squizbox("General Knowledge", true);

		Question gk_01 = new Question("What is the capital city of France?", generalKnowledge.getsQuizBoxID());
		gk_01.addOptions("Berlin");
		gk_01.addOptions("Madrid");
		gk_01.addOptions("Paris");
		gk_01.addOptions("Rome");
		boolean correctAnswer = gk_01.makeItCorrectOption(3);
		generalKnowledge.addQuestions(gk_01);

		Question gk_02 = new Question("Who was the first person to walk on the moon?", generalKnowledge.getsQuizBoxID());
		gk_02.addOptions("Buzz Aldrin");
		gk_02.addOptions("Yuri Gagarin");
		gk_02.addOptions("Michael Collins");
		gk_02.addOptions("Neil Armstrong");
		correctAnswer = gk_02.makeItCorrectOption(4);
		generalKnowledge.addQuestions(gk_02);

		Question gk_03 = new Question("Which is the largest ocean in the world?", generalKnowledge.getsQuizBoxID());
		gk_03.addOptions("Atlantic Ocean");
		gk_03.addOptions("Indian Ocean");
		gk_03.addOptions("Arctic Ocean");
		gk_03.addOptions("Pacific Ocean");
		correctAnswer = gk_03.makeItCorrectOption(4);
		generalKnowledge.addQuestions(gk_03);

		Question gk_04 = new Question("What is the national animal of India?", generalKnowledge.getsQuizBoxID());
		gk_04.addOptions("Peacock");
		gk_04.addOptions("Tiger");
		gk_04.addOptions("Elephant");
		gk_04.addOptions("Lion");
		correctAnswer = gk_04.makeItCorrectOption(2);
		generalKnowledge.addQuestions(gk_04);

		Question gk_05 = new Question("Which planet is known as the Red Planet?", generalKnowledge.getsQuizBoxID());
		gk_05.addOptions("Earth");
		gk_05.addOptions("Mars");
		gk_05.addOptions("Venus");
		gk_05.addOptions("Jupiter");
		correctAnswer = gk_05.makeItCorrectOption(2);
		generalKnowledge.addQuestions(gk_05);
		return generalKnowledge;
	}

	public static void showDefaultQuiz()
	{
		System.out.println("Default Quiz Menu");
		System.out.println("1) History\n2) Computer Science\n3) Biology\n4) GK\n0) Back to user menu");
		System.out.println("Which Quiz you want to choose");
	}

	public static void handleQuiz(Squizbox quiz) {

		System.out.println("Quiz-Name: " + quiz.boxName);
		int userScore = 0;
		for (int i = 0; i < quiz.questions.size(); i++) {
			Question question = quiz.questions.get(i);
			System.out.println((i + 1) + ") " + question.getQuestion());
			question.showOptions();

			int userChoice = -1;

			while (true) {
				if (squizScanner.hasNextInt()) {
					userChoice = squizScanner.nextInt();

					if (userChoice >= 1 && userChoice <= question.options.size()) {
						userScore += Quiz.isItCorrectOption(question, userChoice);
						userOptions.add(userChoice - 1);
						break;
					} else {
						System.out.println("Please enter a number between 1 and " + question.options.size());
					}
				} else {
					System.out.println("Invalid input. Please enter a number.");
					squizScanner.next();
				}
			}

			System.out.println("You chose option " + userChoice + ": " + question.options.get(userChoice - 1).getOption());
			System.out.println("next question...\n");
		}

		System.out.println("You have completed the " + quiz.boxName + " Quiz!");
		System.out.println("Your score is " + userScore + " out of " + quiz.questions.size());
//		System.out.println("User-choice: " + userOptions.toString());
		while (true) {
			System.out.println("Would you like to see the correct answer \n1) Yes\n2)No");
			int option = 0;
			try {
				option = squizScanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Please enter a valid number");
				squizScanner.nextLine();
				continue;
			}
			if (option == 1) {
				Quiz.showCorrectAnswers(quiz);
				userOptions.clear();
				break;
			}
			else if (option == 2) {
				System.out.println("Ok done!");
				userOptions.clear();
				break;
			}
			else {
				System.out.println("Provide an valid input");
				continue;
			}
		}
	}

	public static void showCorrectAnswers(Squizbox quiz)
	{
		final String RESET = "\u001B[0m";
		final String BLUE = "\u001B[34m";
		final String GREEN = "\u001B[32m";
		final String RED = "\u001B[31m";
		final String YELLOW = "\u001B[33m";

		for (int i = 0; i < quiz.questions.size(); i++) {
			Question currentQuestion = quiz.questions.get(i);
			System.out.println(BLUE + (i + 1) + ") " + currentQuestion.getQuestion() + RESET);
			for (int j = 0; j < currentQuestion.options.size(); j++) {
				Option currentOption = currentQuestion.options.get(j);
				if (currentOption.isCorrect()) {
					System.out.println(GREEN + "   " + (j + 1) + ") " + currentOption.getOption() + RESET);
				}
				else if (j == userOptions.get(i)) {
					System.out.println(YELLOW + "   " + (j + 1) + ") " + currentOption.getOption() + RESET);
				}
				else {
					System.out.println(RED + "   " + (j + 1) + ") " + currentOption.getOption() + RESET);
				}
			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

	public static int isItCorrectOption(Question question, int option)
	{
		Option choosenOption = question.options.get(option - 1);
		if (choosenOption.isCorrect()) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public static Squizbox createQuiz(String userid)
	{
		System.out.println("Enter the Quiz-name");
		String quizName = squizScanner.nextLine();
		System.out.println("How many questions in this quiz");
		int questionNum = squizScanner.nextInt();
		squizScanner.nextLine();

		Squizbox currentQuiz = new Squizbox(quizName, true, userid);
		ArrayList<Question> questionList = new ArrayList<>();
		for (int i = 0; i < questionNum; i++) {
			Question currentQuestion = Question.createQuestion(currentQuiz.getsQuizBoxID(), i);
			questionList.add(currentQuestion);
		}
		currentQuiz.setQuestions(questionList);
		return currentQuiz;
	}

	public static void showCurrentQuiz(Squizbox quiz)
	{
		final String RESET = "\u001B[0m";
		final String BLUE = "\u001B[34m";
		final String GREEN = "\u001B[32m";
		final String RED = "\u001B[31m";
		final String YELLOW = "\u001B[33m";

		for (int i = 0; i < quiz.questions.size(); i++) {
			Question currentQuestion = quiz.questions.get(i);
			System.out.println(BLUE + (i + 1) + ") " + currentQuestion.getQuestion() + RESET);
			for (int j = 0; j < currentQuestion.options.size(); j++) {
				Option currentOption = currentQuestion.options.get(j);
				if (currentOption.isCorrect()) {
					System.out.println(GREEN + "   " + (j + 1) + ") " + currentOption.getOption() + RESET);
				}
				else {
					System.out.println(RED + "   " + (j + 1) + ") " + currentOption.getOption() + RESET);
				}
			}
		}
		System.out.println("\n" + BLUE + "Quiz-ID - " + quiz.getsQuizBoxID() + RESET);
		System.out.println(GREEN + "Share the above Quiz-ID to who wants to play this Quiz" + RESET + "\n");
	}
}
