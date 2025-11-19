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
@Table(name = "kamar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kamar.findAll", query = "SELECT k FROM Kamar k"),
    @NamedQuery(name = "Kamar.findByIdKamar", query = "SELECT k FROM Kamar k WHERE k.idKamar = :idKamar"),
    @NamedQuery(name = "Kamar.findByNomorKamar", query = "SELECT k FROM Kamar k WHERE k.nomorKamar = :nomorKamar"),
    @NamedQuery(name = "Kamar.findByTipeKamar", query = "SELECT k FROM Kamar k WHERE k.tipeKamar = :tipeKamar"),
    @NamedQuery(name = "Kamar.findByFasilitas", query = "SELECT k FROM Kamar k WHERE k.fasilitas = :fasilitas")})
public class Kamar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_kamar")
    private String idKamar;
    @Basic(optional = false)
    @Column(name = "nomor_kamar")
    private String nomorKamar;
    @Basic(optional = false)
    @Column(name = "tipe_kamar")
    private String tipeKamar;
    @Basic(optional = false)
    @Column(name = "fasilitas")
    private String fasilitas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKamar")
    private Collection<KamarTerpesan> kamarTerpesanCollection;

    public Kamar() {
    }

    public Kamar(String idKamar) {
        this.idKamar = idKamar;
    }

    public Kamar(String idKamar, String nomorKamar, String tipeKamar, String fasilitas) {
        this.idKamar = idKamar;
        this.nomorKamar = nomorKamar;
        this.tipeKamar = tipeKamar;
        this.fasilitas = fasilitas;
    }

    public String getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(String idKamar) {
        this.idKamar = idKamar;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
    }

    public String getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(String tipeKamar) {
        this.tipeKamar = tipeKamar;
    }

    public String getFasilitas() {
        return fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        this.fasilitas = fasilitas;
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
        hash += (idKamar != null ? idKamar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kamar)) {
            return false;
        }
        Kamar other = (Kamar) object;
        if ((this.idKamar == null && other.idKamar != null) || (this.idKamar != null && !this.idKamar.equals(other.idKamar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pertemuan_15.Kamar[ idKamar=" + idKamar + " ]";
    }
    
}
