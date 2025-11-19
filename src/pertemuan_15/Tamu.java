/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pertemuan_15;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lenovo IP 330-14IKB
 */
@Entity
@Table(name = "tamu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tamu.findAll", query = "SELECT t FROM Tamu t"),
    @NamedQuery(name = "Tamu.findByNik", query = "SELECT t FROM Tamu t WHERE t.nik = :nik"),
    @NamedQuery(name = "Tamu.findByNamaTamu", query = "SELECT t FROM Tamu t WHERE t.namaTamu = :namaTamu"),
    @NamedQuery(name = "Tamu.findByLamaMenginap", query = "SELECT t FROM Tamu t WHERE t.lamaMenginap = :lamaMenginap"),
    @NamedQuery(name = "Tamu.findByKamarDipesan", query = "SELECT t FROM Tamu t WHERE t.kamarDipesan = :kamarDipesan")
})
public class Tamu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nik")
    private String nik;
    @Basic(optional = false)
    @Column(name = "nama_tamu")
    private String namaTamu;
    @Basic(optional = false)
    @Column(name = "lama_menginap")
    private String lamaMenginap;

    // <- Ubah mapping kolom di bawah ini supaya cocok ke DB (id_kamar)
    @Basic(optional = false)
    @Column(name = "id_kamar") // sebelumnya "kamar_dipesan" — ganti ke "id_kamar"
    private String kamarDipesan;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nik")
    private Collection<KamarTerpesan> kamarTerpesanCollection;

    public Tamu() {
    }

    public Tamu(String nik) {
        this.nik = nik;
    }

    public Tamu(String nik, String namaTamu, String lamaMenginap, String kamarDipesan) {
        this.nik = nik;
        this.namaTamu = namaTamu;
        this.lamaMenginap = lamaMenginap;
        this.kamarDipesan = kamarDipesan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNamaTamu() {
        return namaTamu;
    }

    public void setNamaTamu(String namaTamu) {
        this.namaTamu = namaTamu;
    }

    public String getLamaMenginap() {
        return lamaMenginap;
    }

    public void setLamaMenginap(String lamaMenginap) {
        this.lamaMenginap = lamaMenginap;
    }

    public String getKamarDipesan() {
        return kamarDipesan;
    }

    public void setKamarDipesan(String kamarDipesan) {
        this.kamarDipesan = kamarDipesan;
    }

    @XmlTransient
    public Collection<KamarTerpesan> getKamarTerpesanCollection() {
        return kamarTerpesanCollection;
    }

    public void setKamarTerpesanCollection(Collection<KamarTerpesan> kamarTerpesanCollection) {
        this.kamarTerpesanCollection = kamarTerpesanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nik != null ? nik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tamu)) {
            return false;
        }
        Tamu other = (Tamu) object;
        if ((this.nik == null && other.nik != null) || (this.nik != null && !this.nik.equals(other.nik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pertemuan_15.Tamu[ nik=" + nik + " ]";
    }

}
