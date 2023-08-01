package onlineExamm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface IExamService {
    void conductExam(Connection connection, Participant participant) throws SQLException;
}

abstract class AbstractExamService implements IExamService {
    protected void updateParticipantName(Connection connection, Participant participant) throws SQLException {
        String sql = "UPDATE participants SET name = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, participant.getName());
            statement.setInt(2, participant.getId());
            statement.executeUpdate();
        }
    }

    protected List<Question> getQuestionsFromDatabase(Connection connection) throws SQLException {
        List<Question> questions = new ArrayList<>();

        String sql = "SELECT * FROM questions";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Question question = new Question();
                    question.setId(resultSet.getInt("id"));
                    question.setQuestionText(resultSet.getString("question_text"));
                    question.setOptionA(resultSet.getString("option_a"));
                    question.setOptionB(resultSet.getString("option_b"));
                    question.setOptionC(resultSet.getString("option_c"));
                    question.setOptionD(resultSet.getString("option_d"));
                    question.setCorrectOption(resultSet.getString("correct_option"));
                    questions.add(question);
                }
            }
        }
        return questions;
    }

    protected void insertParticipant(Connection connection, Participant participant) throws SQLException {
        String sql = "INSERT INTO participants (name) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, participant.getName());
            statement.executeUpdate();

            // Get the generated participant ID
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    participant.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    protected void insertParticipantAnswer(Connection connection, int participantId, int questionId, String selectedOption) throws SQLException {
        String sql = "INSERT INTO participant_answers (participant_id, question_id, selected_option) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, participantId);
            statement.setInt(2, questionId);
            statement.setString(3, selectedOption);
            statement.executeUpdate();
        }
    }

    protected int getParticipantScore(Connection connection, int participantId) throws SQLException {
        String sql = "SELECT COUNT(*) AS score FROM participant_answers " +
                "JOIN questions ON participant_answers.question_id = questions.id " +
                "WHERE participant_id = ? AND participant_answers.selected_option = questions.correct_option";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, participantId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("score");
                }
            }
        }
        return 0;
    }
}

public class Main extends AbstractExamService {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Welcome to the Online Exam!");

            // For simplicity, we will add a sample participant.
            // In a real application, you would likely have an admin panel to manage participants.

            // Sample participant
            Participant participant = new Participant();
            participant.setName("John Doe");
            Main main = new Main(); // Create an instance of Main class
            main.insertParticipant(connection, participant); // Call non-static method

            // Conduct exam
            main.conductExam(connection, participant); // Call non-static method

            // Display participant's score
            int score = main.getParticipantScore(connection, participant.getId()); // Call non-static method
            System.out.println("\nYour Exam Results:");
            System.out.println("Score: " + score);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void conductExam(Connection connection, Participant participant) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Fetch questions from the database
        List<Question> questions = getQuestionsFromDatabase(connection);

        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();

        // Update participant's name in the database
        participant.setName(userName);
        updateParticipantName(connection, participant);

        System.out.println("\nInstructions: Enter the option letter (A/B/C/D) for each question.\n");

        int numOfQuestions = Math.min(10, questions.size());

        for (int i = 0; i < numOfQuestions; i++) {
            Question question = questions.get(i);

            System.out.println(question.getQuestionText());
            System.out.println("A. " + question.getOptionA());
            System.out.println("B. " + question.getOptionB());
            System.out.println("C. " + question.getOptionC());
            System.out.println("D. " + question.getOptionD());

            String selectedOption;
            do {
                System.out.print("Your answer (A/B/C/D): ");
                selectedOption = scanner.nextLine().toUpperCase();
            } while (!selectedOption.matches("[ABCD]"));

            // Insert participant's answer into the database
            insertParticipantAnswer(connection, participant.getId(), question.getId(), selectedOption);
        }

        System.out.println("\nThank you for completing the exam!");

        // Display participant's score
        int score = getParticipantScore(connection, participant.getId());
        System.out.println("\nYour Exam Results:");
        System.out.println("Name: " + participant.getName());
        System.out.println("Score: " + score + "/" + numOfQuestions);
    }
}