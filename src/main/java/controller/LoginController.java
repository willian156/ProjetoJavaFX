package controller;

import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Login;
import java.io.IOException;
import java.sql.*;

import static dao.LoginDAO.create;
import static dao.LoginDAO.logar;

public class LoginController {

    @FXML
    Button btn_logar;
    @FXML
    Button btn_criar;
    @FXML
    Button btn_deletar;
    @FXML
    TextField
                tx_login,
                tx_senha;

    public static Login usuarioLogado;

    public void login(ActionEvent event) throws SQLException, IOException {

        Login login = new Login();

        login.setLogin(tx_login.getText());
        login.setSenha(tx_senha.getText());

        int log = logar(login);

        if(log == 1){

            scenes.LoginScene.menuSc(event);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Usuário encontrado!");
            alert.setContentText("Login executado com sucesso!");
            alert.show();

        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Usuário não encontrado!");
            alert.setContentText("Usuário não encontrado! Por favor, verifique seu login e senha.");
            alert.show();
        }
    }

    public void cadastrar(ActionEvent event) throws SQLException {
        Login login = new Login();
        login.setLogin(tx_login.getText());
        login.setSenha(tx_senha.getText());
        create(login);

    }

    public void deletar(ActionEvent event) throws SQLException, IOException {
        Connection connection = DbConnection.getConnectionSqlite();
        String delete = "delete from Logins where Login = ?";
        PreparedStatement stmt = connection.prepareStatement(delete);
        stmt.setString(1, tx_login.getText());
        stmt.execute();
        stmt.close();


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login deletado");
        alert.setContentText("Login deletado com Sucesso!");
        alert.show();
    }
}
