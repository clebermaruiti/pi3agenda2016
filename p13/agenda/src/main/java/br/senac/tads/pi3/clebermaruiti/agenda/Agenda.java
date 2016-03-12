/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.clebermaruiti.agenda;

import com.sun.org.apache.bcel.internal.generic.TABLESWITCH;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Optional;

/**
 *
 * @author clebermaruiti
 */

public class Agenda {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Agenda ag = new Agenda();
//        Scanner sc = new Scanner(System.in);
        int n = 1;

        System.out.println("=============MENU=============");
        System.out.println("1-Inserir um contato");
        System.out.println("2-Alterar um contato");
        System.out.println("3-Consultar um contato");
        System.out.println("4-Remover um contato");
        System.out.println("5-Listar contatos");
        System.out.println("6-Sair");

        while (n != 6) {
            System.out.println("Digite uma opção do menu:");
            n = sc.nextInt();

            switch (n) {
                case 1:
                    ag.inserirPessoas();
                    break;
                case 5:
                    ag.listarPessoas();
                    break;
                default:
                    System.out.println("Erro");

            }
        }
    }

    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        // Passo 1: Registrar driver JDBC.
        Class.forName("org.apache.derby.jdbc.ClientDataSource");

        // Passo 2: Abrir a conexão
        conn = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/agendabd;SecurityMechanism=3",
                "app", // usuario
                "app"); // senha
        return conn;
    }

    public void inserirPessoas() {
        Statement stmt = null;
        Connection conn = null;
        Long id;
        String nome;
        String dataNasc;
        String email;
        String telefone;
        System.out.println("Digite o nome do contato");
        nome = sc.next();
        System.out.println("Digite a data de nascimento do contato");
        dataNasc = sc.next();
        System.out.println("Digite o telefone do contato");
        telefone = sc.next();
        System.out.println("Digite o email do contato");
        email = sc.next();

        try {

            //Abrindo a conexão
            conn = obterConexao();
            stmt = conn.createStatement();

            //Executa a query de inserção
            java.sql.Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO TB_CONTATO (NM_CONTATO,DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL, DT_CADASTRO) VALUES ("
                    + nome + ",'"
                    + dataNasc + "','"
                    + telefone + "','"
                    + email + "')");

            System.out.println("Contato inserido com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void listarPessoas() {

        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT ID_CONTATO, NM_CONTATO, DT_NASCIMENTO, VL_TELEFONE, VL_EMAIL FROM TB_CONTATO";
        try {
            conn = obterConexao();
            stmt = conn.createStatement();
            ResultSet resultados = stmt.executeQuery(sql);

            DateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

            while (resultados.next()) {
                Long id = resultados.getLong("ID_CONTATO");
                String nome = resultados.getString("NM_CONTATO");
                Date dataNasc = resultados.getDate("DT_NASCIMENTO");
                String email = resultados.getString("VL_EMAIL");
                String telefone = resultados.getString("VL_TELEFONE");
                System.out.println(String.valueOf(id) + ", " + nome + ", " + formatadorData.format(dataNasc) + ", " + email + ", " + telefone);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
