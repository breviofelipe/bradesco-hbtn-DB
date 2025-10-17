package demo;

import entities.Pessoa;
import entities.Produto;
import models.PessoaModel;
import models.ProdutoModel;

import java.util.List;

public class AdministrativoApp {

    public static void main(String[] args) {
        ProdutoModel produtoModel = new ProdutoModel();

        Produto p1 = new Produto();
        p1.setNome("TV");
        p1.setPreco(300.0);
        p1.setQuantidade(100);
        p1.setStatus(true);

        // 1) Criando um produto
        produtoModel.create(p1);

        // 2) Buscando todos os produtos na base de dados
        List<Produto> produtos = produtoModel.findAll();
        System.out.println("Qtde de produtos encontrados: " + produtos.size());
        for (Produto p : produtos) {
            System.out.println("Produto: " + p.getId() + " - " + p.getNome() + " - R$" + p.getPreco());
        }

        // 3) Buscando um produto pelo ID
        if (!produtos.isEmpty()) {
            Produto primeiro = produtos.get(0);
            Produto produtoEncontrado = produtoModel.findById(primeiro);
            if (produtoEncontrado != null) {
                System.out.println("Produto encontrado: " + produtoEncontrado.getNome());
            } else {
                System.out.println("Produto não encontrado!");
            }

            // 4) Atualizando o produto encontrado
            produtoEncontrado.setPreco(produtoEncontrado.getPreco() + 50.0);
            produtoModel.update(produtoEncontrado);
            System.out.println("Produto atualizado com novo preço: " + produtoEncontrado.getPreco());

            // 5) Removendo o produto
            produtoModel.delete(produtoEncontrado);
            System.out.println("Produto removido com sucesso!");
        }

        // 6) Exibindo novamente os produtos após a exclusão
        List<Produto> produtosFinais = produtoModel.findAll();
        System.out.println("Produtos restantes na base: " + produtosFinais.size());


        // ==== TESTE PESSOA ====
        PessoaModel pessoaModel = new PessoaModel();

        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Felipe Brevio");
        pessoa1.setEmail("felipe@email.com");
        pessoa1.setIdade(30);

        // 1) Criando pessoa
        pessoaModel.create(pessoa1);

        // 2) Listando todas as pessoas
        List<Pessoa> pessoas = pessoaModel.findAll();
        System.out.println("Qtde de pessoas encontradas: " + pessoas.size());
        for (Pessoa p : pessoas) {
            System.out.println("Pessoa: " + p.getId() + " - " + p.getNome() + " - " + p.getEmail());
        }

        // 3) Buscando pessoa por ID
        if (!pessoas.isEmpty()) {
            Pessoa primeira = pessoas.get(0);
            Pessoa encontrada = pessoaModel.findById(primeira);
            if (encontrada != null) {
                System.out.println("Pessoa encontrada: " + encontrada.getNome());
            }

            // 4) Atualizando a pessoa
            encontrada.setEmail("novoemail@email.com");
            pessoaModel.update(encontrada);

            // 5) Removendo a pessoa
            pessoaModel.delete(encontrada);
        }

        // 6) Verificando se há pessoas após a exclusão
        pessoas = pessoaModel.findAll();
        System.out.println("Pessoas restantes no banco: " + pessoas.size());
    }
}
