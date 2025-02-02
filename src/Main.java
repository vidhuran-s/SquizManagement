import squizbox.Quiz;
import squizbox.Squizbox;
import userdetails.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args) throws IOException {
		System.out.println("Welcome to Squiz Box");
		loop1: while (true) {
			System.out.println("1) Sign-up\n2) Login\n0) Exit");
			int options;
			while (true) {
				if (scanner.hasNextInt()) {
					options = scanner.nextInt();
					break;
				}
				else {
					System.out.println("Enter a valid number");
					scanner.nextLine();
				}
			}
			scanner.nextLine();

			switch (options) {
				case 1:
					signUp();
					break;
				case 2:
					login();
					break loop1;
				case 0:
					System.out.println("Successfully Exited");
					break loop1;
				default:
					System.out.println("Invalid Option");
					continue loop1;
			}

		}
	}

	public static void signUp() throws IOException {
		System.out.println("Sign-up Page");
		System.out.println("Enter your name");
		String name = scanner.nextLine();
		if(name.isEmpty()) {
			System.out.println("Provide a valid name");
			return;
		}
		System.out.println("Enter your Email id");
		String email = scanner.nextLine();
		System.out.println("Enter the password (typical password)");
		String password = scanner.nextLine();
		if (UserAuthencation.isValidEmail(email) && UserAuthencation.isValidPassword(password)) {
			System.out.println("Enter the password once again to confirm it");
			String confirmPassword = scanner.nextLine();
			if (password.equals(confirmPassword)) {
//				if (!UserAuthencation.checkLogin(email, password)) {
//					User currentUser = UserAuthencation.registerUserDetails(name, email, password);
//					currentUser.showUserID();
//					handleUserMenu(currentUser);
//				} else {
//					System.out.println("The User already exists. Try to login.");
//				}
				if (!Jdbc.findUser(email)) {
					User currentUser = new User(name, email, User.encryptPassword(password));
					Jdbc.insertUser(currentUser);
					currentUser.showUserID();
					handleUserMenu(currentUser);
				} else {
					System.out.println("The User already exists. Try to login.");
				}
			}
			else {
				System.out.println("Password and Confirm Password are different\nCheck the Password twice");
			}
		}
		else {
			System.out.println("Your email or password is not valid");
		}
	}

	public static void login() throws IOException {
		System.out.println("Enter your Email id");
		String email = scanner.nextLine();
		System.out.println("Enter the password");
		String password = scanner.nextLine();
		if (!Admin.validateAdmin(email, password)) {
//			if (UserAuthencation.checkLogin(email, password)) {
//				User currentUser = UserAuthencation.userCreate(email, password);
//				handleUserMenu(currentUser);
//			}
//			else {
//				System.out.println("Provide valid credentials");
//			}
			if (Jdbc.findUser(email)) {
				if (Jdbc.isBlockedUser(email)) {
					System.out.println("The given email-ID is temporarily blocked. Contact Admin");
					return;
				}
				User currentUser = Jdbc.getUser(email);
				handleUserMenu(currentUser);
			}
			else {
				System.out.println("Provide valid credentials");
			}
		}
		else {
			handleAdminMenu();
		}
	}

	public static void handleUserMenu(User currentUser) throws IOException {
		scanner = new Scanner(System.in);
		ArrayList<Squizbox> quizList = Jdbc.getQuizByUserId(currentUser.getUserID());
		currentUser.setQuizList(quizList);
		loop2: while (true) {
			System.out.println("User Menu\n1) Quiz\n0) exit");
			int userOptions;
			while (true) {
				if (scanner.hasNextInt()) {
					userOptions = scanner.nextInt();
					break;
				}
				else {
					System.out.println("Enter a valid number");
					scanner.nextLine();
				}
			}
			scanner.nextLine();

			switch (userOptions) {
				case 1:
					currentUser.showUserMenu();
					int userOption = scanner.nextInt();
					scanner.nextLine();
					switch (userOption) {
						case 1:
							System.out.println("Welcome to Default Quiz");
							loopDefaultQuiz: while (true) {
								Quiz.showDefaultQuiz();
								int userChoice = -1;
								if (scanner.hasNextInt()) {
									userChoice = scanner.nextInt();
								} else {
									System.out.println("Invalid input. Please enter a valid number.");
									scanner.next();
									continue;
								}

								switch (userChoice) {
									case 1:
										Squizbox history = Quiz.historyQuiz();
										Quiz.handleQuiz(history);
										break;
									case 2:
										Squizbox computerScience = Quiz.computerScienceQuiz();
										Quiz.handleQuiz(computerScience);
										break;
									case 3:
										Squizbox biology = Quiz.biologyQuiz();
										Quiz.handleQuiz(biology);
										break;
									case 4:
										Squizbox generalKnow = Quiz.generalKnowledgeQuiz();
										Quiz.handleQuiz(generalKnow);
										break;
									case 0:
										System.out.println("Exited Successfully");
										break loopDefaultQuiz;
									default:
										System.out.println("Invalid Option");
										continue loopDefaultQuiz;
								}
							}
							break;
						case 2:
							sharingQuiz: while (true) {
								System.out.println("Enter the Quiz-ID");
								String quizCode = scanner.nextLine();
//								Squizbox askedQuiz = UserAuthencation.shareQuiz(currentUser.getUserID(), quizCode);
								Squizbox askedQuiz = Jdbc.getQuizByQuizId(quizCode);
								if (askedQuiz != null) {
									Quiz.handleQuiz(askedQuiz);
									break;
								}
							}
							break;
						case 3:
							if (currentUser.isCanModify()) {
								Squizbox currentQuiz = currentUser.makeQuiz();
//								UserAuthencation.quizStore(currentQuiz);
								Jdbc.insertQuiz(currentQuiz);
//								quizList = UserAuthencation.readingQuiz(currentUser.getUserID());
//								currentUser.setQuizList(quizList);
								currentUser.quizList.add(currentQuiz);
							}
							else {
								System.out.println("You dont have the access to create quiz");
							}
							break;
						case 4:
							showuserQuizMenu: while (true) {
								int menuLimit = currentUser.showUsercreatedQuizMenu();
								if (menuLimit != 0) {
									userOption = scanner.nextInt();
									if (userOption >= menuLimit || userOption > 0) {
										Squizbox userSelectedQuiz = currentUser.getQuizList().get(userOption - 1);
//										Quiz.handleQuiz(userSelectedQuiz);
										Quiz.showCurrentQuiz(userSelectedQuiz);
										break;
									}
									else if (userOption == 0) {
										break showuserQuizMenu;
									}
									else {
										System.out.println("Provide an valid option");
										continue;
									}
								}
								else {
									System.out.println();
									break showuserQuizMenu;
								}
							}
							break;
						case 5:
							deleteUserCreatedQuiz: while (true) {
								int menuLimit = currentUser.showUsercreatedQuizMenu();
								if (menuLimit != 0) {
									userOption = scanner.nextInt();
									if (userOption <= menuLimit && userOption > 0) {
										Squizbox userSelectedQuiz = currentUser.getQuizList().get(userOption - 1);
//										UserAuthencation.removeQuizBox(currentUser.getUserID(), userSelectedQuiz.getsQuizBoxID());
										Jdbc.removeQuiz(userSelectedQuiz.getsQuizBoxID());
										quizList.remove(userSelectedQuiz);
//										quizList.clear();
//										quizList = UserAuthencation.readingQuiz(currentUser.getUserID());
										break;
									}
									else if (userOption == 0) {
										break;
									}
									else {
										System.out.println("Provide a valid option");
										continue;
									}
								}
								else {
									break;
								}
							}
						default:
							continue loop2;
					}
					break;

				case 0:
					System.out.println("Logged out successfully!");
					break loop2;

				default:
					System.out.println("Invalid Option");
					continue loop2;
			}
		}
	}


	public static void handleAdminMenu() throws IOException {
//		ArrayList<User> userList = UserAuthencation.readAllUsers();
		ArrayList<User> userList = Jdbc.getAllUsers();
		loopAdmin: while (true) {
			Admin.showAdminMenu();
			int adminOption = scanner.nextInt();
			scanner.nextLine();
			switch (adminOption) {
				case 1:
					User.displayUserDetails(userList);
					break;
				case 2:
					User.displayUserAccess(userList);
					System.out.println("Enter User ID to manage quiz access or Press 0 to exit");
					String givenUserId = scanner.nextLine();
					if (givenUserId.equals("0")) continue;
//					User currentUser = UserAuthencation.findUserById(givenUserId);
					boolean isItUser = Jdbc.findUserbyUserId(givenUserId);
					if (isItUser) {
						System.out.println("1. Enable Quiz Access");
						System.out.println("2. Disable Quiz Access");
						adminOption = scanner.nextInt();
						scanner.nextLine();

						if (adminOption == 1) {
//							UserAuthencation.updateModifyStatus(givenUserId, true);
							Jdbc.setQuizModifyStatus(givenUserId, true);
//							currentUser = UserAuthencation.findUserById(givenUserId);
							userList = Jdbc.getAllUsers();
						}
						else if (adminOption == 2) {
//							UserAuthencation.updateModifyStatus(givenUserId, false);
							Jdbc.setQuizModifyStatus(givenUserId, false);
//							currentUser = UserAuthencation.findUserById(givenUserId);
							userList = Jdbc.getAllUsers();
						}
						else {
							System.out.println("Provide an valid option");
						}
					}
					else {
						System.out.println("User not found");
					}
					break;
				case 3:
					User.displayUserDetails(userList);
					System.out.println("Enter User ID to remove the user or Press 0 to exit");
					givenUserId = scanner.nextLine();
					if (givenUserId.equals("0")) continue;
//					User currentUser = UserAuthencation.findUserById(givenUserId);
					isItUser = Jdbc.findUserbyUserId(givenUserId);
					if (isItUser) {
						System.out.println("1. Block account");
						System.out.println("2. Unblock account");
						adminOption = scanner.nextInt();
						scanner.nextLine();

						if (adminOption == 1) {
//							UserAuthencation.updateModifyStatus(givenUserId, true);
							Jdbc.setisBlockedStatus(givenUserId, true);
//							currentUser = UserAuthencation.findUserById(givenUserId);
							userList = Jdbc.getAllUsers();
						}
						else if (adminOption == 2) {
//							UserAuthencation.updateModifyStatus(givenUserId, false);
							Jdbc.setisBlockedStatus(givenUserId, false);
//							currentUser = UserAuthencation.findUserById(givenUserId);
							userList = Jdbc.getAllUsers();
						}
						else {
							System.out.println("Provide an valid option");
						}
					}
					else {
						System.out.println("User not found");
					}
					break;
				case 0:
					break loopAdmin;
				default:
					System.out.println("Invalid option. Please try again.");
					break;
			}
		}
	}
}