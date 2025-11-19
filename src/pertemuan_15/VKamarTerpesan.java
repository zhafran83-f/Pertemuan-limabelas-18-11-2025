/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_15;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
@Entity
@Table(name = "v_kamar_terpesan")    // sesuaikan nama view/table jika berbeda
public class VKamarTerpesan {

    @Id
    @Column(name = "id_kamar_terpesan") // pastikan nama kolom sesuai view
    private Integer idKamarTerpesan;

    @Column(name = "nik")
    private String nik;

    @Column(name = "nomor_kamar")
    private String nomorKamar;

    // getter & setter
    public Integer getIdKamarTerpesan() {
        return idKamarTerpesan;
    }

    public void setIdKamarTerpesan(Integer idKamarTerpesan) {
        this.idKamarTerpesan = idKamarTerpesan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
    }
}
