package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Pessoa;

import java.util.List;

public class PessoaService {

    private EntityManagerFactory emf;
    private EntityManager em;

    public PessoaService() {
        // Inicializa o EntityManagerFactory e EntityManager
        emf = Persistence.createEntityManagerFactory("pessoaPU");
        em = emf.createEntityManager();
    }

    // CREATE
    public void criarPessoa(Pessoa pessoa) {
        try {
            em.getTransaction().begin();
            em.persist(pessoa);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // READ (por ID)
    public Pessoa buscarPessoaPorId(Long id) {
        return em.find(Pessoa.class, id);
    }

    // READ (todos)
    public List<Pessoa> listarPessoas() {
        return em.createQuery("SELECT p FROM Pessoa p", Pessoa.class).getResultList();
    }

    // UPDATE
    public void atualizarPessoa(Pessoa pessoa) {
        try {
            em.getTransaction().begin();
            em.merge(pessoa);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletarPessoa(Long id) {
        try {
            Pessoa pessoa = em.find(Pessoa.class, id);
            if (pessoa != null) {
                em.getTransaction().begin();
                em.remove(pessoa);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // Fechar recursos
    public void close() {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    public static void main(String[] args) {
        PessoaService pessoaService = new PessoaService();

        // Criando uma nova pessoa
        Pessoa pessoa1 = new Pessoa("João", "joao@example.com", "senha123", "12345678901");
        pessoaService.criarPessoa(pessoa1);

        // Listando todas as pessoas
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        pessoas.forEach(pessoa -> System.out.println(pessoa.getNome()));

        // Buscando uma pessoa por ID
        Pessoa pessoaBuscada = pessoaService.buscarPessoaPorId(pessoa1.getId());
        System.out.println("Pessoa encontrada: " + pessoaBuscada.getNome());

        // Atualizando uma pessoa
        pessoaBuscada.setNome("João Atualizado");
        pessoaService.atualizarPessoa(pessoaBuscada);

        // Deletando uma pessoa
        pessoaService.deletarPessoa(pessoaBuscada.getId());

        pessoaService.close();
    }
}
