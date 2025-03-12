package br.unipar.programacaoweb;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class ConsomeCEP {

    //TODO
    // 1. solicite um cep ao usuário (pode ser joptionpane)
    // 2. verifique se existe o cep na base de dados
    // 3. se não existir, consulte o cep na api do viacep
    // 4. salve o novo cep na base de dados com data e hora da consulta

    public static void main(String[] args) {
        try {
            //bloco para consultar via cep
//            String cep = "85904180"; // CEP que queremos consultar
//            String url = "https://viacep.com.br/ws/" + cep + "/json/"; // URL que pegamos no site do ViaCEP
//
//            URL urlViaCEP = new URL(url); // objeto URL com a instancia do VIA CEP
//            HttpURLConnection conexao = (HttpURLConnection) urlViaCEP.openConnection(); // objeto para abrir a conexão
//            conexao.setRequestMethod("GET"); // método GET (vamos só pegar informações)
//
//            BufferedReader leitor = // objeto para ler a resposta
//                    new BufferedReader(new InputStreamReader(conexao.getInputStream())); // rio de informações que está vindo da conexão
//
//            String linha; // variável para armazenar a linha que estamos lendo
//            StringBuilder resposta = new StringBuilder(); // variável para construir e armazenar a resposta completa
//
//            while ((linha = leitor.readLine()) != null) { // enquanto tivermos linhas para ler
//                resposta.append(linha + "\n"); // adicionamos a linha na resposta
//            }
//
//            System.out.println(resposta.toString()); // exibimos a resposta completa
//
//            ObjectMapper mapper = new ObjectMapper(); // objeto para mapear o JSON para um objeto Java
//            Endereco endereco = mapper.readValue(resposta.toString(), Endereco.class); // mapeamos o JSON para um objeto Endereco
//--------------------------------------------------------------------------------
//            //bloco para inserir manualmente
//            Endereco enderecoManual = new Endereco();
//
//
//            enderecoManual.setCep("85904180");
//            enderecoManual.setLogradouro("Rua das Flores");
//            enderecoManual.setBairro("Centro");
//            enderecoManual.setLocalidade("Toledo");
//            enderecoManual.setUf("PR");
//            enderecoManual.setIbge("4127709");
//            enderecoManual.setComplemento("Casa");
//
//
//            EnderecoDAO dao = new EnderecoDAO();
//            dao.salvar(enderecoManual);


//--------------------------------------------------------------------------------

            String cep = JOptionPane.showInputDialog("Digite um CEP: ");


            EnderecoDAO dao = new EnderecoDAO();


            //Endereco endereco = null;
            Endereco endereco = consultaBancoLocal(cep);
            if ( endereco == null) {
                endereco = consultaViaCep(cep);
                endereco.setDataCadastro(LocalDateTime.now());
                endereco.setCep(endereco.getCep().replace("-",""));
                dao.salvar(endereco);
            }

            JOptionPane.showMessageDialog(null, "CEP: " + endereco.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Endereco consultaBancoLocal(String cep) {

        EnderecoDAO dao = new EnderecoDAO();

        //dao.buscarPorCep(cep);
        Endereco endereco = dao.buscarPorCep(cep);
        if(endereco == null){
            return null;

        }else{

            return endereco;
        }


    }


    private static Endereco consultaViaCep(String cep) throws IOException {

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        URL urlViaCep = new URL(url);
        HttpURLConnection conexao = (HttpURLConnection) urlViaCep.openConnection();
        conexao.setRequestMethod("GET");


        BufferedReader leitor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));

        String linha;
        StringBuilder resposta = new StringBuilder();

        while ((linha = leitor.readLine()) != null) {
            resposta.append(linha+"\n");

        }

        ObjectMapper mapper = new ObjectMapper();
        Endereco endereco = mapper.readValue(resposta.toString(),Endereco.class);



//        System.out.println(endereco.getCep());
//        System.out.println(endereco.getLogradouro());
//        System.out.println(endereco.getBairro());
//        System.out.println(endereco.getLocalidade());
//        System.out.println(endereco.getUf());
//        System.out.println(endereco.getIbge());
//        System.out.println(endereco.getComplemento());

        return endereco;
    }




}