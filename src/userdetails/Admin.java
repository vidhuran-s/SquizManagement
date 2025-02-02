package userdetails;

public class Admin extends Person
{
    private String adminID;
    public Admin(String name, String email, String password) {
        super(name, email, password);
        this.adminID = "AdminID-001";
    }

    public Admin(String email, String password) {
        super("Admin", email, password);
    }

    public String getAdminID() {
        return adminID;
    }

    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }

    static Admin realAdmin = new Admin("Admin", "admin@email.com", User.encryptPassword("iAmAdmin123@"));

    public static boolean validateAdmin(String givenEmailId, String givenPassword) {
        String adminEmail = realAdmin.getEmail();
        String adminPassword = User.decryptPassword(realAdmin.getPassword());
        return  (adminEmail.equals(givenEmailId) && adminPassword.equals(givenPassword));
    }

    public static void showAdminMenu()
    {
        System.out.println("\n===================================");
        System.out.println("           Admin Menu              ");
        System.out.println("===================================");
        System.out.println("1. View User Details");
        System.out.println("2. Manage user's quiz permission");
        System.out.println("3. Remove user");
        System.out.println("0. Exit");
        System.out.println("===================================");
        System.out.println("Enter your choice: ");
    }
}
