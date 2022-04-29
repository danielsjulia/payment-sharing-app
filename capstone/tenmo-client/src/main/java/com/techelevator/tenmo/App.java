package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.util.BasicLogger;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        accountService.setAuthToken(currentUser.getToken());
        transferService.setAuthToken(currentUser.getToken());

        System.out.println("***DEBUG***");
        System.out.println(currentUser.getToken());
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
                transferMenu();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        BigDecimal currentBalance = accountService.getBalance();
        System.out.println("Your current account balance is: $" + currentBalance);
	}

	private void viewTransferHistory() {
		List<TransferDetails> transferHistory = accountService.getPastTransfers();
        consoleService.printTransferHistory(transferHistory);
	}

    private void transferMenu() {
        int menuSelection = -1;
        long transferId = -1;
        while(menuSelection != 0) {
            consoleService.printTransferMenu();
            menuSelection = consoleService.promptForInt("Please choose an option: ");
            if (menuSelection == 1) {
                transferId = consoleService.promptForLong("Please enter Transfer ID number for details: ");
                TransferDetails transferDetails = accountService.getTransferDetails(transferId);
                String username = currentUser.getUser().getUsername();
                consoleService.printTransferDetails(transferDetails, username);
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {

        System.out.println(transferService.getUsers());

        // add checks on userID and amount
        long userId;
        BigDecimal amount;

        boolean invalidUser = true;
        do {
            userId = consoleService.promptForInt("Select a user by ID:");

            if (userId != currentUser.getUser().getId() && transferService.containsUser(userId)) {
                invalidUser = false;
            } else {
                System.out.println("Invalid selection. Try again.");
            }

        } while (invalidUser);


        boolean invalidAmount = true;
        do {
            amount = consoleService.promptForBigDecimal("Enter the amount you wish to transfer:");
            Long currentUserId = currentUser.getUser().getId();
            BigDecimal currentUserBalance = accountService.getBalance();

            //|| amount.compareTo(currentUserBalance) == 0

            if (amount.compareTo(new BigDecimal("0.0")) > 0 && amount.compareTo(currentUserBalance) <= 0) {
                invalidAmount = false;
            } else {
                System.out.println("You cannot transfer that amount. Try again.");
            }

        } while (invalidAmount);

        // start transfer here
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setUserFromId(currentUser.getUser().getId());
        transferDTO.setUserToId(userId);
        transferDTO.setTransferAmount(amount);

        transferService.completedTransfer(transferDTO);
        System.out.println("woo!");
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
