/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_15;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
public class TestJPA {
    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        EntityManager em = null;

        try {
            System.out.println("Membuat EntityManagerFactory...");
            emf = Persistence.createEntityManagerFactory("Pertemuan_15PU");
            em = emf.createEntityManager();

            System.out.println("Connection berhasil — mulai query test.\n");

            // Kamar
            Long kamarCount = em.createQuery("SELECT COUNT(k) FROM Kamar k", Long.class).getSingleResult();
            System.out.println("Jumlah record di table Kamar = " + kamarCount);

            List<Kamar> kamarList = em.createQuery("SELECT k FROM Kamar k", Kamar.class)
                                      .setMaxResults(5)
                                      .getResultList();
            System.out.println("\nContoh data Kamar (max 5):");
            for (Kamar k : kamarList) {
                printEntity(k);
            }

            // Tamu
            Long tamuCount = em.createQuery("SELECT COUNT(t) FROM Tamu t", Long.class).getSingleResult();
            System.out.println("\nJumlah record di table Tamu = " + tamuCount);

            List<Tamu> tamuList = em.createQuery("SELECT t FROM Tamu t", Tamu.class)
                                     .setMaxResults(5)
                                     .getResultList();
            System.out.println("\nContoh data Tamu (max 5):");
            for (Tamu t : tamuList) {
                printEntity(t);
            }

            // Login_1 (opsional)
            try {
                Long loginCount = em.createQuery("SELECT COUNT(l) FROM Login_1 l", Long.class).getSingleResult();
                System.out.println("\nJumlah record di table Login = " + loginCount);
            } catch (Exception ex) {
                System.out.println("\nEntity Login_1 tidak tersedia atau query gagal: " + ex.getMessage());
            }

            // VKamarTerpesan (view entity)
            try {
                Long vCount = em.createQuery("SELECT COUNT(v) FROM VKamarTerpesan v", Long.class).getSingleResult();
                System.out.println("\nJumlah record di view VKamarTerpesan = " + vCount);

                List<VKamarTerpesan> vList = em.createQuery("SELECT v FROM VKamarTerpesan v", VKamarTerpesan.class)
                                               .setMaxResults(10)
                                               .getResultList();
                System.out.println("\nContoh data VKamarTerpesan (max 10):");
                for (VKamarTerpesan v : vList) {
                    printEntity(v);
                }
            } catch (Exception ex) {
                System.out.println("\nEntity VKamarTerpesan tidak tersedia atau query gagal: " + ex.getMessage());
            }

            System.out.println("\nSemua query test selesai.");
        } catch (Exception e) {
            System.err.println("Terjadi error saat inisialisasi JPA atau mengeksekusi query:");
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) em.close();
            if (emf != null && emf.isOpen()) emf.close();
            System.out.println("EntityManager & EntityManagerFactory ditutup.");
        }
    }

    // Utility: print semua getter (reflection) dari sebuah entity
    private static void printEntity(Object entity) {
        if (entity == null) return;
        Class<?> cls = entity.getClass();
        System.out.println("=== " + cls.getSimpleName() + " ===");
        java.lang.reflect.Method[] methods = cls.getMethods();
        for (java.lang.reflect.Method m : methods) {
            String name = m.getName();
            if (name.startsWith("get") && m.getParameterCount() == 0) {
                try {
                    Object value = m.invoke(entity);
                    String prop = name.substring(3); // misal getNamaTamu -> NamaTamu
                    System.out.println(prop + " : " + value);
                } catch (Exception ex) {
                    // ignore - ada kemungkinan getter melempar exception (rare)
                }
            }
        }
        System.out.println("==============");
    }
}
