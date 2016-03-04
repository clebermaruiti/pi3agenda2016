/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.pi3.clebermaruiti.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author clebermaruiti
 */
public class Agenda {
    public static Statement st; // Preparacao para conectar com o banco de dados (java.sql)
    public static ResultSet rs; // Resultado da conexao com o banco de dados (java.sql)

    private Connection obterConexao() throws SQLException, ClassNotFoundException {
        Connection conn = null;

        //Passo 1: Registrar drive JDBC
        Class.forName("org.apache,derby.jdbc.ClientDataSouce");

        //Passo 2: Abrir a conexão
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/agendabd");
            
        return conn;
    }
}


