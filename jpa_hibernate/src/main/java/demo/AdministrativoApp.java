package demo;

import entities.Produto;
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

    }
}
