package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import server.models.Course;
import server.models.RegistrationForm;
import java.util.Scanner;

public class LineClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1337;

    public static void main(String[] args) {
        System.out.println("*** Bienvenue au portail d'inscription de cours de l'Udem ***");
        // Création du client
        LineClient client = new LineClient();
        client.sessionsMessage();

        Scanner scanner = new Scanner(System.in);
        int etat = 0;
        String session = null;
        boolean run = true;
        while (run) {
            // Choix session
            if (etat == 0) {
                int choix1 = Integer.parseInt(scanner.nextLine());
                if (choix1 == 1 || choix1 == 2 || choix1 == 3) {
                    switch (choix1) {
                        case 1:
                            session = "Automne";
                            break;
                        case 2:
                            session = "Hiver";
                            break;
                        case 3:
                            session = "Ete";
                    }
                    client.choiceSession(choix1);
                    etat = 1;
                } else {
                    throw new IllegalArgumentException("Entrée invalide");
                }
            }
            // Choix inscription ou sélection d'une autre session
            if (etat == 1) {
                int choix2 = Integer.parseInt(scanner.nextLine());
                if (choix2 == 1) {
                    client.sessionsMessage();
                    etat = 0;

                } else if (choix2 == 2) {
                    etat = 2;
                } else {
                    throw new IllegalArgumentException("Entrée invalide");
                }
            }
            // Envoi de l'inscription
            if (etat == 2) {

                System.out.print("\n" + "Veuiller saisir votre prénom: ");
                String prenom = scanner.nextLine();
                System.out.print("Veuiller saisir votre nom: ");
                String nom = scanner.nextLine();
                System.out.print("Veuiller saisir votre email: ");
                String email = scanner.nextLine();
                System.out.print("Veuiller saisir votre matricule: ");
                String matricule = scanner.nextLine();
                System.out.print("Veuiller saisir le code du cours: ");
                String code = scanner.nextLine();

                if (!client.verifyEmail(email)) {
                    throw new IllegalArgumentException("L'addrese courriel rentré est invalide");
                }
                if (!client.verifyCodeCourse(code, session)) {
                    throw new IllegalArgumentException("Le code du cours rentré est invalide");
                }
                if (!client.verifyMatricule(matricule)) {
                    throw new IllegalArgumentException("Le matricule rentré est invalide");
                } 
                else {
                    client.inscription(nom, prenom, email, matricule, session, code);
                }
                run = false;
            }
        }
    }

    // Fonction qui affiche le message d'accueil
    public void sessionsMessage() {

        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours");
        System.out.println("1.Automne" + "\n" + "2.Hiver" + "\n" + "3.Été");
        System.out.print("> Choix: ");

    }
}