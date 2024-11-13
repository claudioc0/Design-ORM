package test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import model.Jogo;
import model.JogoConsole;
import model.JogoPC;

import java.util.List;

public class JogoService {

    private EntityManagerFactory emf;
    private EntityManager em;

    public JogoService() {
        // Inicializa o EntityManagerFactory e EntityManager
        emf = Persistence.createEntityManagerFactory("DesignPU");
        em = emf.createEntityManager();
    }

    // CREATE
    public void criarJogo(Jogo jogo) {
        try {
            em.getTransaction().begin();
            em.persist(jogo);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // READ (por ID)
    public Jogo buscarJogoPorId(Long id) {
        return em.find(Jogo.class, id);
    }

    // READ (todos)
    public List<Jogo> listarJogos() {
        return em.createQuery("SELECT j FROM model.Jogo j", Jogo.class).getResultList();
    }

    // UPDATE
    public void atualizarJogo(Jogo jogo) {
        try {
            em.getTransaction().begin();
            em.merge(jogo);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    // DELETE
    public void deletarJogo(Long id) {
        try {
            Jogo jogo = em.find(Jogo.class, id);
            if (jogo != null) {
                em.getTransaction().begin();
                em.remove(jogo);
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
        JogoService jogoService = new JogoService();

        // Criando um novo JogoConsole
        JogoConsole jogoConsole = new JogoConsole("FIFA 23", "Jogo de futebol", "Esporte", "2023-09-30", 299.99, "imagem_fifa23", "PlayStation 5", "Vendedor A");
        jogoService.criarJogo(jogoConsole);

        // Criando um novo JogoPC
        JogoPC jogoPC = new JogoPC("The Witcher 3", "Jogo de RPG", "Aventura", "2015-05-19", 89.99, "imagem_witcher3", "Requisitos: 8GB RAM", "Vendedor B");
        jogoService.criarJogo(jogoPC);

        // Listando todos os jogos
        List<Jogo> jogos = jogoService.listarJogos();
        jogos.forEach(jogo -> System.out.println(jogo.getId() + ": " + jogo.getNome()));

        // Buscando um Jogo por ID (supondo que o ID seja 1)
        Jogo jogoBuscado = jogoService.buscarJogoPorId(1L);
        if (jogoBuscado != null) {
            System.out.println("Jogo encontrado: " + jogoBuscado.getNome());
        } else {
            System.out.println("Jogo não encontrado.");
        }

        // Atualizando um jogo
        if (jogoBuscado != null) {
            jogoBuscado.setNome("The Witcher 3 - Edição Completa");
            jogoService.atualizarJogo(jogoBuscado);
        }

        // Deletando um Jogo
        if (jogoBuscado != null) {
            jogoService.deletarJogo(jogoBuscado.getId());
        }

        jogoService.close();
    }

}